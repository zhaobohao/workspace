using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using EasyopenSDKCSharp.Response;

namespace EasyopenSDKCSharp.Request
{
    public class HelloRequest:BaseRequest<HelloResponse>
    {
        public override string GetName()
        {
            return "hello";
        }
    }
}
