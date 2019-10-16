using System;
namespace SDKCSharp.Common
{
    /// <summary>
    /// 将方法名中的"."转成"_"并在后面追加"_response"。
    /// 如：alipay.trade.order.settle --> alipay_trade_order_settle_response。
    /// </summary>
    public class DefaultDataNameBuilder : DataNameBuilder
    {

        private const char DOT = '.';
        private const char UNDERLINE = '_';
        private const string DATA_SUFFIX = SopSdkConstants.DATA_SUFFIX;

        public string Build(string method)
        {
            return method.Replace(DOT, UNDERLINE) + DATA_SUFFIX;
        }
    }
}
