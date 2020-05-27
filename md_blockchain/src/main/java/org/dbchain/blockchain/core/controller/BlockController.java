package org.dbchain.blockchain.core.controller;

import cn.hutool.core.collection.CollectionUtil;
import org.dbchain.blockchain.ApplicationContextProvider;
import org.dbchain.blockchain.block.Block;
import org.dbchain.blockchain.block.BlockBody;
import org.dbchain.blockchain.block.Instruction;
import org.dbchain.blockchain.block.Operation;
import org.dbchain.blockchain.block.check.BlockChecker;
import org.dbchain.blockchain.socket.packet.BlockPacket;
import org.dbchain.blockchain.socket.packet.PacketBuilder;
import org.dbchain.blockchain.socket.packet.PacketType;
import org.dbchain.blockchain.common.exception.TrustSDKException;
import org.dbchain.blockchain.core.bean.BaseData;
import org.dbchain.blockchain.core.bean.ResultGenerator;
import org.dbchain.blockchain.core.event.DbSyncEvent;
import org.dbchain.blockchain.core.manager.DbBlockManager;
import org.dbchain.blockchain.core.manager.MessageManager;
import org.dbchain.blockchain.core.manager.SyncManager;
import org.dbchain.blockchain.core.model.MessageEntity;
import org.dbchain.blockchain.core.requestbody.BlockRequestBody;
import org.dbchain.blockchain.core.requestbody.InstructionBody;
import org.dbchain.blockchain.core.service.BlockService;
import org.dbchain.blockchain.core.service.InstructionService;
import org.dbchain.blockchain.socket.body.RpcBlockBody;
import org.dbchain.blockchain.socket.client.PacketSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author zhaobo create on 2020/3/7.
 */
@Api(tags = "区块链接口", description = "简单区块链功能接口")
@RestController
@RequestMapping("/block")
public class BlockController {
    @Resource
    private BlockService blockService;
    @Resource
    private PacketSender packetSender;
    @Resource
    private DbBlockManager dbBlockManager;
    @Resource
    private InstructionService instructionService;
    @Resource
    private SyncManager syncManager;
    @Resource
    private MessageManager messageManager;
    @Resource
    private BlockChecker blockChecker;
    @Value("${publicKey:A8WLqHTjcT/FQ2IWhIePNShUEcdCzu5dG+XrQU8OMu54}")
    private String publicKey;
    @Value("${privateKey:yScdp6fNgUU+cRUTygvJG4EBhDKmOMRrK4XJ9mKVQJ8=}")
    private String privateKey;

    /**
     * 添加一个block，需要先在InstructionController构建1-N个instruction指令，然后调用该接口生成Block
     *
     * @param blockRequestBody
     *         指令的集合
     * @return 结果
     */
    @ApiIgnore
    @PostMapping("/insert")
    @ApiOperation(value = "添加一个区块", notes = "测试添加一个区块", httpMethod = "POST", response = BaseData.class)
    public BaseData insert(@ApiParam(name = "blockRequestBody对象", value = "传入json格式", required = true) @RequestBody BlockRequestBody blockRequestBody) throws TrustSDKException {
    	String msg = blockService.check(blockRequestBody);
        if (msg != null) {
            return ResultGenerator.genFailResult(msg);
        }
        return ResultGenerator.genSuccessResult(blockService.addBlock(blockRequestBody));
    }

