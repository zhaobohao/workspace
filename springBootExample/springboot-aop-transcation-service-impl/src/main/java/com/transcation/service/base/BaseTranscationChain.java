package com.transcation.service.base;

import com.transcation.service.enums.ServiceStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * create by: zhaobo
 * description: service的链路编排
 * create time: 2020/6/10 16:04
 */
public abstract class BaseTranscationChain {
    //用List集合来存储过滤规则
    List<BaseTranscationService> chains = new ArrayList<BaseTranscationService>();
    //用于标记规则的引用顺序
    int index=0;
    // 如果 有冲正失败和查证失败，需要重试的次数。
    private int retry=3;

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    //往规则链条中添加规则
    public BaseTranscationChain addTranscationService(BaseTranscationService f) {
        chains.add(f);
        //代码的设计技巧:Chain链添加过滤规则结束后返回添加后的Chain，方便我们下面doFilter函数的操作
        return this;
    }

    /**
     * 所有的chain 调用些方法执行。
     * @param context
     * @return
     */
    public Boolean invoke(BaseServiceContext context) {
        int step=chains.size();
//        for ( index = 0; index <step ; index++) {
//            //每添加一个过滤规则，index自增1
//            BaseTranscationService service=chains.get(index);
//            //根据索引值获取对应的服务进行处理
//            try {
//                ServiceStatus result=service.trade(context);
//                if(result== ServiceStatus.FAILS){
//                    refund(context);
//                }else if(result==ServiceStatus.TIMEOUT)
//                {
//                    // 正向交易 超时了，进行查证流程。
//                    ServiceStatus checkResult=  service.check(context);
//                    if(checkResult==ServiceStatus.Doubt){
//                        int refundStep=0;
//                        //有service冲正操作，超时，那么循环调用N次。
//                        for (;refundStep < retry;refundStep++) {
//                            if (service.check(context)==ServiceStatus.SUCCESS){
//                                refundStep--;
//                                break;
//                            }
//                        }
//                        if(refundStep>=index){
//                            // 重试多次失败,调用冲正方法
//                            refund(context);
//                        }
//                    }
//
//                }
//            }catch (Exception e){
//               //返回任何异常，调用所有已执行service的冲正流程
//                for (int sub=index-1;sub<=index ; sub--) {
//                    // 需要冲正的service
////                    BaseTranscationService service=chains.get(index);
////                    service.refund(context);
//                }
//                refund(context);
//            }
//        }
//        // 所有service执行成功后，执行chain的正向交易
//        trade(context);
        return true;
    }

    /**
     * chain 的冲正方法
     * @param context
     */
    private void refund(BaseServiceContext context) {
        //如果正向交易 失败，调用所有已执行service的冲正流程
//        for (int sub=index-1;sub<=index ; sub--) {
//            // 需要冲正的service
//            BaseTranscationService refundService=chains.get(index);
//            if (refundService.refund(context)!= ServiceStatus.SUCCESS)
//            {
//                int refundStep=0;
//                //有service冲正操作，超时，那么循环调用N次。
//                for (;refundStep < retry;refundStep++) {
//                    if (refundService.refund(context)==ServiceStatus.SUCCESS){
//                        refundStep--;
//                        break;
//                    }
//                }
//                if(refundStep>=index){
//                    // 重试多次失败,回调冲正失败方法
//                    refundFails(context);
//                }
//            }
//        }
        // 回调冲正成功后方法
        refundSuccess(context);
    }

    protected ServiceStatus trade(BaseServiceContext context) {
        return null;
    }

    /**
     * 冲正成功后的，回调处理
     * @param context
     * @return
     */
    protected abstract ServiceStatus refundSuccess(BaseServiceContext context);

    /**
     *  冲正失败后的，回调处理
     * @param context
     * @return
     */

    protected abstract ServiceStatus refundFails(BaseServiceContext context);

    /**
     *  查证成功后的处理
     * @param context
     * @return
     */
    protected abstract ServiceStatus checkSuccess(BaseServiceContext context) ;

    /**
     *  查证失败后的处理
     * @param context
     * @return
     */
    protected abstract ServiceStatus checkFails(BaseServiceContext context) ;
}
