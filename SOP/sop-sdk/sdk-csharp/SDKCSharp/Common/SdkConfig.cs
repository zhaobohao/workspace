using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SDKCSharp.Utility;

namespace SDKCSharp.Common
{
    public class SdkConfig
    {
        public static string SUCCESS_CODE = "10000";

        public static string DEFAULT_VERSION = "1.0";

        public static string FORMAT_TYPE = "json";

        public static string TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

        public static Encoding CHARSET = Encoding.UTF8;

        public static SignType SIGN_TYPE = SignType.RSA2;

        public static DataNameBuilder dataNameBuilder = new DefaultDataNameBuilder();
    }
}
