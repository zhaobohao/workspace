using System;
using SDKCSharp.Common;
using SDKCSharp.Response;

namespace SDKCSharp.Request
{
    public class GetStoryRequest : BaseRequest<GetStoryResponse>
    {
        public override string GetMethod()
        {
            return "alipay.story.find";
        }
    }

}
