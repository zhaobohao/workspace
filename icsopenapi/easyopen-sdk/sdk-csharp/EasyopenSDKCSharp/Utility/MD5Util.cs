using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Security.Cryptography;

namespace EasyopenSDKCSharp.Utility
{
    public class MD5Util
    {
        /// <summary>
        /// MD5加密，全部大写
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public static string EncryptToUpper(string input)
        {
            return Encrypt(input).ToUpper();
        }

        /// <summary>
        /// 返回长度16串,小写
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public static String Encrypt16(String input)
        {
            return Encrypt(input).Substring(8, 16);
        }

        /// <summary>
        /// MD5加密，全部小写
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public static string Encrypt(string input)
        {
            return Encrypt(Encoding.UTF8.GetBytes(input));
        }

        /// <summary>
        /// MD5加密
        /// </summary>
        /// <param name="inputData"></param>
        /// <returns>返回小写字母</returns>
        public static string Encrypt(byte[] inputData)
        {
            // Create a new instance of the MD5CryptoServiceProvider object.
            MD5CryptoServiceProvider md5Hasher = new MD5CryptoServiceProvider();

            // Convert the input string to a byte array and compute the hash.
            byte[] data = md5Hasher.ComputeHash(inputData);

            // Create a new Stringbuilder to collect the bytes
            // and create a string.
            StringBuilder sBuilder = new StringBuilder();

            // Loop through each byte of the hashed data 
            // and format each one as a hexadecimal string.
            for (int i = 0; i < data.Length; i++)
            {
                sBuilder.Append(data[i].ToString("x2"));
            }

            // Return the hexadecimal string.
            return sBuilder.ToString();
        }
    }
}
