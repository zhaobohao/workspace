using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using EasyopenSDKCSharp;

namespace EasyopenSDKCSharp.Common
{
    public class OpenConfig
    {

        private String successCode = SdkConfig.SUCCESS_CODE;
        
        /// <summary>
        /// 返回码成功值
        /// </summary>
        public String SuccessCode
        {
            get { return successCode; }
            set { successCode = value; }
        }

        private String defaultVersion = SdkConfig.DEFAULT_VERSION;
        /// <summary> 
        /// 默认版本号 
        /// </summary>
        public String DefaultVersion
        {
            get { return defaultVersion; }
            set { defaultVersion = value; }
        }

        private String apiName = "name";
        /// <summary> 
        /// 接口属性名 
        /// </summary>
        public String ApiName
        {
            get { return apiName; }
            set { apiName = value; }
        }

        private String versionName = "version";
        /// <summary> 
        /// 版本号名称 
        /// </summary>
        public String VersionName
        {
            get { return versionName; }
            set { versionName = value; }
        }

        private String appKeyName = "app_key";
        /// <summary> 
        /// appKey名称 
        /// </summary>
        public String AppKeyName
        {
            get { return appKeyName; }
            set { appKeyName = value; }
        }

        private String dataName = "data";
        /// <summary> 
        /// data名称 
        /// </summary>
        public String DataName
        {
            get { return dataName; }
            set { dataName = value; }
        }

        private String timestampName = "timestamp";
        /// <summary> 
        /// 时间戳名称 
        /// </summary>
        public String TimestampName
        {
            get { return timestampName; }
            set { timestampName = value; }
        }

        private String timestampPattern = "yyyy-MM-dd HH:mm:ss";
        /// <summary> 
        /// 时间戳格式 
        /// </summary>
        public String TimestampPattern
        {
            get { return timestampPattern; }
            set { timestampPattern = value; }
        }

        private String signName = "sign";
        /// <summary> 
        /// 签名串名称 
        /// </summary>
        public String SignName
        {
            get { return signName; }
            set { signName = value; }
        }

        private String formatName = "format";
        /// <summary> 
        /// 格式化名称 
        /// </summary>
        public String FormatName
        {
            get { return formatName; }
            set { formatName = value; }
        }

        private String formatType = "json";
        /// <summary> 
        /// 格式类型 
        /// </summary>
        public String FormatType
        {
            get { return formatType; }
            set { formatType = value; }
        }

        private String accessTokenName = "access_token";
        /// <summary> accessToken名称 
        /// </summary>
        public String AccessTokenName
        {
            get { return accessTokenName; }
            set { accessTokenName = value; }
        }

        private String locale = "zh-CN";
        /// <summary> 
        /// 国际化语言 
        /// </summary>
        public String Locale
        {
            get { return locale; }
            set { locale = value; }
        }

        private String responseCodeName = "code";
        /// <summary> 
        /// 响应code名称 
        /// </summary>
        public String ResponseCodeName
        {
            get { return responseCodeName; }
            set { responseCodeName = value; }
        }

        private int connectTimeoutSeconds = 10;
        /// <summary> 
        /// 请求超时时间 
        /// </summary>
        public int ConnectTimeoutSeconds
        {
            get { return connectTimeoutSeconds; }
            set { connectTimeoutSeconds = value; }
        }

        private int readTimeoutSeconds = 10;
        /// <summary> 
        /// http读取超时时间 
        /// </summary>
        public int ReadTimeoutSeconds
        {
            get { return readTimeoutSeconds; }
            set { readTimeoutSeconds = value; }
        }



    }
}
