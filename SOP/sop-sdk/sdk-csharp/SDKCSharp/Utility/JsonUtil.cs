using System;
using System.Collections;
using System.Collections.Generic;
using Newtonsoft.Json;
using SDKCSharp.Request;

namespace SDKCSharp.Utility
{
    /// <summary>
    /// JSON序列化/反序列化工具
    /// 使用Newtonsoft.Json组件，详见：https://www.newtonsoft.com/json
    /// </summary>
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
            return JsonConvert.DeserializeObject<T>(json);// //反序列化
        }

        /// <summary>
        /// json字符串转换成Dictionary
        /// </summary>
        /// <returns>The to dictionary.</returns>
        /// <param name="json">Json.</param>
        public static Dictionary<string, object> ParseToDictionary(string json)
        {
            return JsonConvert.DeserializeObject<Dictionary<string, object>>(json);
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
            return JsonConvert.SerializeObject(obj);
        }
    }
}
