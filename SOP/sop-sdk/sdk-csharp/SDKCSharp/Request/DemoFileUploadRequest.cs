using System;
using SDKCSharp.Response;

namespace SDKCSharp.Request
{
    public class DemoFileUploadRequest : BaseRequest<DemoFileUploadResponse>
    {
        public override string GetMethod()
        {
            return "demo.file.upload";
        }
    }
}