    /**
     * 测试生成一个insert:Block，公钥私钥可以通过PairKeyController来生成
     * @param content
     * sql内容
     */
    @GetMapping("/create")
    @ApiOperation(value = "创建一个区块", notes = "创建一个新区块", httpMethod = "GET", response = BaseData.class)
    public BaseData create(@ApiParam(name = "content", value = "区块链内容", required = true)  @RequestParam(value = "content") String content) throws Exception {
        InstructionBody instructionBody = new InstructionBody();
        instructionBody.setOperation(Operation.ADD);
        instructionBody.setTable("message");
        instructionBody.setJson("{\"content\":\"" + content + "\"}");
        /*instructionBody.setPublicKey("A8WLqHTjcT/FQ2IWhIePNShUEcdCzu5dG+XrQU8OMu54");
        instructionBody.setPrivateKey("yScdp6fNgUU+cRUTygvJG4EBhDKmOMRrK4XJ9mKVQJ8=");*/
        instructionBody.setPublicKey(publicKey);
        instructionBody.setPrivateKey(privateKey);
        Instruction instruction = instructionService.build(instructionBody);

        BlockRequestBody blockRequestBody = new BlockRequestBody();
        blockRequestBody.setPublicKey(instructionBody.getPublicKey());
        BlockBody blockBody = new BlockBody();
        blockBody.setInstructions(CollectionUtil.newArrayList(instruction));

        blockRequestBody.setBlockBody(blockBody);

        return ResultGenerator.genSuccessResult(blockService.addBlock(blockRequestBody));
    }
    
    /**
     * 测试生成一个update:Block，公钥私钥可以通过PairKeyController来生成
     * @param id 更新的主键
     * @param content
     * sql内容
     */
    @GetMapping("update")
    @ApiOperation(value = "更新区块链内容", notes = "根据ID更新区块链内容", httpMethod = "GET", response = BaseData.class)
    public BaseData testUpdate(@ApiParam(name = "id", value = "区块链信息编号", required = true) @RequestParam(value = "id",required = true) String id,
                               @ApiParam(name = "content", value = "区块链内容", required = true) @RequestParam(value = "content") String content) throws Exception {
    	if(StringUtils.isBlank(id)) ResultGenerator.genSuccessResult("主键不可为空");
    	InstructionBody instructionBody = new InstructionBody();
    	instructionBody.setOperation(Operation.UPDATE);
    	instructionBody.setTable("message");
    	instructionBody.setInstructionId(id);
    	instructionBody.setJson("{\"content\":\"" + content + "\"}");
    	 /*instructionBody.setPublicKey("A8WLqHTjcT/FQ2IWhIePNShUEcdCzu5dG+XrQU8OMu54");
        instructionBody.setPrivateKey("yScdp6fNgUU+cRUTygvJG4EBhDKmOMRrK4XJ9mKVQJ8=");*/
        instructionBody.setPublicKey(publicKey);
        instructionBody.setPrivateKey(privateKey);
    	Instruction instruction = instructionService.build(instructionBody);
    	
    	BlockRequestBody blockRequestBody = new BlockRequestBody();
    	blockRequestBody.setPublicKey(instructionBody.getPublicKey());
    	BlockBody blockBody = new BlockBody();
    	blockBody.setInstructions(CollectionUtil.newArrayList(instruction));
    	
    	blockRequestBody.setBlockBody(blockBody);
    	
    	return ResultGenerator.genSuccessResult(blockService.addBlock(blockRequestBody));
    }
    
