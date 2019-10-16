using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using SDKCSharp.Client;
using SDKCSharp.Common;
using SDKCSharp.Model;
using SDKCSharp.Request;
using SDKCSharp.Response;
using SDKCSharp.Utility;

namespace SDKTest
{
    class MainClass
    {
        static string url = "http://localhost:8081";
        static string appId = "201904035630907729292csharp";
        // 开发者私钥, PKCS1 2048
        static string privateKey = "MIIEowIBAAKCAQEA5+OvJxeSzf44NxQ/cl7Ii+BzPg2k6sRcvH4ffOtU5Dzq1/oEvg02nxIhmwOHBZmjbmuUu0aLsfglUTAwqfXftfAKZidshsgj9NNh0/kxk0avRZ1UoljWGz/FxVZA0ogbxxhohPZ9jWcD+eBQcIwF2DtHfAJqWWZrYFnCMeHD8mPzxo2kwXSvDzi0vf9I2tKiYvNG26a9FqeYtPOoi81sdS3+70HOMdxP8ejXtyfnKpKz7Dx506LCIRS5moWS3Q5eTLV3NGX/1CSJ8wpQA2DAQTjVhX5eVu7Yqz12t8W+sjWM/tHUR6cgwYYR10p7tSCeCPzkigjGxKm4cYXWtATQJQIDAQABAoIBAHFDsgrrJca+NKEan77ycwx3jnKx4WrWjOF4zVKL9AQjiSYDNgvKknJyPb3kpC/lEoHdxGERHSzJoxib7DkoIqRQYhPxj73pxj5QfYk3P7LLJNNg/LTrpXDb3nL8JV9wIflGf87qQvstZTDJEyFWE4jBs7Hr0BxovWvri8InnzkmERJ1cbGJgNHe1Y3Zo2tw0yaHxQCxLuajP+notRZhD9bEp7uKeI0w9AvlW6k8m/7y10F0BK/TlyW8rQiEC391yOiRYoMcUh4hd2Q9bMx3jngZgX8PXIvZZcup4/pvWlv1alwhB2tsnLdazP62r1MO80vLyLunzGO+7WwCjEYlVaECgYEA+lQRFmbhKaPuAuXMtY31Fbga8nedka5TjnEV7+/kX+yowE2OlNujF+ZG8UTddTxAGv56yVNi/mjRlgD74j8z0eOsgvOq9mwbCrgLhLo51H9O/wAxtb+hBKtC5l50pBr4gER6d8W6EQNTSGojnMIaLXTkAZ5Qf6Z8e2HFVdOn0X0CgYEA7SSrTokwzukt5KldNu5ukyyd+C3D1i6orbg6qD73EP9CfNMfGSBn7dDv9wMSJH01+Ty+RgTROgtjGRDbMJWnfbdt/61NePr9ar5sb6Nbsf7/I0w7cZF5dsaFYgzaOfQYquzXPbLQHkpMT64bqpv/Mwy4F2lFvaYWY5fA4pC2uckCgYEAg75Ym9ybJaoTqky8ttQ2Jy8UZ4VSVQhVC0My02sCWwWXLlXi8y7An+Rec73Ve0yxREOn5WrQT6pkmzh7V/ABWrYi5WxODpCIjtSbo0fLBa3Wqle00b0/hdCITetqIa/cFs1zUrOqICgK3bKWeXqiAkhhcwSZwwSgwOKM04Wn7ZUCgYBvhHX2mbdVJfyJ8kc+hMOE/E9RHRxiBVEXWHJlGi8PVCqNDq8qHr4g7Mdbzprig+s0yKblwHAvrpkseWvKHiZEjVTyDipHgShY4TGXEigVvUd37uppTrLi8xpYcJjS9gH/px7VCdiq1d+q/MJP6coJ1KphgATm2UrgDMYNBWaYWQKBgEHRxrmER7btUF60/YgcqPHFc8RpYQB2ZZE0kyKGDqk2Data1XYUY6vsPAU28yRLAaWr/D2H17iyLkxP80VLm6QhifxCadv90Q/Wl1DFfOJQMW6avyQ0so6G0wFq/LJxaFK4iLXQn1RJnmTp6BYiJMmK2BhFbRzw8ssMoF6ad2rr";
        // 开放平台给的公钥
        // 前往SOP-ADMIN，ISV管理--秘钥管理，选择PKCS1，生成平台提供的公私钥，然后把【平台公钥】放到这里
        static string publicKeyPlatform = "";

