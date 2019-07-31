using System;
using System.Xml;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

using Org.BouncyCastle.Asn1.Pkcs;
using Org.BouncyCastle.Asn1.X509;
using Org.BouncyCastle.Crypto.Generators;
using Org.BouncyCastle.Crypto.Parameters;
using Org.BouncyCastle.Math;
using Org.BouncyCastle.Pkcs;
using Org.BouncyCastle.Security;
using Org.BouncyCastle.Crypto.Engines;
using Org.BouncyCastle.X509;
using Org.BouncyCastle.Crypto;
using Org.BouncyCastle.Asn1;
using Org.BouncyCastle.Crypto.Encodings;

namespace EasyopenSDKCSharp.Utility
{
    public class RSAUtil
    {

        static Encoding UTF8 = Encoding.UTF8;
        static RSA rsa = new RSA();

        /// <summary>
        /// 公钥解密
        /// </summary>
        /// <param name="data"></param>
        /// <param name="publicKey"></param>
        /// <returns></returns>
        public static string DecryptByPublicKey(string data, string publicKey)
        {
            return rsa.DecryptByPublicKey(data, publicKey);
        }

        /// <summary>
        /// 公钥加密
        /// </summary>
        /// <param name="data">加密内容</param>
        /// <param name="publicKey">公钥（Base64编码后的）</param>
        /// <returns>返回Base64内容</returns>
        public static string EncryptByPublicKey(string data, string publicKey)
        {
            return rsa.EncryptByPublicKey(data, publicKey);
        }

        
    }




}
