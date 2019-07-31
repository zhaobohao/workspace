using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using EasyopenSDKCSharp.Common;

namespace EasyopenSDKCSharp.Response
{
    /// <summary>
    /// 返回的Response，新建Response要继承这个类
    /// </summary>
    /// <typeparam name="T">对应data字段的数据类型</typeparam>
    public abstract class BaseResponse<T>
    {
        private static string SUCCESS_CODE = SdkConfig.SUCCESS_CODE;

        /// <summary>
        /// 状态码，0表示成功，其它都是失败
        /// </summary>
        public string code { get; set; }

        /// <summary>
        /// 消息，如果有错误则为错误信息
        /// </summary>
        public string msg { get; set; }

        /// <summary>
        /// 数据
        /// </summary>
        public T data { get; set; }

        /// <summary>
        /// 是否成功
        /// </summary>
        /// <returns></returns>
        public bool IsSuccess()
        {
            return SUCCESS_CODE == this.code;
        }
    }
}
