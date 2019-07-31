using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EasyopenSDKCSharp.Request
{
    public class GetGoodsRequest:BaseRequest<EasyopenSDKCSharp.Response.GetGoodsResponse>
    {
        public override string GetName()
        {
            return "goods.get";
        }     
    }
}
