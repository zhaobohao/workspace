using System;
using System.Runtime.Serialization;

namespace SDKCSharp.Common
{
    /// <summary>
    /// AOP客户端异常。
    /// </summary>
    public class SopException : Exception
    {
        private string errorCode;
        private string errorMsg;

        public SopException()
            : base()
        {
        }

        public SopException(string message)
            : base(message)
        {
        }

        protected SopException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {
        }

        public SopException(string message, Exception innerException)
            : base(message, innerException)
        {
        }

        public SopException(string errorCode, string errorMsg)
            : base(errorCode + ":" + errorMsg)
        {
            this.errorCode = errorCode;
            this.errorMsg = errorMsg;
        }

        public string ErrorCode
        {
            get { return this.errorCode; }
        }

        public string ErrorMsg
        {
            get { return this.errorMsg; }
        }
    }
}