    /**
     * 测试生成一个delete:Block，公钥私钥可以通过PairKeyController来生成
     * @param id 待删除记录的主键
     * sql内容
     */
    @GetMapping("delete")
    @ApiOperation(value = "删除区块内容", notes = "删除区块链内容", httpMethod = "GET", response = BaseData.class)
    public BaseData delete(@ApiParam(name = "id", value = "区块链信息编号", required = true)  @RequestParam(value = "id",required = true) String id) throws Exception {
    	if(StringUtils.isBlank(id)) ResultGenerator.genSuccessResult("主键不可为空");
    	InstructionBody instructionBody = new InstructionBody();
    	instructionBody.setOperation(Operation.DELETE);
    	instructionBody.setTable("message");
    	instructionBody.setInstructionId(id);
        MessageEntity message=messageManager.findById(id);
        String content=ObjectUtils.isEmpty(message)?"":message.getContent();
        instructionBody.setJson("{\"content\":\"" + content + "\"}");
    	 /*instructionBody.setPublicKey("A8WLqHTjcT/FQ2IWhIePNShUEcdCzu5dG+XrQU8OMu54");
        instructionBody.setPrivateKey("yScdp6fNgUU+cRUTygvJG4EBhDKmOMRrK4XJ9mKVQJ8=");*/
        instructionBody.setPublicKey(publicKey);
        instructionBody.setPrivateKey(privateKey);
    	Instruction instruction = instructionService.build(instructionBody);
    	
    	BlockRequestBody blockRequestBody = new BlockRequestBody();
    	blockRequestBody.setPublicKey(instructionBody.getPublicKey());
    	BlockBody blockBody = new BlockBody();
    	blockBody.setInstructions(CollectionUtil.newArrayList(instruction));
    	
    	blockRequestBody.setBlockBody(blockBody);
    	
    	return ResultGenerator.genSuccessResult(blockService.addBlock(blockRequestBody));
    }

    /**
     * 查询已落地的sqlite里的所有数据
     */
    @ApiOperation(value = "查询区块链数据", notes = "查询区块链数据", httpMethod = "GET", response = BaseData.class)
    @GetMapping("sqlite")
    public BaseData sqlite() {
        return ResultGenerator.genSuccessResult(messageManager.findAll());
    }

    /**
     * 查询已落地的sqlite里content字段
     */
    @ApiOperation(value = "查询区块链内容", notes = "查询区块链内容", httpMethod = "GET", response = BaseData.class)
    @GetMapping("sqlite/content")
    public BaseData content() {
        return ResultGenerator.genSuccessResult(messageManager.findAllContent());
    }

    /**
     * 获取最后一个block的信息
     */
    @ApiOperation(value = "获取最后一个块信息", notes = "获取最后一个块信息", httpMethod = "GET", response = BaseData.class)
    @GetMapping("last")
    public BaseData last() {
        return ResultGenerator.genSuccessResult(dbBlockManager.getLastBlock());
    }

    /**
     * 手工执行区块内sql落地到sqlite操作
     * @param pageable
     * 分页
     * @return
     * 已同步到哪块了的信息
     */
    @ApiIgnore
    @ApiOperation(value = "手工执行区块内sql落地到sqlite操作", notes = "获取数据同步到的区块信息", httpMethod = "GET", response = BaseData.class)
    @GetMapping("sync")
    public BaseData sync( @PageableDefault Pageable pageable) {
        ApplicationContextProvider.publishEvent(new DbSyncEvent(""));
        return ResultGenerator.genSuccessResult(syncManager.findAll(pageable));
    }
    
    /**
     * 全量检测区块是否正常
     * @return
     * null - 通过
     * hash - 第一个异常hash
     */
    @ApiIgnore
    @ApiOperation(value = "全量检测区块是否正常", notes = "全量检测区块是否正常", httpMethod = "GET", response = BaseData.class)
    @GetMapping("check")
    public BaseData check() {
    	
    	Block block = dbBlockManager.getFirstBlock();
    	
    	String hash = null;
    	while(block != null && hash == null) {
    		hash = blockChecker.checkBlock(block);
    		block = dbBlockManager.getNextBlock(block);
    	}
    	return ResultGenerator.genSuccessResult(hash);
    }

    /**
     * 获取第一个区块信息
     */
    @ApiOperation(value = "获取第一个区块信息", notes = "获取第一个区块信息", httpMethod = "GET", response = BaseData.class)
    @GetMapping("/first")
    public BaseData first() {
        Block block = dbBlockManager.getFirstBlock();
        BlockPacket packet = new PacketBuilder<RpcBlockBody>()
                .setType(PacketType.NEXT_BLOCK_INFO_REQUEST)
                .setBody(new RpcBlockBody(block)).build();
        packetSender.sendGroup(packet);
        return ResultGenerator.genSuccessResult(block);
    }


}
