using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using Microsoft.CSharp;
//使用JavaScriptSerializer方式需要引入的命名空间，这个在程序集System.Web.Extensions.dll.中
using System.Web.Script.Serialization;

namespace EasyopenSDKCSharp.Utility
{
    public class JsonUtil
    {

        public const string EMPTY_JSON = "{}";


        /// <summary>
        /// JSON字符串转化成对象
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="json"></param>
        /// <returns></returns>
        public static T ParseObject<T>(string json)
        {
            JavaScriptSerializer js = new JavaScriptSerializer();
            return js.Deserialize<T>(json);// //反序列化
        }

        /// <summary>
        /// 对象转换成json字符串
        /// </summary>
        /// <param name="obj"></param>
        /// <returns></returns>
        public static string ToJSONString(object obj) {
            if (obj == null)
            {
                return EMPTY_JSON;
            }
            JavaScriptSerializer js = new JavaScriptSerializer();
            return js.Serialize(obj);//序列化
        }
    }
}
