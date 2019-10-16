using System;
using System.Collections.Generic;
using System.Text;
using SDKCSharp.Common;
using SDKCSharp.Utility;

namespace SDKCSharp.Request
{
    /// <summary>
    /// 接口请求对象，新建的Request要继承这个类
    /// </summary>
    /// <typeparam name="T">对应的Response对象</typeparam>
    public abstract class BaseRequest<T>
    {
        private string method; 
        private string version;

        private string bizContent;
        private object bizModel;

        private List<UploadFile> files;

        public string Method { get => method; }

        public List<UploadFile> Files { set => files = value; }
        public string BizContent { set => bizContent = value; }
        public object BizModel { set => bizModel = value; }
        public string Version { get => version; set => version = value; }

        /// <summary>
        /// 返回接口名
        /// </summary>
        /// <returns></returns>
        public abstract string GetMethod();

        /// <summary>
        /// 返回版本号
        /// </summary>
        /// <returns></returns>
        public virtual string GetVersion()
        {
            return version;
        }

        /// <summary>
        /// 指定HTTP请求method,默认POST
        /// </summary>
        /// <returns>The request method.</returns>
        public virtual RequestMethod GetRequestMethod()
        {
            return RequestMethod.POST;
        }

        public BaseRequest()
        {
            this.method = this.GetMethod();
            this.version = this.GetVersion();
        }

        protected BaseRequest(string name, string version)
        {
            this.method = name;
            this.version = version;
        }

        /// <summary>
        /// 添加上传文件
        /// </summary>
        /// <param name="file">File.</param>
        public void AddFile(UploadFile file)
        {
            if(this.files == null)
            {
                this.files = new List<UploadFile>();
            }
            this.files.Add(file);
        }

        /// <summary>
        /// 创建请求表单
        /// </summary>
        /// <returns></returns>
        public RequestForm CreateRequestForm(OpenConfig openConfig)
        {
            string timestamp = DateTime.Now.ToString(openConfig.TimestampPattern);
            string v = this.version ?? openConfig.DefaultVersion;
            // 业务参数
            string biz_content = BuildBizContent();

            Dictionary<string, string> dict = new Dictionary<string, string>
            {
                [openConfig.MethodName] = this.Method,
                [openConfig.FormatName] = openConfig.FormatType,
                [openConfig.CharsetName] = openConfig.Charset.BodyName,
                [openConfig.SignTypeName] = openConfig.SignType.ToString(),
                [openConfig.TimestampName] = timestamp,
                [openConfig.VersionName] = v,
                [openConfig.DataName] = biz_content
            };

            RequestForm requestForm = new RequestForm(dict)
            {
                Charset = openConfig.Charset,
                RequestMethod = GetRequestMethod(),
                Files = this.files
            };
            return requestForm;
        }

        protected string BuildBizContent()
        {
            if (bizModel != null)
            {
                return JsonUtil.ToJSONString(bizModel);
            }
            else
            {
                return this.bizContent;
            }
        }





    }
}
