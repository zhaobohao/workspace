using System;
using Newtonsoft.Json;

namespace SDKCSharp.Model
{
    public class DemoFileUploadModel
    {
        [JsonProperty("remark")]
        public string Remark { get; set; }
    }
}
