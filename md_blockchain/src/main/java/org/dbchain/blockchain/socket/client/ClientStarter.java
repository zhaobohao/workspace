package org.dbchain.blockchain.socket.client;

import com.google.common.collect.Maps;
import org.dbchain.blockchain.common.AppId;
import org.dbchain.blockchain.common.CommonUtil;
import org.dbchain.blockchain.core.bean.Member;
import org.dbchain.blockchain.core.bean.MemberData;
import org.dbchain.blockchain.core.bean.Permission;
import org.dbchain.blockchain.core.bean.PermissionData;
import org.dbchain.blockchain.core.event.NodesConnectedEvent;
import org.dbchain.blockchain.core.manager.PermissionManager;
import org.dbchain.blockchain.socket.common.Const;
import org.dbchain.blockchain.socket.packet.BlockPacket;
import org.dbchain.blockchain.socket.packet.NextBlockPacketBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tio.client.ClientTioConfig;
import org.tio.client.TioClient;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import static org.dbchain.blockchain.socket.common.Const.GROUP_NAME;

/**
 * @author zhaobo create on 2020/3/18.
 */
@Component
public class ClientStarter {
    @Resource
    private ClientTioConfig clientTioConfig;
    @Resource
    private PacketSender packetSender;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private PermissionManager permissionManager;
    @Value("${managerUrl}")
    private String managerUrl;
    @Value("${appId}")
    private String appId;
    @Value("${name}")
    private String name;
    @Value("${singleNode:false}")
    private Boolean singleNode;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Set<Node> nodes = new HashSet<>();
    
    // 节点连接状态
    private Map<String,Integer> nodesStatus = Maps.newConcurrentMap();
    private volatile boolean isNodesReady = false; // 节点是否已准备好

    /**
     * 初始化权限信息
     * 避免新联盟节点加入时，同步区块而权限未初始化导致同步异常
     */
    @PostConstruct
    public void initPermission() {
        fetchPermission();
    }

    /**
     * 从麦达区块链管理端获取已登记的各服务器ip
     * 隔5分钟去获取一次
     */
    @Scheduled(fixedRate = 300000)
    public void fetchOtherServer() {
        String localIp = CommonUtil.getLocalIp();
        logger.info("本机IP：{}",localIp);
        try {
            //如果连不上服务器，就不让启动
            MemberData memberData = restTemplate.getForEntity(managerUrl + "member?name=" + name + "&appId=" + AppId
                            .value +
                            "&ip=" +
                            localIp,
                    MemberData.class).getBody();
            //合法的客户端
            if (memberData.getCode() == 0) {
                List<Member> memberList = memberData.getMembers();
                logger.info("共有" + memberList.size() + "个成员需要连接：" + memberList.toString());

                nodes.clear();
                for (Member member : memberList) {
                    Node node = new Node(member.getIp(), Const.PORT);
                    nodes.add(node);
                }
                //开始尝试绑定到对方开启的server
                bindServerGroup(nodes);

            } else {
                logger.error("不是合法有效的已注册的客户端");
                System.exit(0);
            }
        } catch (Exception e) {
            logger.error("请先启动md_blockchain_manager服务，并配置appId等属性，只有合法联盟链成员才能启动该服务");
            System.exit(0);
        }

    }

    /**
     * 从麦达区块链管理端获取权限信息，一天获取一次即可
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24, initialDelay = 2000)
    public void fetchPermission() {
        try {
            //如果连不上服务器，就不让启动
            PermissionData permissionData = restTemplate.getForEntity(managerUrl + "permission?name=" + name,
                    PermissionData.class).getBody();
            //获取到权限
            if (permissionData.getCode() == 0) {
                List<Permission> permissionList = permissionData.getPermissions();
                permissionManager.savePermissionList(permissionList);
            } else {
                logger.error("无法获取权限信息");
                System.exit(0);
            }
        } catch (Exception e) {
            logger.error("请先启动md_blockchain_manager服务，并配置appId等属性，只有合法联盟链成员才能启动该服务");
            System.exit(0);
        }

    }

    /**
     * 每30秒群发一次消息，和别人对比最新的Block
     */
    @Scheduled(fixedRate = 30000)
    public void heartBeat() {
    	if(!isNodesReady) {
    	    return;
        }
        logger.info("---------开始心跳包--------");
        BlockPacket blockPacket = NextBlockPacketBuilder.build();
        packetSender.sendGroup(blockPacket);
    }

