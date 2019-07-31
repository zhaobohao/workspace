using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using EasyopenSDKCSharp.Common;
using EasyopenSDKCSharp.Response;
using EasyopenSDKCSharp.Utility;

namespace EasyopenSDKCSharp.Request
{
    /// <summary>
    /// 接口请求对象，新建的Request要继承这个类
    /// </summary>
    /// <typeparam name="T">对应的Response对象</typeparam>
    public abstract class BaseRequest<T>
    {
        private static string FORMAT_TYPE = SdkConfig.FORMAT_TYPE;
        private static string TIMESTAMP_PATTERN = SdkConfig.TIMESTAMP_PATTERN;
        private static RequestMethod METHOD_POST = RequestMethod.POST;


        private string name;
        /// <summary>
        /// 接口名
        /// </summary>
        public string Name
        {
            get { return name; }
            set { name = value; }
        }


        private string version;
        /// <summary>
        /// 版本号
        /// </summary>
        public string Version
        {
            get { return version; }
            set { version = value; }
        }

        private object data;
        /// <summary>
        /// 设置参数
        /// </summary>
        public object Data
        {
            get { return data; }
            set { data = value; }
        }
        /// <summary>
        /// 设置参数，同Data
        /// </summary>
        [IgnoreSign]
        public object Param
        {
            get { return data; }
            set { data = value; }
        }

        /// <summary>
        /// 时间戳
        /// </summary>
        private string timestamp = DateTime.Now.ToString(TIMESTAMP_PATTERN);
        public string Timestamp
        {
            get { return timestamp; }
            set { timestamp = value; }
        }

        private string access_token = string.Empty;
        public string Access_token
        {
            get { return access_token; }
            set { access_token = value; }
        }

        private string format = SdkConfig.FORMAT_TYPE;
        /// <summary>
        /// 传输格式，默认json
        /// </summary>
        public string Format
        {
            get { return format; }
            set { format = value; }
        }



        private RequestMethod requestMethod = METHOD_POST;
        /// <summary>
        /// 请求方式，默认POST
        /// </summary>
        [IgnoreSign]
        public RequestMethod RequestMethod
        {
            get { return requestMethod; }
            set { requestMethod = value; }
        }

        private List<UploadFile> files;
        /// <summary>
        /// 上传文件
        /// </summary>
        [IgnoreSign]
        public List<UploadFile> Files
        {
            get { return files; }
            set { files = value; }
        }



        public BaseRequest()
        {
            this.name = this.GetName();
            this.version = this.GetVersion();
        }

        public BaseRequest(string name, string version)
        {
            this.name = name;
            this.version = version;
        }

        /// <summary>
        /// 返回接口名
        /// </summary>
        /// <returns></returns>
        /// 
        public abstract string GetName();

        /// <summary>
        /// 返回版本号
        /// </summary>
        /// <returns></returns>
        public virtual string GetVersion()
        {
            return SdkConfig.DEFAULT_VERSION;
        }

        /// <summary>
        /// 创建请求表单
        /// </summary>
        /// <returns></returns>
        public RequestForm CreateRequestForm()
        {
            Dictionary<string, object> dict = ClassUtil.ConvertObjectToDictionary(this);
            RequestForm requestForm = new RequestForm(dict);
            requestForm.Files = this.files;
            return requestForm;
        }


    }
}
