using System;
namespace SDKCSharp.Common
{
    public class SopSdkErrors
    {
        /// <summary>
        /// 网络错误
        /// </summary>
        public static ErrorResponse HTTP_ERROR = BuildErrorResponse("836875001", "网络错误");

        /// <summary>
        /// 验证返回sign错误
        /// </summary>
        public static ErrorResponse CHECK_RESPONSE_SIGN_ERROR = BuildErrorResponse("836875002", "验证服务端sign出错");

        public static ErrorResponse BuildErrorResponse(string code, string msg)
        {
            return new ErrorResponse
            {
                Code = code,
                SubCode = code,
                SubMsg = msg,
                Msg = msg
            };
        }
    }
}
