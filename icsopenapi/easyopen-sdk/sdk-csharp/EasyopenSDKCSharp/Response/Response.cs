using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization;

namespace EasyopenSDKCSharp.Response
{
    /// <summary>
    /// 已废弃，查看BaseResponse
    /// </summary>
    /// <typeparam name="T"></typeparam>
    [Obsolete]
    [DataContract]
    public class Response<T>
    {
        private const string SUCCESS_CODE = "0";

        [DataMember]
        public string code {get; set;}

        [DataMember]
        public string msg { get; set; }

        [DataMember]
        public T data;

        public bool IsSuccess()
        {
            return SUCCESS_CODE == this.code;
        }

    }
}
