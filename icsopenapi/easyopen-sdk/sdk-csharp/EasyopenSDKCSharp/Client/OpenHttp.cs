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

using EasyopenSDKCSharp.Common;

namespace EasyopenSDKCSharp.Client
{
    public class OpenHttp
    {
        public const string CONTENT_TYPE_JSON = "application/json";
        public const string CONTENT_TYPE_STREAM = "application/octet-stream";
        public static string REQUEST_METHOD_POST = RequestMethod.POST.ToString();

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
            bindHeader(request, header);
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
            var request = CreateWebRequest(url, header);
            request.ContentType = CONTENT_TYPE_JSON;
            request.Method = REQUEST_METHOD_POST;

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

        private void bindHeader(HttpWebRequest request, Dictionary<string, string> header)
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
        public string PostFile(string url, Dictionary<string, object> form, Dictionary<string, string> header, List<UploadFile> files)
        {
            Encoding ENCODING_UTF8 = Encoding.UTF8;

            using (MemoryStream memoryStream = new MemoryStream())
            {
                // 1.分界线
                string boundary = string.Format("----{0}", DateTime.Now.Ticks.ToString("x")),       // 分界线可以自定义参数
                    appendBoundary = string.Format("--{0}\r\n", boundary),
                    endBoundary = string.Format("\r\n--{0}--\r\n", boundary);

                byte[] beginBoundaryBytes = ENCODING_UTF8.GetBytes(appendBoundary),
                    endBoundaryBytes = ENCODING_UTF8.GetBytes(endBoundary);


                StringBuilder payload = new StringBuilder();

                // 2.组装 上传文件附加携带的参数 到内存流中
                if (form != null && form.Count > 0)
                {
                    ICollection<string> keys = form.Keys;
                    foreach (string key in keys)
                    {
                        string boundaryBlock = string.Format("{0}Content-Disposition: form-data; name=\"{1}\"\r\n\r\n{2}\r\n", appendBoundary, key, form[key]);
                        byte[] boundaryBlockBytes = ENCODING_UTF8.GetBytes(boundaryBlock);
                        memoryStream.Write(boundaryBlockBytes, 0, boundaryBlockBytes.Length);
                    }
                }

                // 3.组装文件头数据体 到内存流中
                foreach (UploadFile uploadFile in files)
                {
                    string boundaryBlock = string.Format("{0}Content-Disposition: form-data; name=\"{1}\"; filename=\"{2}\"\r\nContent-Type: application/octet-stream\r\n\r\n", appendBoundary, uploadFile.Name, uploadFile.FileName);
                    byte[] boundaryBlockBytes = ENCODING_UTF8.GetBytes(boundaryBlock);
                    memoryStream.Write(boundaryBlockBytes, 0, boundaryBlockBytes.Length);
                    memoryStream.Write(uploadFile.FileData, 0, uploadFile.FileData.Length);
                }

                // 4.组装结束分界线数据体 到内存流中
                memoryStream.Write(endBoundaryBytes, 0, endBoundaryBytes.Length);

                // 5.获取二进制数据,最终需要发送给服务器的数据
                byte[] postBytes = memoryStream.ToArray();

                // 6.HttpWebRequest 组装
                HttpWebRequest webRequest = CreateWebRequest(url, header);
                webRequest.Method = REQUEST_METHOD_POST;
                webRequest.ContentType = string.Format("multipart/form-data; boundary={0}", boundary);
                webRequest.ContentLength = postBytes.Length;
                bindHeader(webRequest, header);
                if (Regex.IsMatch(url, "^https://"))
                {
                    ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls;
                    ServicePointManager.ServerCertificateValidationCallback = CheckValidationResult;
                }

                // 7.写入上传请求数据
                using (Stream requestStream = webRequest.GetRequestStream())
                {
                    requestStream.Write(postBytes, 0, postBytes.Length);
                }
                // 8.获取响应
                using (HttpWebResponse webResponse = (HttpWebResponse)webRequest.GetResponse())
                {
                    using (StreamReader reader = new StreamReader(webResponse.GetResponseStream(), ENCODING_UTF8))
                    {
                        string body = reader.ReadToEnd();
                        return body;
                    }
                }

            }

        }

        static bool CheckValidationResult(object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors errors)
        {
            return true;
        }
    }
}
