using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDKCSharp.Common
{
    public class RequestForm
    {

        /// <summary>
        /// 请求表单内容
        /// </summary>
        public Dictionary<string, string> Form { get; set; }

        /// <summary>
        /// 上传文件
        /// </summary>
        public List<UploadFile> Files { get; set; }

        /// <summary>
        /// 指定或者设置字符集
        /// </summary>
        /// <value>The charset.</value>
        public Encoding Charset { get; set; }

        /// <summary>
        /// 指定或设置HTTP请求method
        /// </summary>
        /// <value>The request method.</value>
        public RequestMethod RequestMethod { get; set; } = RequestMethod.POST;

        public RequestForm(Dictionary<string, string> form)
        {
            this.Form = form;
        }
         
    }
}
