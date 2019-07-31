using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using EasyopenSDKCSharp.Response;

namespace EasyopenSDKCSharp.Request
{
    public class FileUploadRequest : BaseRequest<FileUploadResponse>
    {
        public override string GetName()
        {
            return "file.upload";
        }
    }
}