    public void onNodesReady() {
        logger.info("开始群发信息获取next Block");
        //在这里发请求，去获取group别人的新区块
        BlockPacket nextBlockPacket = NextBlockPacketBuilder.build();
        packetSender.sendGroup(nextBlockPacket);
    }

    /**
     * client在此绑定多个服务器，多个服务器为一个group，将来发消息时发给一个group。
     * 此处连接的server的ip需要和服务器端保持一致，服务器删了，这边也要踢出Group
     */
    private void bindServerGroup(Set<Node> serverNodes) {
        //当前已经连接的
        SetWithLock<ChannelContext> setWithLock = Tio.getAll(clientTioConfig);
        Lock lock2 = setWithLock.getLock().readLock();
        lock2.lock();
        try {
            Set<ChannelContext> set = setWithLock.getObj();
            //已连接的节点集合
            Set<Node> connectedNodes = set.stream().map(ChannelContext::getServerNode).collect(Collectors.toSet());

            //连接新增的，删掉已在管理端不存在的
            for (Node node : serverNodes) {
                if (!connectedNodes.contains(node)) {
                    connect(node);
                }
            }
            //删掉已经不存在
            for (ChannelContext channelContext : set) {
                Node node = channelContext.getServerNode();
                if (!serverNodes.contains(node)) {
                    Tio.remove(channelContext, "主动关闭" + node.getIp());
                }

            }
        } finally {
            lock2.unlock();
        }

    }

    private void connect(Node serverNode) {
        try {
            TioClient tioClient = new TioClient(clientTioConfig);
            logger.info("开始绑定" + ":" + serverNode.toString());
            tioClient.asynConnect(serverNode);
        } catch (Exception e) {
            logger.info("异常");
        }
    }
    
    @EventListener(NodesConnectedEvent.class)
    public void onConnected(NodesConnectedEvent connectedEvent){
    	ChannelContext channelContext = connectedEvent.getSource();
    	Node node = channelContext.getServerNode();
    	if (channelContext.isClosed) {
            logger.info("连接" + node.toString() + "失败");
            nodesStatus.put(node.getIp(), -1);
            return;
        }else{
        	logger.info("连接" + node.toString() + "成功");
        	nodesStatus.put(node.getIp(), 1);
        	//绑group是将要连接的各个服务器节点做为一个group
        	Tio.bindGroup(channelContext, GROUP_NAME);

        	int csize = Tio.getAll(clientTioConfig).size();
        	if(csize >= pbftAgreeCount()){
        		synchronized (nodesStatus) {
        			if(!isNodesReady){
        				isNodesReady = true;
        				onNodesReady();
        			}
				}
        	}
        }
    }

    public int halfGroupSize() {
        SetWithLock<ChannelContext> setWithLock = clientTioConfig.groups.clients(clientTioConfig, Const.GROUP_NAME);
        return setWithLock.getObj().size() / 2;
    }

    /**
     * pbft算法中拜占庭节点数量f，总节点数3f+1
     *
     * @return f
     */
    public int pbftSize() {
        //Group内共有多少个节点
        int total = nodes.size();
        int pbft = (total - 1) / 3;
        if (pbft <= 0) {
            pbft = 1;
        }
        //如果要单节点测试，此处返回值改为0
        if(singleNode) {
            return 0;
        }
        return pbft;
    }

    public int pbftAgreeCount() {
        return pbftSize() * 2 + 1;
    }
}