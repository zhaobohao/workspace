using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EasyopenSDKCSharp.Common
{
    public class RequestForm
    {
        private RequestMethod requestMethod = RequestMethod.POST;

        /// <summary>
        /// 请求方式,POST,GET
        /// </summary>
        public RequestMethod RequestMethod
        {
            get { return requestMethod; }
            set { requestMethod = value; }
        }

        private Dictionary<string, object> form;

        /// <summary>
        /// 请求表单内容
        /// </summary>
        public Dictionary<string, object> Form
        {
            get { return form; }
            set { form = value; }
        }

        private List<UploadFile> files;

        /// <summary>
        /// 上传文件
        /// </summary>
        public List<UploadFile> Files
        {
            get { return files; }
            set { files = value; }
        }

        public RequestForm(Dictionary<string, object> form)
        {
            this.form = form;
        }

    }
}
