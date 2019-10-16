using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Security.Cryptography;

namespace SDKCSharp.Utility
{
    /// <summary>
    /// AES加解密.
    /// 字符集:UTF-8
    /// 算法模式:ECB
    /// 补码方式:PKCS7Padding
    /// 加密结果编码方式:Base64
    /// </summary>
    public class AESUtil
    {
        private static Encoding ENCODING_UTF8 = Encoding.UTF8;

        /// <summary>
        ///  AES 加密
        /// </summary>
        /// <param name="data">明文（待加密）</param>
        /// <param name="password">密文</param>
        /// <returns>返回Base64字符串</returns>
        public static string EncryptToBase64String(string data, string password)
        {
            if (string.IsNullOrEmpty(data)) return null;
            Byte[] toEncryptArray = ENCODING_UTF8.GetBytes(data);

            RijndaelManaged rm = new RijndaelManaged
            {
                Key = ENCODING_UTF8.GetBytes(password),
                Mode = CipherMode.ECB,
                Padding = PaddingMode.PKCS7
            };

            ICryptoTransform cTransform = rm.CreateEncryptor();
            Byte[] resultArray = cTransform.TransformFinalBlock(toEncryptArray, 0, toEncryptArray.Length);

            return Convert.ToBase64String(resultArray, 0, resultArray.Length);
        }
        /// <summary>
        ///  AES 解密
        /// </summary>
        /// <param name="data">密文（待解密）</param>
        /// <param name="password">密码</param>
        /// <returns>返回明文</returns>
        public static string DecryptFromBase64String(string data, string password)
        {
            if (string.IsNullOrEmpty(data)) return null;
            Byte[] toEncryptArray = Convert.FromBase64String(data);

            RijndaelManaged rm = new RijndaelManaged
            {
                Key = ENCODING_UTF8.GetBytes(password),
                Mode = CipherMode.ECB,
                Padding = PaddingMode.PKCS7
            };

            ICryptoTransform cTransform = rm.CreateDecryptor();
            Byte[] resultArray = cTransform.TransformFinalBlock(toEncryptArray, 0, toEncryptArray.Length);

            return ENCODING_UTF8.GetString(resultArray);
        }
    }
}
