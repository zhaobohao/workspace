using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Security;
using System.IO;

using System.Text.RegularExpressions;
using System.Security.Cryptography.X509Certificates;

using SDKCSharp.Common;
using System.Collections.Specialized;

namespace SDKCSharp.Client
{
    public class OpenHttp
    {
        public const string CONTENT_TYPE_JSON = "application/json";
        public const string CONTENT_TYPE_STREAM = "application/octet-stream";
        public const string CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
        public const string METHOD_POST = "POST";
        public const string METHOD_GET = "GET";

        public CookieContainer cookieContainer = new CookieContainer();

        private OpenConfig openConfig;

        public OpenHttp(OpenConfig openConfig)
        {
            this.openConfig = openConfig;
        }

        public HttpWebRequest CreateWebRequest(string url)
        {
            return CreateWebRequest(url, null);
        }

        public HttpWebRequest CreateWebRequest(string url, Dictionary<string, string> header)
        {
            var request = (HttpWebRequest)WebRequest.Create(url);
            request.CookieContainer = cookieContainer;
            request.ContinueTimeout = this.openConfig.ConnectTimeoutSeconds * 1000;
            request.ReadWriteTimeout = this.openConfig.ReadTimeoutSeconds * 1000;
            BindHeader(request, header);
            return request;
        }

        /// <summary>
        /// get请求
        /// </summary>
        /// <param name="url"></param>
        /// <returns></returns>
        public string Get(string url, Dictionary<string, string> header)
        {
            var request = CreateWebRequest(url, header);
            request.Method = METHOD_GET;
            var response = (HttpWebResponse)request.GetResponse();
            var responseString = new StreamReader(response.GetResponseStream()).ReadToEnd();
            return responseString;
        }

        /// <summary>
        /// get请求
        /// </summary>
        /// <param name="url"></param>
        /// <returns></returns>
        public string Get(string url)
        {
            return Get(url, null);
        }

