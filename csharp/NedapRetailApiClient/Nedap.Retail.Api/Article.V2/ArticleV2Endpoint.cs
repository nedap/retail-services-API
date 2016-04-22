using Nedap.Retail.Api.OAuth;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;

namespace Nedap.Retail.Api.Article.V2
{
    /// <summary>
    /// Article V2 API endpoint
    /// </summary>
    public class ArticleV2Endpoint
    {
        private ApiCaller apiCaller;

        internal ArticleV2Endpoint(ApiCaller apiCaller)
        {
            this.apiCaller = apiCaller;
        }

        /// <summary>
        /// Requests the total number of articles registered in !D Cloud.
        /// </summary>
        /// <returns>Total number of articles</returns>
        public long Quantity()
        {
            return apiCaller.Get<QuantityResponse>("/article/v2/quantity").Quantity;
        }

        /// <summary>
        /// Retrieve one or more articles based on GTIN value(s) or barcodes. Optionally specify returned Article properties. When querying on barcode, the barcode type is ignored.
        /// All articles that have a barcode that match (case insensitive for alphanumeric barcodes) one of the given barcodes is returned.
        /// </summary>
        /// <param name="gtins">The GTIN(s) of which article information should be returned. Repeat key-value for retrieving multiple GTINs.Mutually exclusive with barcodes[] .</param>
        /// <param name="barcodes">The barcode(s) of which article information should be returned. Repeat key-value for retrieving multiple barcodes.Mutually exclusive with gtins[].</param>
        /// <param name="fields">Optional. Which fields should be included in the response. Can be any of the Article fields. When omitted: all fields will be included in the response. Repeat key-value for retrieving multiple fields.</param>
        /// <returns>List of articles retrieved</returns>
        public List<Article> Details(List<string> gtins, List<string> barcodes, List<string> fields)
        {
            gtins.Required(() => gtins);
            barcodes.Required(() => barcodes);

            NameValueCollection parameters = new NameValueCollection();
            gtins.ForEach(g => parameters.Add("gtins[]", g));
            barcodes.ForEach(b => parameters.Add("barcodes[]", b));
            if (fields != null) fields.ForEach(f => parameters.Add("fields[]", f));
            return apiCaller.Get<List<Article>>("/article/v2/retrieve", parameters);
        }

        /// <summary>
        /// Retrieve articles. Optionally specify returned Article properties.
        /// </summary>
        /// <param name="updatedAfter">Articles updated on or after this time. When omitted: return all Article objects since 1 january 1970.</param>
        /// <param name="skip">Skip this number of articles. When omitted: skip none.</param>
        /// <param name="count">Return this number of articles. When omitted: return 100 Article objects. The number of returned Resources is limited at 50.000.</param>
        /// <param name="fields">Which fields should be included in the response. Can be one of the earlier defined fields. When omitted: return all fields in Article resource. Repeat key-value for retrieving multiple fields.</param>
        /// <returns>List of articles retrieved</returns>
        public List<Article> Retrieve(Nullable<DateTime> updatedAfter, Nullable<int> skip, Nullable<int> count, List<string> fields)
        {
            NameValueCollection parameters = new NameValueCollection();
            if (updatedAfter.HasValue) parameters.Add("updated_after", updatedAfter.Value.ToString());
            if (skip.HasValue) parameters.Add("skip", skip.Value.ToString());
            if (count.HasValue) parameters.Add("count", count.Value.ToString());
            if (fields != null) fields.ForEach(f => parameters.Add("fields[]", f));
            return apiCaller.Get<List<Article>>("/article/v2/retrieve", parameters);
        }

        /// <summary>
        /// Articles are created or replaced using the GTIN as primary key.
        /// Entire entry is replaced if existed: all existing fields are cleared for these entries.
        /// </summary>
        /// <param name="articles">List of articles</param>
        /// <returns>Status code</returns>
        public string CreateOrReplace(List<Article> articles)
        {
            articles.Required(() => articles);

            CreateOrReplaceRequest request = new CreateOrReplaceRequest();
            request.Articles = articles;
            return apiCaller.Post<string>("/article/v2/create_or_replace", request);
        }

        /// <summary>
        /// Articles are created or updated using the GTIN as primary key.
        /// Information is merged with existing information in the database.
        /// </summary>
        /// <param name="articles">List of articles</param>
        /// <returns>Status code</returns>
        public string CreateOrUpdate(List<Article> articles)
        {
            articles.Required(() => articles);

            CreateOrReplaceRequest request = new CreateOrReplaceRequest();
            request.Articles = articles;
            return apiCaller.Post<string>("/article/v2/create_or_update", request);
        }

        /// <summary>
        /// Article entries are deleted for given organization.
        /// All articles will be physically deleted from database and this process is irreversible.
        /// </summary>
        /// <returns>Status code</returns>
        public string Delete()
        {
            return apiCaller.Post<string>("/article/v2/delete", "");
        }
    }
}