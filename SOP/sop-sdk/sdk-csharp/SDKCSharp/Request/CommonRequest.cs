using System;
using SDKCSharp.Response;

namespace SDKCSharp.Request
{
    public class CommonRequest : BaseRequest<CommonResponse>
    {
        public CommonRequest(string method): base(method, null)
        {
           
        }

        public CommonRequest(string method, string version): base(method, version)
        {
           
        }

        public override string GetMethod()
        {
            return "";
        }

    }
}
