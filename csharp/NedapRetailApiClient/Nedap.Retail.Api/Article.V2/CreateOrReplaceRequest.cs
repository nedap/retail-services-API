using Newtonsoft.Json;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Article.V2
{
    internal class CreateOrReplaceRequest
    {
        [JsonProperty("articles")]
        public List<Article> Articles { get; set; }
    }
}