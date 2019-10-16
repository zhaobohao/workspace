using System;
using System.Collections;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace SDKCSharp.Response
{
    public class DemoFileUploadResponse : BaseResponse
    {
        [JsonProperty("files")]
        private List<FileMeta> files = new List<FileMeta>();

        public List<FileMeta> Files { get => files; set => files = value; }

        public class FileMeta
        {

            public FileMeta(String filename, long size, String content)
            {
                this.Filename = filename;
                this.Size = size;
                this.Content = content;
            }

            public FileMeta()
            {
            }

            [JsonProperty("filename")]
            public String Filename { get; set; }

            [JsonProperty("size")]
            public long Size { get; set; }

            [JsonProperty("content")]
            public String Content { get; set; }
        }
    }
}