        // 从文件中加载
        //static string filePath = "/Users/thc/logs/priKey.txt";
        //static OpenClient client = new OpenClient(url, appId, filePath, true);

        // 声明一个就行
        static OpenClient client = new OpenClient(url, appId, privateKey, publicKeyPlatform);


        public static void Main(string[] args)
        {
            TestGet();
            Console.WriteLine("--------------------");
            TestCommon();
            Console.WriteLine("--------------------");
            TestUpload();
        }

        // 标准用法
        private static void TestGet()
        {
            // 创建请求对象
            GetStoryRequest request = new GetStoryRequest();
            // 请求参数
            GetStoryModel model = new GetStoryModel();
            model.Name = "白雪公主";
            request.BizModel = model;

            // 发送请求
            GetStoryResponse response = client.Execute(request);

            if (response.IsSuccess())
            {
                // 返回结果
                Console.WriteLine("成功！response:{0}\n响应原始内容:{1}", JsonUtil.ToJSONString(response), response.Body);
            }
            else
            {
                Console.WriteLine("错误, code:{0}, msg:{1}, subCode:{2}, subMsg:{3}",
                    response.Code, response.Msg, response.SubCode, response.SubMsg);
            }
        }

        // 懒人版，如果不想添加Request,Response,Model。可以用这种方式，返回Dictionary<string, object>，后续自己处理
        private static void TestCommon()
        {
            // 创建请求对象
            CommonRequest request = new CommonRequest("alipay.story.find");
            // 请求参数
            Dictionary<string, string> bizModel = new Dictionary<string, string>
            {
                ["name"] = "白雪公主"
            };

            request.BizModel = bizModel;

            // 发送请求
            CommonResponse response = client.Execute(request);

            if (response.IsSuccess())
            {
                // 返回结果
                string body = response.Body;
                Dictionary<string, object> dict = JsonUtil.ParseToDictionary(body);

                Console.WriteLine("Dictionary内容:");
                foreach (var item in dict)
                {
                    Console.WriteLine("{0}:{1}", item.Key, item.Value);
                }
            }
            else
            {
                Console.WriteLine("错误, code:{0}, msg:{1}, subCode:{2}, subMsg:{3}",
                    response.Code, response.Msg, response.SubCode, response.SubMsg);
            }
        }

        private static void TestUpload()
        {
            DemoFileUploadRequest request = new DemoFileUploadRequest();

            DemoFileUploadModel model = new DemoFileUploadModel
            {
                Remark = "上传文件参数"
            };
            request.BizModel = model;

            string workDir = Environment.CurrentDirectory;
            int index = workDir.LastIndexOf(@"/bin/Debug", StringComparison.Ordinal);
            string root = workDir.Substring(0, index);

            Console.WriteLine("当前目录{0}", root);

            // 文件上传
            // 将当前目录下的两个文件上传到服务器
            request.AddFile(new UploadFile("file1", root + "/file1.txt"));
            request.AddFile(new UploadFile("file2", root + "/file2.txt"));

            DemoFileUploadResponse response = client.Execute(request);
            if (response.IsSuccess())
            {
                List<DemoFileUploadResponse.FileMeta> responseFiles = response.Files;
                Console.WriteLine("您上传的文件信息：");
                responseFiles.ForEach(file =>
                {
                    Console.WriteLine("上传的文件名：{0}，文件大小：{1}，文件内容：{2}", file.Filename, file.Size, file.Content);
                }
                );
            }
            else
            {
                Console.WriteLine("错误, code:{0}, msg:{1}, subCode:{2}, subMsg:{3}",
                     response.Code, response.Msg, response.SubCode, response.SubMsg);
            }
        }
    }
}
