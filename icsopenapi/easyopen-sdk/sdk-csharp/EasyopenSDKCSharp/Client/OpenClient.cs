using System;
using System.Web;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Threading.Tasks;

using EasyopenSDKCSharp.Common;
using EasyopenSDKCSharp.Request;
using EasyopenSDKCSharp.Response;
using EasyopenSDKCSharp.Utility;


namespace EasyopenSDKCSharp.Client
{
    /// <summary>
    /// 客户端
    /// </summary>
    public class OpenClient
    {
        private const string AND = "&";
        private const string EQ = "=";

        private static OpenConfig DEFAULT_CONFIG = new OpenConfig();

        private const string ACCEPT_LANGUAGE = "Accept-Language";
        private const string AUTHORIZATION = "Authorization";
        private const string PREFIX_BEARER = "Bearer ";

        private OpenConfig openConfig;

        public OpenConfig OpenConfig
        {
            get { return openConfig; }
            set { openConfig = value; }
        }

        private OpenRequest openRequest;      
  
        public OpenRequest OpenRequest
        {
            get { return openRequest; }
            set { openRequest = value; }
        }

        private string url;
        private string appKey;

        public string AppKey
        {
            get { return appKey; }
            set { appKey = value; }
        }
        private string secret;


        public OpenClient(string url, string appKey, string secret) : this(url, appKey, secret, DEFAULT_CONFIG)
        {
            
        }

        public OpenClient(string url, string appKey, string secret, OpenConfig openConfig)
        {
            this.url = url;
            this.appKey = appKey;
            this.secret = secret;
            this.openConfig = openConfig;
            this.openRequest = new OpenRequest(openConfig);
        }

        /// <summary>
        /// 发送请求
        /// </summary>
        /// <typeparam name="T">返回的Response类</typeparam>
        /// <param name="request">请求对象</param>
        /// <returns>返回Response类</returns>
        public virtual T Execute<T>(BaseRequest<T> request)
        {
            return this.Execute<T>(request, null);
        }

        /// <summary>
        /// 发送请求
        /// </summary>
        /// <typeparam name="T">返回的Response类</typeparam>
        /// <param name="request">请求对象</param>
        /// <param name="jwt">jwt字符串</param>
        /// <returns>返回Response类</returns>
        public virtual T Execute<T>(BaseRequest<T> request, string jwt)
        {
            RequestForm requestForm = request.CreateRequestForm();
            Dictionary<string, object> form = requestForm.Form;
            string dataName = this.openConfig.DataName;
            object data = form[dataName];

            string dataJson = JsonUtil.ToJSONString(data);
          
            dataJson = HttpUtility.UrlEncode(dataJson, System.Text.Encoding.UTF8);

            form[dataName] = dataJson;
            form.Add(this.openConfig.AppKeyName, this.appKey); 
            // 添加秘钥
            string sign = SignUtil.CreateSign(form, this.secret);
            form.Add(this.openConfig.SignName, sign);

            Dictionary<string, string> header = this.buildHeader(jwt);

            string resp = this.doExecute(url, requestForm, header);

            return this.parseResponse<T>(resp, request);
        }

        /// <summary>
        /// 执行请求
        /// </summary>
        /// <param name="url">请求url</param>
        /// <param name="requestForm">请求内容</param>
        /// <param name="header">请求header</param>
        /// <returns>返回服务器响应内容</returns>
        protected virtual String doExecute(String url, RequestForm requestForm, Dictionary<string, string> header)
        {
            return openRequest.Request(this.url, requestForm, header);
        }

        /// <summary>
        /// 解析返回结果
        /// </summary>
        /// <typeparam name="T">返回的Response</typeparam>
        /// <param name="resp">服务器响应内容</param>
        /// <param name="request">请求Request</param>
        /// <returns>返回Response</returns>
        protected virtual T parseResponse<T>(String resp, BaseRequest<T> request) {
            return JsonUtil.ParseObject<T>(resp);
        }


        private Dictionary<string, string> buildHeader(string jwt)
        {
            Dictionary<string, string> header = new Dictionary<string, string>();
            header.Add(ACCEPT_LANGUAGE, this.openConfig.Locale);
            if (!string.IsNullOrEmpty(jwt))
            {
                header.Add(AUTHORIZATION, PREFIX_BEARER + jwt);
            }
            return header;
        }

    }
}
