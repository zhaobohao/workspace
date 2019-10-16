using System;
namespace SDKCSharp.Common
{
    /// <summary>
    /// 返回固定的dataName
    /// </summary>
    public class CustomDataNameBuilder: DataNameBuilder
    {

        private string dataName = "result";

        public CustomDataNameBuilder()
        {
        }


        public CustomDataNameBuilder(string dataName)
        {
            this.dataName = dataName;
        }

        public string Build(string method)
        {
            return dataName;
        }
    }
}
