using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using EasyopenSDKCSharp.Common;
using EasyopenSDKCSharp.Client;
using EasyopenSDKCSharp.Request;
using EasyopenSDKCSharp.Response;
using EasyopenSDKCSharp.Model;
using EasyopenSDKCSharp.Param;
using EasyopenSDKCSharp.Utility;

namespace EasyopenSDKTest
{
    [TestClass]
    public class OpenClientTest
    {
        static string url = "http://localhost:8080/api";
        static string appKey = "test";
        static string secret = "123456";        

        static OpenClient client = new OpenClient(url, appKey, secret);
        // 公钥
        static string publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbNQvVpS25TzGN7EOK4N/YMmq+sgCL/8y2mHn9F4RPJonEu19GAgISVzPiY8Q9C9i+t9H9KvcjBy+EODdhl2SWpfI76U+ArIHMeSSrfOo322rKOzGAippa+Qh4fb99Al4sLZy739b2Lh+WdYGzbqClnlPBe4M01CVPPEHYjmqoRQIDAQAB";
        static EncryptClient encryptClient = new EncryptClient(url, appKey, secret, publicKey);

        [TestMethod]
        public void TestHello()
        {
            HelloRequest request = new HelloRequest();
            //request.Version = "1.0";

            HelloResponse response = client.Execute<HelloResponse>(request);

            if (response.IsSuccess())
            {
                Assert.IsNotNull(response.data);
            }
            else
            {
                throw new SystemException(response.msg);
            }
        }

        /// <summary>
        /// 正常例子
        /// </summary>
        [TestMethod]
        public void TestGetGoods()
        {
            // 接口请求
            GetGoodsRequest request = new GetGoodsRequest();
            // 请求参数
            GoodsParam param = new GoodsParam();
            // 属性赋值
            param.goods_name = "iphone6";

            // 设置请求参数
            request.Param = param;

            // 发送请求，返回结果
            GetGoodsResponse response = client.Execute<GetGoodsResponse>(request);

            if (response.IsSuccess())
            {
                Assert.IsTrue(response.data is Goods);
                Assert.IsTrue(response.data.goods_name == "苹果iPhoneX");
            }
            else
            {
                throw new SystemException(response.msg);
            }

        }

        /// <summary>
        /// get请求
        /// </summary>
        [TestMethod]
        public void TestGetGoods2()
        {
            // 接口请求
            GetGoodsRequest request = new GetGoodsRequest();
            // 请求参数
            GoodsParam param = new GoodsParam();
            // 属性赋值
            param.goods_name = "iphone6";

            // 设置请求参数
            request.Param = param;
            // GET请求
            request.RequestMethod = RequestMethod.GET;

            // 发送请求，返回结果
            GetGoodsResponse response = client.Execute<GetGoodsResponse>(request);

            if (response.IsSuccess())
            {
                Assert.IsTrue(response.data is Goods);
                Assert.IsTrue(response.data.goods_name == "苹果iPhoneX");
            }
            else
            {
                throw new SystemException(response.msg);
            }

        }

        /// <summary>
        /// JWT
        /// </summary>
        [TestMethod]
        public void TestGetGoodsJwt()
        {
            // 接口请求
            GetGoodsRequest request = new GetGoodsRequest();
            // 请求参数
            GoodsParam param = new GoodsParam();
            // 属性赋值
            param.goods_name = "iphone6";

            // 设置请求参数
            request.Param = param;

            OpenHttp openHttp = new OpenHttp(new OpenConfig());
            string jwt = openHttp.Get(@"http://localhost:8080/jwtLogin", null);

            // 发送请求，返回结果
            GetGoodsResponse response = client.Execute<GetGoodsResponse>(request, jwt);

            if (response.IsSuccess())
            {
                Assert.IsTrue(response.data is Goods);
                Assert.IsTrue(response.data.goods_name == "苹果iPhoneX");
            }
            else
            {
                throw new SystemException(response.msg);
            }

        }

        /// <summary>
        /// 文件上传
        /// </summary>
        [TestMethod]
        public void TestUploadFile()
        {
            // 接口请求
            FileUploadRequest request = new FileUploadRequest();
            // 请求参数
            GoodsParam param = new GoodsParam();
            // 属性赋值
            param.goods_name = "iphone6";

            // 设置请求参数
            request.Param = param;

            List<UploadFile> files = new List<UploadFile>();
            files.Add(new UploadFile("headImg", @"D:\1.txt"));
            files.Add(new UploadFile("idcardImg", @"D:\2.txt"));
            request.Files = files;

            // 发送请求，返回结果
            FileUploadResponse response = client.Execute<FileUploadResponse>(request);

            if (response.IsSuccess())
            {
                Assert.IsTrue(response.data != null);
            }
            else
            {
                throw new SystemException(response.msg);
            }

        }


        /// <summary>
        /// 加密传输模式
        /// </summary>
        [TestMethod]
        public void TestEncryptClient() {
            // 接口请求
            GetGoodsRequest request = new GetGoodsRequest();
            // 请求参数
            GoodsParam param = new GoodsParam();
            // 属性赋值
            param.goods_name = "iphone6";

            // 设置请求参数
            request.Param = param;

            // 发送请求，返回结果
            GetGoodsResponse response = encryptClient.Execute<GetGoodsResponse>(request);

            if (response.IsSuccess())
            {
                Assert.IsTrue(response.data is Goods);
                Assert.IsTrue(response.data.goods_name == "苹果iPhoneX");
            }
            else
            {
                throw new SystemException(response.msg);
            }

        }

        /// <summary>
        /// 返回纯字符串
        /// </summary>
        [TestMethod]
        public void TestLazy()
        {
            // 接口请求
            CommonRequest request = new CommonRequest("goods.get");
            // 请求参数
            Dictionary<string, object> param = new Dictionary<string, object>();
            // 属性赋值
            param["goods_name"] = "iphone6";

            // 设置请求参数
            request.Param = param;

            // 发送请求，返回结果
            CommonResponse response = client.Execute<CommonResponse>(request);

            if (response.IsSuccess())
            {
                Assert.IsTrue(response.data is Dictionary<string, object>);
                Assert.IsTrue(response.data["goods_name"].ToString() == "苹果iPhoneX");
            }
            else
            {
                throw new SystemException(response.msg);
            }
        }

        
    }
}
