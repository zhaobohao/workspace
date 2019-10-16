using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Threading.Tasks;

using SDKCSharp.Utility;

namespace SDKCSharp.Common
{
    public class UploadFile
    {

        /// <summary>
        /// 添加上传文件
        /// </summary>
        /// <param name="name">表单名称，不能重复</param>
        /// <param name="filePath">文件路径</param>
        public UploadFile(string name, string filePath)
            : this(name, FileUtil.GetFileName(filePath), FileUtil.ReadFile(filePath))
        {
        }
        

        /// <summary>
        /// 添加上传文件
        /// </summary>
        /// <param name="name">表单名称，不能重复</param>
        /// <param name="fileName">文件名称</param>
        /// <param name="fileStream">文件流</param>
        public UploadFile(string name, string fileName, FileStream fileStream)
            : this(name, fileName, FileUtil.ReadFile(fileStream))
        {
        }

        /// <summary>
        /// 添加上传文件
        /// </summary>
        /// <param name="name">表单名称，不能重复</param>
        /// <param name="fileName">文件名称</param>
        /// <param name="fileData">文件内容</param>
        public UploadFile(string name, string fileName, byte[] fileData)
        {
            this.name = name;
            this.fileName = fileName;
            this.fileData = fileData;
            this.md5 = MD5Util.Encrypt(fileData);
        }

        private string name;

        public string Name
        {
            get { return name; }
        }

        private string fileName;

        public string FileName
        {
            get { return fileName; }
        }

        private byte[] fileData;

        public byte[] FileData
        {
            get { return fileData; }
        }

        private string md5;

        public string Md5
        {
            get { return md5; }
        }
    }
}
