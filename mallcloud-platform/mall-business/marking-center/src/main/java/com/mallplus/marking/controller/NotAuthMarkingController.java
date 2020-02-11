package com.mallplus.marking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallplus.common.annotation.IgnoreAuth;
import com.mallplus.common.annotation.SysLog;
import com.mallplus.common.constant.ConstansValue;
import com.mallplus.common.entity.pms.PmsFavorite;
import com.mallplus.common.entity.pms.PmsProduct;
import com.mallplus.common.entity.sms.*;
import com.mallplus.common.entity.ums.UmsMember;
import com.mallplus.common.redis.template.RedisUtil;
import com.mallplus.common.utils.CommonResult;
import com.mallplus.common.utils.DateUtils;
import com.mallplus.common.utils.JsonUtil;
import com.mallplus.common.utils.ValidatorUtils;
import com.mallplus.common.vo.*;
import com.mallplus.goods.service.IPmsFavoriteService;
import com.mallplus.goods.service.IPmsProductService;
import com.mallplus.marking.mapper.*;
import com.mallplus.marking.service.*;
import com.mallplus.member.service.IUmsMemberService;
import com.mallplus.member.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠卷表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "NotAuthMarkingController", description = "")
@RequestMapping("/")
public class NotAuthMarkingController {
    @Resource
    private ISmsCouponService couponService;
    @Resource
    private SmsCouponHistoryMapper couponHistoryMapper;
    @Resource
    private SmsFlashPromotionProductRelationMapper smsFlashPromotionProductRelationMapper;
    @Resource
    private ISmsRedPacketService redPacketService;
    @Resource
    private ISmsUserRedPacketService userRedPacketService;
    @Resource
    private SmsGroupMemberMapper groupMemberMapper;
    @Resource
    private SmsGroupMapper groupMapper;
    @Resource
    private ISmsBasicGiftsService basicGiftsService;
    @Resource
    private ISmsBasicMarkingService basicMarkingService;
    @Resource
    private SmsGroupActivityMapper groupActivityMapper;
    @Resource
    private ISmsGroupActivityService smsGroupActivityService;
    @Resource
    private IPmsProductService productService;
    @Resource
    private RedisService redisService;
    @Autowired
    private IUmsMemberService memberService;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private IPmsFavoriteService favoriteService;
    @IgnoreAuth
    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有红包列表")
    @ApiOperation("根据条件查询所有红包列表")
    @PostMapping(value = "/notAuth/redPacket/list")
    public Object getSmsRedPacketByPage(@RequestBody SmsRedPacket entity) {
        try {
            List<SmsRedPacket> redPacketList = redPacketService.list(new QueryWrapper<>());
            SmsUserRedPacket userRedPacket = new SmsUserRedPacket();
            userRedPacket.setUserId(entity.getUserId());
            List<SmsUserRedPacket> list = userRedPacketService.list(new QueryWrapper<>(userRedPacket));
            for(SmsRedPacket vo : redPacketList){
                if (list!=null && list.size()>0){
                    for (SmsUserRedPacket vo1 : list){
                        if(vo.getId().equals(vo1.getRedPacketId())){
                            vo.setStatus(1);
                            vo.setReciveAmount(vo1.getAmount());
                            break;
                        }
                    }
                }
            }
            return new CommonResult().success(redPacketList);
        } catch (Exception e) {
            log.error("根据条件查询所有红包列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }
    @IgnoreAuth
    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有红包列表")
    @ApiOperation("根据条件查询所有红包列表")
    @GetMapping(value = "/notAuth/coupon/list")
    public Object getCouponByPage() {
        try {
            return new CommonResult().success(couponService.selectNotRecive());
        } catch (Exception e) {
            log.error("根据条件查询所有红包列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }
    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有导航栏列表")
    @ApiOperation("根据条件查询所有导航栏列表")
    @GetMapping(value = "/notAuth/nav/list")
    public Object getNavByPage() {
        try {
            return new CommonResult().success(couponService.selectNotRecive());
        } catch (Exception e) {
            log.error("根据条件查询所有红包列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有导航栏列表")
    @ApiOperation("根据条件查询所有导航栏列表")
    @PostMapping(value = "/notAuth/acceptGroup")
    public Object acceptGroup(@RequestBody OrderParam orderParam) {
        Long nowT = System.currentTimeMillis();
        SmsGroup group = groupMapper.selectById(orderParam.getGroupId());
        Date endTime = DateUtils.convertStringToDate(DateUtils.addHours(group.getEndTime(), group.getHours()), "yyyy-MM-dd HH:mm:ss");

        if (nowT > group.getStartTime().getTime() && nowT < endTime.getTime()) {
            SmsGroupMember groupMember = new SmsGroupMember();

            if(orderParam.getGroupType()==1){
                groupMember.setMainId(orderParam.getMemberId());
                groupMember.setGoodsId(orderParam.getGoodsId());
                SmsGroupMember    exist = groupMemberMapper.selectOne(new QueryWrapper<>(groupMember));
                if (exist!=null){
                    return new CommonResult().failed("你已经参加过此活动");
                }
                groupMember.setName(orderParam.getMemberIcon());
                groupMember.setStatus(2);
                groupMember.setOrderId(orderParam.getOrderId()+"");
                groupMember.setMainId(orderParam.getMemberId());
                groupMember.setCreateTime(new Date());
                groupMember.setGroupId(group.getId());

                groupMember.setMemberId(orderParam.getMemberId()+"");
                groupMember.setExipreTime(System.currentTimeMillis()+(group.getHours()*60*60*60));
                groupMemberMapper.insert(groupMember);
            }else{
                groupMember = groupMemberMapper.selectById(orderParam.getMgId());
                String []mids = groupMember.getMemberId().split(",");
                for (int i=0;i<mids.length;i++){
                    if (orderParam.getMemberId().toString().equals(mids[i])){
                        return new CommonResult().failed("你已经参加过此活动");
                    }
                }

                groupMember.setName(groupMember.getName()+","+orderParam.getMemberIcon());
                groupMember.setOrderId(groupMember.getOrderId()+","+orderParam.getOrderId());
                groupMember.setMemberId(groupMember.getMemberId()+","+orderParam.getMemberId());
                groupMemberMapper.updateById(groupMember);
            }

        } else {
            return new CommonResult().failed("活动已经结束");
        }
        return new CommonResult().failed("活动已经结束");
    }


    @ApiOperation("根据条件查询所有拼团列表")
    @GetMapping(value = "/notAuth/listGroup")
    public List<SmsGroup> listGroup(){
        return    groupMapper.selectList(new QueryWrapper<>());
    }


    @ApiOperation("根据条件查询拼团")
    @GetMapping(value = "/notAuth/getGroupById")
    public SmsGroup getGroupById(@RequestParam("id") Long id){
        return groupMapper.selectById(id);
    }


    @ApiOperation("根据条件查询拼团用户表")
    @GetMapping(value = "/notAuth/selectGroupMemberList")
    public Object selectGroupMemberList(@RequestParam("id") Long id){
        return groupMemberMapper.selectList(new QueryWrapper<SmsGroupMember>().eq("group_id",id));
    }

    @PostMapping(value = "/notAuth/updateGroupById")
    public void updateGroupById(@RequestBody SmsGroup group) {
groupMapper.updateById(group);
    }

    @PostMapping(value = "/notAuth/matchOrderBasicMarking")
   public SmsBasicMarking matchOrderBasicMarking(@RequestBody CartMarkingVo vo){
        return basicMarkingService.matchOrderBasicMarking(vo);
    }

    @PostMapping(value = "/notAuth/matchOrderBasicGifts")
    public List<SmsBasicGifts> matchOrderBasicGifts(@RequestBody CartMarkingVo vo) {
       return basicGiftsService.matchOrderBasicGifts(vo);
    }

    @PostMapping(value = "/notAuth/getFlashPromotionProductRelationById")
    public SmsFlashPromotionProductRelation getFlashPromotionProductRelationById(@RequestParam("id") Integer id) {
       return smsFlashPromotionProductRelationMapper.selectById(id);
    }

    @PostMapping(value = "/notAuth/updateFlashPromotionProductRelationById")
    public void updateFlashPromotionProductRelationById(@RequestBody SmsFlashPromotionProductRelation relation) {
        smsFlashPromotionProductRelationMapper.updateById(relation);
    }

    @PostMapping(value = "/notAuth/getSmsGroupActivityById")
    public SmsGroupActivity getSmsGroupActivityById(@RequestParam("groupActivityId") Long groupActivityId) {
        return groupActivityMapper.selectById(groupActivityId);
    }

    @PostMapping(value = "/notAuth/getCouponById")
    public SmsCoupon getCouponById(@RequestParam("couponId") Long couponId) {
        return couponService.getById(couponId);
    }

    @PostMapping(value = "/notAuth/getcouponHistoryById")
    public SmsCouponHistory getcouponHistoryById(@RequestParam("memberCouponId") Long memberCouponId) {
        return  couponHistoryMapper.selectById(memberCouponId);
    }

    @PostMapping(value = "/notAuth/getPromotionProductList")
    public List<SmsCouponHistoryDetail> listCart(@RequestBody CartMarkingVo vo) {
        return couponService.listCart(vo);
    }

    @IgnoreAuth
    @SysLog(MODULE = "oms", REMARK = "查询团购商品列表")
    @ApiOperation(value = "查询团购商品列表")
    @ResponseBody
    @RequestMapping(value = "/notAuth/groupActivityList", method = RequestMethod.GET)
    public Object orderList(SmsGroupActivity groupActivity,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        IPage<SmsGroupActivity> page = null;
        groupActivity.setStatus(1);
        page = smsGroupActivityService.page(new Page<SmsGroupActivity>(pageNum, pageSize), new QueryWrapper<>(groupActivity).orderByDesc("create_time"));

        for (SmsGroupActivity smsGroupActivity : page.getRecords()) {
            if (ValidatorUtils.notEmpty(smsGroupActivity.getGoodsIds())) {
                List<PmsProduct> productList = (List<PmsProduct>) productService.list(new QueryWrapper<PmsProduct>().in("id", Arrays.asList(smsGroupActivity.getGoodsIds().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList())).select(ConstansValue.sampleGoodsList));
                if (productList != null && productList.size() > 0) {
                    smsGroupActivity.setProductList(productList);
                }
            }

        }
        return new CommonResult().success(page);
    }
    @SysLog(MODULE = "pms", REMARK = "查询团购商品详情信息")
    @IgnoreAuth
    @ResponseBody
    @RequestMapping(value = "/notAuth/group.activity.getdetial", method = RequestMethod.GET)
    @ApiOperation(value = "查询团购商品详情信息")
    public Object queryProductDetail(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        SmsGroupActivity groupActivity = smsGroupActivityService.getById(id);
        Map<String, Object> map = new HashMap<>();
        if (groupActivity != null) {
            if (ValidatorUtils.notEmpty(groupActivity.getGoodsIds())) {
                List<Long> goodIds = Arrays.asList(groupActivity.getGoodsIds().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                GoodsDetailResult goods = JsonUtil.jsonToPojo(redisService.get(String.format(Rediskey.GOODSDETAIL, goodIds.get(0) + "")), GoodsDetailResult.class);
                if (goods == null || goods.getGoods() == null) {
                    goods = productService.getGoodsRedisById(goodIds.get(0));
                }
                if (goods != null && goods.getGoods() != null) {
                    UmsMember umsMember = memberService.getCurrentMember();
                    if (umsMember != null && umsMember.getId() != null) {
                        isCollectGoods(map, goods, umsMember);
                    }
                    recordGoodsFoot(id);

                    List<Long> newGoodIds = goodIds.subList(1, goodIds.size());
                    if (newGoodIds != null && newGoodIds.size() > 0) {
                        List<PmsProduct> productList = (List<PmsProduct>) productService.list(new QueryWrapper<PmsProduct>().in("id", goodIds).select(ConstansValue.sampleGoodsList));
                        if (productList != null && productList.size() > 0) {
                            groupActivity.setProductList(productList);
                        }
                    }
                    map.put("groupActivity", groupActivity);
                    map.put("goods", goods);
                    return new CommonResult().success(map);
                }
            }

        }
        return new CommonResult().failed();
    }

    /**
     * 判断是否收藏商品
     *
     * @param map
     * @param goods
     * @param umsMember
     */
    private void isCollectGoods(Map<String, Object> map, GoodsDetailResult goods, UmsMember umsMember) {
        PmsProduct p = goods.getGoods();
        PmsFavorite query = new PmsFavorite();
        query.setObjId(p.getId());
        query.setMemberId(umsMember.getId());
        query.setType(1);
        PmsFavorite findCollection = favoriteService.getOne(new QueryWrapper<>(query));
        if (findCollection != null) {
            map.put("favorite", true);
        } else {
            map.put("favorite", false);
        }
    }

    /**
     * 记录商品浏览记录
     *
     * @param id
     */
    private void recordGoodsFoot(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        //记录浏览量到redis,然后定时更新到数据库
        String key = Rediskey.GOODS_VIEWCOUNT_CODE + id;
        //找到redis中该篇文章的点赞数，如果不存在则向redis中添加一条
        Map<Object, Object> viewCountItem = redisUtil.hGetAll(Rediskey.GOODS_VIEWCOUNT_KEY);
        Integer viewCount = 0;
        if (!viewCountItem.isEmpty()) {
            if (viewCountItem.containsKey(key)) {
                viewCount = Integer.parseInt(viewCountItem.get(key).toString()) + 1;
                redisUtil.hPut(Rediskey.GOODS_VIEWCOUNT_KEY, key, viewCount + "");
            } else {
                redisUtil.hPut(Rediskey.GOODS_VIEWCOUNT_KEY, key, 1 + "");
            }
        } else {
            redisUtil.hPut(Rediskey.GOODS_VIEWCOUNT_KEY, key, 1 + "");
        }
    }

}
