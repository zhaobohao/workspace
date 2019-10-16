using System;
using Newtonsoft.Json;

namespace SDKCSharp.Response
{
    /// <summary>
    /// 返回的Response，新建Response要继承这个类
    /// </summary>
    public abstract class BaseResponse
    {
        
        /// <summary>
        /// 状态码，0表示成功，其它都是失败
        /// </summary>
        [JsonProperty("code")]
        public string Code { get; set; }

        /// <summary>
        /// 消息，如果有错误则为错误信息
        /// </summary>
        [JsonProperty("msg")]
        public string Msg { get; set; }

        /// <summary>
        /// 错误状态码
        /// </summary>
        /// <value>The sub code.</value>
        [JsonProperty("sub_code")]
        public string SubCode { get; set; }

        /// <summary>
        /// 错误消息
        /// </summary>
        /// <value>The sub message.</value>
        [JsonProperty("sub_msg")]
        public string SubMsg { get; set; }

        /// <summary>
        /// 响应原始内容
        /// </summary>
        [JsonIgnore]
        public string Body { get; set; }

        /// <summary>
        /// 是否成功
        /// </summary>
        /// <returns></returns>
        public bool IsSuccess()
        {
            return string.IsNullOrEmpty(SubCode);
        }
    }
}
