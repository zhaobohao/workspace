using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EasyopenSDKCSharp.Utility
{
    /// <summary>
    /// 签名工具类
    /// </summary>
    public class SignUtil
    {

        /// <summary>
        /// 参数签名
        /// </summary>
        /// <param name="paramsMap">参数</param>
        /// <param name="secret">秘钥</param>
        /// <returns>返回sign</returns>
        public static String CreateSign(Dictionary<string, object> paramsMap, string secret)
        {
            StringBuilder sb = new StringBuilder();
            ArrayList paramNames = new ArrayList(paramsMap.Keys);

            paramNames.Sort();

            sb.Append(secret);
            foreach (string paramName in paramNames)
            {
                sb.Append(paramName).Append(paramsMap[paramName]);
            }
            sb.Append(secret);

            string source = sb.ToString();

            return MD5Util.EncryptToUpper(source);
        }
               

    }
}
