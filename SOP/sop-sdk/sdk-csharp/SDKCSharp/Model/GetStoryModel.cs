using Newtonsoft.Json;

namespace SDKCSharp.Model
{
    public class GetStoryModel
    {
       /// <summary>
        /// 故事名称
        /// </summary>
        /// <value>The name.</value>
        [JsonProperty("name")]
        public string Name { get; set; }
    }
}