        /// <summary>
        /// post请求,发送请求体
        /// </summary>
        /// <param name="url">提交的url</param>
        /// <param name="json">json数据</param>
        /// <param name="header">header</param>
        /// <returns></returns>
        public string PostJsonBody(string url, string json, Dictionary<string, string> header)
        {
            HttpWebRequest request = CreateWebRequest(url, header);
            request.ContentType = CONTENT_TYPE_JSON;
            request.Method = METHOD_POST;

            using (var streamWriter = new StreamWriter(request.GetRequestStream()))
            {
                streamWriter.Write(json);
            }

            var response = (HttpWebResponse)request.GetResponse();
            using (var streamReader = new StreamReader(response.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
                return result;
            }

        }

        /// <summary>
        /// 模拟表单提交
        /// </summary>
        /// <returns>返回结果.</returns>
        /// <param name="url">URL.</param>
        /// <param name="form">Form.</param>
        /// <param name="header">Header.</param>
        /// <param name="method">method，默认POST</param>
        public string RequestFormBody(string url, Dictionary<string, string> form, Dictionary<string, string> header, string method = "POST")
        {
            WebClient webClient = new WebClient();
            // 表单参数
            NameValueCollection postParams = new NameValueCollection();
            foreach (var item in form)
            {
                postParams.Add(item.Key, item.Value);
            }
            if (header != null)
            {
                ICollection<string> keys = header.Keys;
                foreach (string key in keys)
                {
                    webClient.Headers.Add(key, header[key]);
                }
            }
            byte[] byRemoteInfo = webClient.UploadValues(url, method, postParams);
            return Encoding.UTF8.GetString(byRemoteInfo);
        }



        private void BindHeader(HttpWebRequest request, Dictionary<string, string> header)
        {
            if (header == null || header.Count == 0)
            {
                return;
            }
            ICollection<string> keys = header.Keys;
            foreach (string key in keys)
            {
                request.Headers.Add(key, header[key]);
            }
        }


        /// <summary>
        /// post请求，并且文件上传
        /// </summary>
        /// <param name="url">请求url</param>
        /// <param name="form">表单数据</param>
        /// <param name="header">请求头</param>
        /// <param name="files">文件信息</param>
        /// <returns></returns>
        public string PostFile(string url, Dictionary<string, string> form, Dictionary<string, string> header, List<UploadFile> files)
        {
            HttpWebRequest request = CreateWebRequest(url, header);
            request.Method = METHOD_POST;
            // 分隔符
            string boundary = "----" + DateTime.Now.Ticks.ToString("x");
            request.ContentType = string.Format("multipart/form-data; boundary={0}", boundary);

            // 请求流
            var postStream = new MemoryStream();
            #region 处理Form表单请求内容
            // 文件数据模板
            string fileFormdataTemplate =
                "\r\n--" + boundary +
                "\r\nContent-Disposition: form-data; name=\"{0}\"; filename=\"{1}\"" +
                "\r\nContent-Type: application/octet-stream" +
                "\r\n\r\n";
            // 文本数据模板
            string dataFormdataTemplate =
                "\r\n--" + boundary +
                "\r\nContent-Disposition: form-data; name=\"{0}\"" +
                "\r\n\r\n{1}";

            // 是否有上传文件
            bool hasFile = files != null && files.Count > 0;
            if (hasFile)
            {
                // 处理上传文件
                foreach (var fileItem in files)
                {
                    string formdata = null;
                    // 上传文件
                    formdata = string.Format(
                        fileFormdataTemplate,
                        fileItem.Name, //表单键
                        fileItem.FileName);

                    byte[] formdataBytes = null;
                    // 第一行不需要换行
                    if (postStream.Length == 0)
                    {
                        formdataBytes = Encoding.UTF8.GetBytes(formdata.Substring(2, formdata.Length - 2));
                    }
                    else
                    {
                        formdataBytes = Encoding.UTF8.GetBytes(formdata);
                    }
                    postStream.Write(formdataBytes, 0, formdataBytes.Length);

                    // 写入文件内容
                    if (fileItem.FileData != null && fileItem.FileData.Length > 0)
                    {
                        postStream.Write(fileItem.FileData, 0, fileItem.FileData.Length);
                    }
                }
            }
            // 处理文本字段
            foreach (var fieldItem in form)
            {
                string formdata = null;
                {
                    // 上传文本
                    formdata = string.Format(
                        dataFormdataTemplate,
                        fieldItem.Key,
                        fieldItem.Value);
                }

                byte[] formdataBytes = null;
                // 第一行不需要换行
                if (postStream.Length == 0)
                {
                    formdataBytes = Encoding.UTF8.GetBytes(formdata.Substring(2, formdata.Length - 2));
                }
                else
                {
                    formdataBytes = Encoding.UTF8.GetBytes(formdata);
                }
                postStream.Write(formdataBytes, 0, formdataBytes.Length);
            }
            // 结尾
            var footer = Encoding.UTF8.GetBytes("\r\n--" + boundary + "--\r\n");
            postStream.Write(footer, 0, footer.Length);
            #endregion

            request.ContentLength = postStream.Length;

            #region 输入二进制流
            if (postStream != null)
            {
                postStream.Position = 0;
                // 直接写入流
                Stream requestStream = request.GetRequestStream();

                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = postStream.Read(buffer, 0, buffer.Length)) != 0)
                {
                    requestStream.Write(buffer, 0, bytesRead);
                }

                ////debug
                //postStream.Seek(0, SeekOrigin.Begin);
                //StreamReader sr = new StreamReader(postStream);
                //var postStr = sr.ReadToEnd();
                postStream.Close();//关闭文件访问
            }
            #endregion

            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            if (cookieContainer != null)
            {
                response.Cookies = cookieContainer.GetCookies(response.ResponseUri);
            }

            using (Stream responseStream = response.GetResponseStream())
            {
                using (StreamReader myStreamReader = new StreamReader(responseStream, Encoding.UTF8))
                {
                    return myStreamReader.ReadToEnd();
                }
            }
        }

    }
}
