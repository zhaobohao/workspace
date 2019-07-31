using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using EasyopenSDKCSharp.Utility;

namespace EasyopenSDKTest
{
    [TestClass]
    public class UtilityTest
    {
        [TestMethod]
        public void TestMd5()
        {
            string s = MD5Util.EncryptToUpper("123456");
            Assert.IsTrue(s == "E10ADC3949BA59ABBE56E057F20F883E");
        }

        [TestMethod]
        public void TestRSA()
        {
            String pubKey = @"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCG/iIZZzb16PxKqslkDMYa4tVFb3IVPBpLj4BgHQmDfe843sG4gkJIPXCm7+t6QxIbfDfynBpqZJLvu0c6E7TqlCtynBIlRFOBZrQVNEFkaanR2Kln3vd3CIidR571UstOC32XDyqAQNlvjD19zeIDVfmLa0Q+Or0zaxY99QwBHwIDAQAB";
            String mi = @"QU5vDnQ1ukj8GsauokFlgcB/g61U882tj82wHGrrqHEnvaga+4cXjML9RhjpZtKqwDGZTCujsmpynDk4qek6IGOQ/oxdWLwV4ZNjfa/oqA8OFDothVUT8wpqCu9kOYHrTdGybmXD0dB2Iy1/AMQTAgPNNXXiRXdvsz9xWYTV6z8=";

            // 用公钥解密
            string m = RSAUtil.DecryptByPublicKey(mi, pubKey);
            Assert.IsTrue(m == "1234567890123456");
        }

        [TestMethod]
        public void TestRSA2()
        {
            RSA rsa = new RSA();
            // 生成一组公私钥
            RSA.RSAKEY keyStore = rsa.GetKey();
            string pubKey = keyStore.PublicKey;
            string priKey = keyStore.PrivateKey;

            string ming = "1234567890123456";

            // 私钥加密，公钥解密
            string mi = rsa.EncryptByPrivateKey(ming, priKey);
            string ming2 = rsa.DecryptByPublicKey(mi, pubKey);
            Assert.IsTrue(ming == ming2);

            // 公钥加密，私钥解密
            mi = rsa.EncryptByPublicKey(ming, pubKey);
            ming2 = rsa.DecryptByPrivateKey(mi, priKey);
            Assert.IsTrue(ming == ming2);
        }

        [TestMethod]
        public void TestAES() { 
            String content = "我爱你";
            String password = "1234567890123456";

            String ret2 = AESUtil.EncryptToBase64String(content, password);
            String content3 = AESUtil.DecryptFromBase64String(ret2, password);
            Assert.IsTrue(content == (content3));
        }
    }
}
