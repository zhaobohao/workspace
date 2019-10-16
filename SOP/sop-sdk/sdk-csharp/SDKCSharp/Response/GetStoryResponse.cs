using System;
using Newtonsoft.Json;

namespace SDKCSharp.Response
{
    public class GetStoryResponse: BaseResponse
    {
        [JsonProperty("id")]
        public int Id { get; set; }

        [JsonProperty("name")]
        public string Name { get; set; }

        [JsonProperty("gmt_create")]
        public string GmtCreate { get; set; }

    }
}
