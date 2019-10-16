using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;

namespace SDKCSharp.Utility
{
    /// <summary>
    /// 签名工具类
    /// </summary>
    public static class SignUtil
    {

        /// <summary>
        /// 构建签名
        /// </summary>
        /// <returns>The sign.</returns>
        /// <param name="parameters">参数.</param>
        /// <param name="privateKey">私钥.</param>
        /// <param name="charset">字符集.</param>
        /// <param name="signType">签名类型.</param>
        public static string CreateSign(IDictionary<string, string> parameters, string privateKey, Encoding charset, SignType signType)
        {
            RSAHelper rsa = new RSAHelper(signType, charset, privateKey, null);
            string content = GetSignContent(parameters);
            return rsa.Sign(content);
        }

        public static bool RsaCheck(string content, string sign, string publicKeyPlatform, Encoding charset,
                                   SignType signType)
        {
            RSAHelper rsa = new RSAHelper(signType, charset, null, publicKeyPlatform);
            return rsa.Verify(content, sign);
        }

        /// <summary>
        /// 构建签名内容
        /// </summary>
        /// <returns>The sign content.</returns>
        /// <param name="parameters">Parameters.</param>
        public static string GetSignContent(IDictionary<string, string> parameters)
        {
            // 第一步：把字典按Key的字母顺序排序
            IDictionary<string, string> sortedParams = new SortedDictionary<string, string>(parameters);
            IEnumerator<KeyValuePair<string, string>> dem = sortedParams.GetEnumerator();

            // 第二步：把所有参数名和参数值串在一起
            StringBuilder query = new StringBuilder("");
            while (dem.MoveNext())
            {
                string key = dem.Current.Key;
                string value = dem.Current.Value;
                if (!string.IsNullOrEmpty(key) && !string.IsNullOrEmpty(value))
                {
                    query.Append(key).Append("=").Append(value).Append("&");
                }
            }
            string content = query.ToString().Substring(0, query.Length - 1);

            return content;
        }

        /// <summary>
        /// 构建签名内容
        /// </summary>
        /// <returns>The sign content.</returns>
        /// <param name="parameters">Parameters.</param>
        public static string GetSignContentObject(IDictionary<string, object> parameters)
        {
            // 第一步：把字典按Key的字母顺序排序
            IDictionary<string, object> sortedParams = new SortedDictionary<string, object>(parameters);
            IEnumerator<KeyValuePair<string, object>> dem = sortedParams.GetEnumerator();

            // 第二步：把所有参数名和参数值串在一起
            StringBuilder query = new StringBuilder("");
            while (dem.MoveNext())
            {
                string key = dem.Current.Key;
                string value = Convert.ToString(dem.Current.Value);
                value = Regex.Replace(value, @"\s", "");
                if (!string.IsNullOrEmpty(key) && !string.IsNullOrEmpty(value))
                {
                    query.Append(key).Append("=").Append(value).Append("&");
                }
            }
            string content = query.ToString().Substring(0, query.Length - 1);

            return content;
        }

    }
}
