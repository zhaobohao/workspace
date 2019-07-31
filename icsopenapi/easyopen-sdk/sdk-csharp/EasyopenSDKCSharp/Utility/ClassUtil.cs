using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Reflection;
using System.Threading.Tasks;

using EasyopenSDKCSharp.Request;

namespace EasyopenSDKCSharp.Utility
{
    public class ClassUtil
    {
        /// <summary>
        /// 将普通对象转换成Dictionary
        /// </summary>
        /// <param name="obj"></param>
        /// <returns></returns>
        public static Dictionary<string, object> ConvertObjectToDictionary(object obj)
        {
            Dictionary<string, object> dict = new Dictionary<string, object>();

            if (obj == null)
            {
                return dict;
            }
            // 得到请求对象的所有属性
            PropertyInfo[] properties = obj.GetType().GetProperties();

            if (properties.Length <= 0)
            {
                return dict;
            }

            foreach (PropertyInfo propertyInfo in properties)
            {
                if (IsIgnoreSignProperty(propertyInfo))
                {
                    continue;
                }
                string name = propertyInfo.Name.ToLower();
                object value = propertyInfo.GetValue(obj, null);
                // Console.WriteLine("{0}:{1}", name, value);
                dict.Add(name, value);
            }

            return dict;
        }


        /// <summary>
        /// 被[IgnoreSign]标记的字段名,如果被标记的话就不加入到签名算法中
        /// </summary>
        /// <param name="propertyInfo"></param>
        /// <returns></returns>
        private static bool IsIgnoreSignProperty(PropertyInfo propertyInfo)
        {
            Type ignoreSignType = typeof(IgnoreSign);
            // 获取这个字段的元数据
            object[] attrs = propertyInfo.GetCustomAttributes(false);

            foreach (object attr in attrs)
            {
                if (attr.GetType().Equals(ignoreSignType))
                {
                    return true;
                }
            }

            return false;
        }

    }
}
