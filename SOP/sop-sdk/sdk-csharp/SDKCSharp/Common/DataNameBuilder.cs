using System;
namespace SDKCSharp.Common
{
    public interface DataNameBuilder
    {
        /// <summary>
        /// 构建数据节点名称
        /// </summary>
        /// <returns>返回数据节点名称.</returns>
        /// <param name="method">方法名.</param>
        string Build(string method);
    }
}
