# Nedap.Retail.Api.Idcloud.Api.ArticleApi

All URIs are relative to *https://api.nedapretail.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**ArticleCreateOrReplace**](ArticleApi.md#articlecreateorreplace) | **POST** /article/v2/create_or_replace | Create or replace article(s)
[**ArticleDelete**](ArticleApi.md#articledelete) | **DELETE** /article/v3/delete | Delete articles
[**ArticleDeleteAll**](ArticleApi.md#articledeleteall) | **DELETE** /article/v3/delete_all | Delete all articles
[**ArticleFind**](ArticleApi.md#articlefind) | **GET** /article/v2/find | Find articles using search query
[**ArticleQuantity**](ArticleApi.md#articlequantity) | **GET** /article/v2/quantity | Get quantity of articles
[**ArticleRetrieve**](ArticleApi.md#articleretrieve) | **GET** /article/v2/retrieve | Get articles


<a name="articlecreateorreplace"></a>
# **ArticleCreateOrReplace**
> void ArticleCreateOrReplace (Articles articles)

Create or replace article(s)

Create or replace article(s) in !D Cloud. Replacing will happen based on GTIN, which is the primary key.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ArticleCreateOrReplaceExample
    {
        public void main()
        {
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new ArticleApi();
            var articles = new Articles(); // Articles | An array of articles that should be created or replaced.

            try
            {
                // Create or replace article(s)
                apiInstance.ArticleCreateOrReplace(articles);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ArticleApi.ArticleCreateOrReplace: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **articles** | [**Articles**](Articles.md)| An array of articles that should be created or replaced. | 

### Return type

void (empty response body)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="articledelete"></a>
# **ArticleDelete**
> void ArticleDelete (List<string> gtins)

Delete articles

Delete specified articles from the database. This cannot be undone! Please note the articles are not  deleted from the !D Cloud mobile apps. To do that, reset the app and re-register using the QR code.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ArticleDeleteExample
    {
        public void main()
        {
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new ArticleApi();
            var gtins = ;  // List<string> | GTIN(s) to delete. Repeat key-value for deleting multiple articles. If empty or not specified, all articles are deleted.

            try
            {
                // Delete articles
                apiInstance.ArticleDelete(gtins);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ArticleApi.ArticleDelete: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **gtins** | **List&lt;string&gt;**| GTIN(s) to delete. Repeat key-value for deleting multiple articles. If empty or not specified, all articles are deleted. | 

### Return type

void (empty response body)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="articledeleteall"></a>
# **ArticleDeleteAll**
> void ArticleDeleteAll ()

Delete all articles

Delete all articles from the database. This cannot be undone! Please note the articles are not deleted from the !D Cloud mobile apps. To do that, reset the app and re-register using the QR code.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ArticleDeleteAllExample
    {
        public void main()
        {
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new ArticleApi();

            try
            {
                // Delete all articles
                apiInstance.ArticleDeleteAll();
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ArticleApi.ArticleDeleteAll: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

void (empty response body)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="articlefind"></a>
# **ArticleFind**
> ArticleFindResponse ArticleFind (string query = null, int? count = null, int? skip = null, long? externRef = null, List<string> order = null)

Find articles using search query



### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ArticleFindExample
    {
        public void main()
        {
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new ArticleApi();
            var query = query_example;  // string | Search query. (optional)  (default to *)
            var count = 56;  // int? | Return this number of articles. (optional)  (default to 100)
            var skip = 56;  // int? | Skip this number of articles. When omitted: skip none. (optional)  (default to 0)
            var externRef = 789;  // long? | This can be used to match requests with responses from the server (for example, Ajax requests are asynchronous and thus can return out of sequence). (optional) 
            var order = new List<string>(); // List<string> | Column to which ordering should be applied. When omitted: default order wil be based on score. (optional) 

            try
            {
                // Find articles using search query
                ArticleFindResponse result = apiInstance.ArticleFind(query, count, skip, externRef, order);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ArticleApi.ArticleFind: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **query** | **string**| Search query. | [optional] [default to *]
 **count** | **int?**| Return this number of articles. | [optional] [default to 100]
 **skip** | **int?**| Skip this number of articles. When omitted: skip none. | [optional] [default to 0]
 **externRef** | **long?**| This can be used to match requests with responses from the server (for example, Ajax requests are asynchronous and thus can return out of sequence). | [optional] 
 **order** | [**List&lt;string&gt;**](string.md)| Column to which ordering should be applied. When omitted: default order wil be based on score. | [optional] 

### Return type

[**ArticleFindResponse**](ArticleFindResponse.md)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="articlequantity"></a>
# **ArticleQuantity**
> QuantityResponse ArticleQuantity ()

Get quantity of articles

Returns the total number of articles registered in !D Cloud.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ArticleQuantityExample
    {
        public void main()
        {
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new ArticleApi();

            try
            {
                // Get quantity of articles
                QuantityResponse result = apiInstance.ArticleQuantity();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ArticleApi.ArticleQuantity: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**QuantityResponse**](QuantityResponse.md)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="articleretrieve"></a>
# **ArticleRetrieve**
> List<Article> ArticleRetrieve (List<string> gtins = null, List<string> barcodes = null, DateTime? updatedAfter = null, int? skip = null, int? count = null, List<string> fields = null)

Get articles

Retrieve articles. There are three ways to call this endpoint:  1) specify gtins,  2) specify barcodes, or  3) specify updated_after, skip and count.  You can not mix these sets: skip and count have no meaning when specifying gtins or barcodes, and combining gtins and updated_after also doesn't work.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ArticleRetrieveExample
    {
        public void main()
        {
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new ArticleApi();
            var gtins = new List<string>(); // List<string> | The GTIN(s) of which article information should be returned. Repeat key-value for retrieving multiple GTINs. Mutually exclusive with barcodes[] and updated_after. (optional) 
            var barcodes = new List<string>(); // List<string> | The barcode(s) of which article information should be returned. Repeat key-value for retrieving multiple barcodes. Mutually exclusive with gtins[] and updated_after. (optional) 
            var updatedAfter = 2013-10-20T19:20:30+01:00;  // DateTime? | Articles updated on or after this time. When omitted: return all Article objects since 1 january 1970. (optional) 
            var skip = 56;  // int? | Skip this number of articles. When omitted: skip none. (optional) 
            var count = 56;  // int? | Return this number of articles. The maximum number of returned articles is limited at 50.000. When omitted: return 100 Article objects. (optional) 
            var fields = new List<string>(); // List<string> | Which fields should be included in the response. Can be any of the Article fields. When omitted: all fields will be included in the response. (optional) 

            try
            {
                // Get articles
                List&lt;Article&gt; result = apiInstance.ArticleRetrieve(gtins, barcodes, updatedAfter, skip, count, fields);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ArticleApi.ArticleRetrieve: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **gtins** | [**List&lt;string&gt;**](string.md)| The GTIN(s) of which article information should be returned. Repeat key-value for retrieving multiple GTINs. Mutually exclusive with barcodes[] and updated_after. | [optional] 
 **barcodes** | [**List&lt;string&gt;**](string.md)| The barcode(s) of which article information should be returned. Repeat key-value for retrieving multiple barcodes. Mutually exclusive with gtins[] and updated_after. | [optional] 
 **updatedAfter** | **DateTime?**| Articles updated on or after this time. When omitted: return all Article objects since 1 january 1970. | [optional] 
 **skip** | **int?**| Skip this number of articles. When omitted: skip none. | [optional] 
 **count** | **int?**| Return this number of articles. The maximum number of returned articles is limited at 50.000. When omitted: return 100 Article objects. | [optional] 
 **fields** | [**List&lt;string&gt;**](string.md)| Which fields should be included in the response. Can be any of the Article fields. When omitted: all fields will be included in the response. | [optional] 

### Return type

[**List<Article>**](Article.md)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

