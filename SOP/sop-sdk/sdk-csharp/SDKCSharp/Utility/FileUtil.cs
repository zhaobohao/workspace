using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.IO;

namespace SDKCSharp.Utility
{
    public class FileUtil
    {

        /// <summary>
        /// 获取文件名，带后缀
        /// </summary>
        /// <param name="filePath">文件全路径</param>
        /// <returns>返回文件名</returns>
        public static string GetFileName(string filePath)
        {
            return Path.GetFileName(filePath);
        }

        /// <summary>
        /// 读取文件，将文件内容转成byte数组
        /// </summary>
        /// <param name="filePath">文件路径</param>
        /// <returns></returns>
        public static byte[] ReadFile(string filePath)
        {
            return File.ReadAllBytes(filePath);
        }

        /// <summary>
        /// 将FileStream内容转成byte数组
        /// </summary>
        /// <param name="fs">FileStream</param>
        /// <returns></returns>
        public static byte[] ReadFile(FileStream fs)
        {
            byte[] buffer = new byte[fs.Length];
            using (BinaryWriter bw = new BinaryWriter(fs))
            {
                bw.Write(buffer);
            }
            return buffer;
        }
    }
}
