using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EasyopenSDKCSharp.Request
{
    public class CommonRequest : BaseRequest<EasyopenSDKCSharp.Response.CommonResponse>
    {
        public CommonRequest(string name)
        {
            this.Name = name;
        }

        public CommonRequest(string name, string version)
        {
            this.Name = name;
            this.Version = version;
        }

        public override string GetName()
        {
            return string.Empty;
        }
    }
}
