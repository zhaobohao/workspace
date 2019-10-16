using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using SDKCSharp;
using SDKCSharp.Utility;

namespace SDKCSharp.Common
{
    public class OpenConfig
    {
        public static DataNameBuilder DATA_NAME_BUILDER = new DefaultDataNameBuilder();

        /// <summary>
        /// 返回码成功值
        /// </summary>
        public string SuccessCode { get; set; } = "10000";

        /// <summary> 
        /// 默认版本号 
        /// </summary>
        public string DefaultVersion { get; set; } = "1.0";

        /// <summary>
        /// 字符编码
        /// </summary>
        /// <value>The charset.</value>
        public Encoding Charset { get; set; } = Encoding.UTF8;

        /// <summary>
        /// 签名类型
        /// </summary>
        /// <value>The type of the sign.</value>
        public SignType SignType { get; set; } = SignType.RSA2;

        /// <summary> 
        /// 格式类型 
        /// </summary>
        public string FormatType { get; set; } = "json";

        /// <summary> 
        /// 时间戳格式 
        /// </summary>
        public string TimestampPattern { get; set; } = "yyyy-MM-dd HH:mm:ss";


        /// <summary> 
        /// 接口属性名 
        /// </summary>
        public string MethodName { get; set; } = "method";

        /// <summary> 
        /// 版本号名称 
        /// </summary>
        public string VersionName { get; set; } = "version";

        /// <summary>
        /// 编码名称
        /// </summary>
        /// <value>The name of the charset.</value>
        public string CharsetName { get; set; } = "charset";

        /// <summary> 
        /// appKey名称 
        /// </summary>
        public string AppKeyName { get; set; } = "app_id";

        /// <summary> 
        /// data名称 
        /// </summary>
        public string DataName { get; set; } = "biz_content";

        /// <summary> 
        /// 时间戳名称 
        /// </summary>
        public string TimestampName { get; set; } = "timestamp";

        /// <summary> 
        /// 签名串名称 
        /// </summary>
        public string SignName { get; set; } = "sign";

        /// <summary>
        /// 签名类型名称
        /// </summary>
        /// <value>The name of the sign type.</value>
        public string SignTypeName { get; set; } = "sign_type";

        /// <summary> 
        /// 格式化名称 
        /// </summary>
        public string FormatName { get; set; } = "format";



        /// <summary> accessToken名称 
        /// </summary>
        public string AccessTokenName { get; set; } = "app_auth_token";

        /// <summary> 
        /// 国际化语言 
        /// </summary>
        public string Locale { get; set; } = "zh-CN";

        /// <summary> 
        /// 响应code名称 
        /// </summary>
        public string ResponseCodeName { get; set; } = "code";

        /// <summary>
        /// 错误响应节点
        /// </summary>
        /// <value>The name of the error response.</value>
        public string ErrorResponseName { get; set; } = "error_response";

        /// <summary> 
        /// 请求超时时间 
        /// </summary>
        public int ConnectTimeoutSeconds { get; set; } = 10;

        /// <summary> 
        /// http读取超时时间 
        /// </summary>
        public int ReadTimeoutSeconds { get; set; } = 10;



        /// <summary>
        /// 节点名称构造器
        /// </summary>
        /// <value>The data name builder.</value>
        public DataNameBuilder DataNameBuilder { get; set; } = DATA_NAME_BUILDER;
    }
}
