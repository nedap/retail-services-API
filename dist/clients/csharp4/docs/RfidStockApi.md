# Nedap.Retail.Api.Idcloud.Api.RfidStockApi

All URIs are relative to *https://api.nedapretail.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**Retrieve**](RfidStockApi.md#retrieve) | **GET** /rfid_stock/v1/retrieve | Returns the real time stock at a certain location in unique items (SGTINs). Items are returned based on their last known bizLocation (location) as registered via the EPCIS Capture API.
[**RetrieveAsGtin14**](RfidStockApi.md#retrieveasgtin14) | **GET** /rfid_stock/v1/retrieve_as_gtin14 | Returns the real time stock at a certain location per GTIN (and not per unique item).
[**RetrieveGroupedByDisposition**](RfidStockApi.md#retrievegroupedbydisposition) | **GET** /rfid_stock/v1/retrieve_grouped_by_disposition | Returns the real time stock at a certain location in unique items (SGTINs). Items are returned based on their last known bizLocation (location) as registered via the EPCIS Capture API.


<a name="retrieve"></a>
# **Retrieve**
> SgtinRfidStockResponse Retrieve (string location, List<string> locationTypes = null)

Returns the real time stock at a certain location in unique items (SGTINs). Items are returned based on their last known bizLocation (location) as registered via the EPCIS Capture API.

The response contains a list of stocks for all matching (sub)locations based on the given parameters: * If the stock for a store without sublocations is requested, the stocks list in the response  will contain one entry containing the stock for the entire store.  * If a store has sublocations, the list of stocks in the response will contain an entry each   for the sublocations. * If a store has stock for both sublocations and store, the list of stocks in the response   contains entries for the store and the sublocations with stock.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class RetrieveExample
    {
        public void main()
        {
            
            var apiInstance = new RfidStockApi();
            var location = http://nedapretail.com/loc/26865;  // string | Location ID of a store or a sublocation.
            var locationTypes = new List<string>(); // List<string> | Filter on sublocation type(s): only stock on sublocations of the specified type(s) will be returned. Repeat key-value for specifying multiple sublocation types. Valid sublocation types are:     STOCK_ROOM, SALES_FLOOR,     OFFSITE_STORAGE and     FOREIGN. Default behavior is to return stocks for all sublocation types except FOREIGN. (optional) 

            try
            {
                // Returns the real time stock at a certain location in unique items (SGTINs). Items are returned based on their last known bizLocation (location) as registered via the EPCIS Capture API.
                SgtinRfidStockResponse result = apiInstance.Retrieve(location, locationTypes);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling RfidStockApi.Retrieve: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string**| Location ID of a store or a sublocation. | 
 **locationTypes** | [**List&lt;string&gt;**](string.md)| Filter on sublocation type(s): only stock on sublocations of the specified type(s) will be returned. Repeat key-value for specifying multiple sublocation types. Valid sublocation types are:     STOCK_ROOM, SALES_FLOOR,     OFFSITE_STORAGE and     FOREIGN. Default behavior is to return stocks for all sublocation types except FOREIGN. | [optional] 

### Return type

[**SgtinRfidStockResponse**](SgtinRfidStockResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="retrieveasgtin14"></a>
# **RetrieveAsGtin14**
> GtinRfidStockResponse RetrieveAsGtin14 (string location, List<string> locationTypes = null, bool? includeArticles = null)

Returns the real time stock at a certain location per GTIN (and not per unique item).

The response contains a list of stocks for all matching sublocations based on the given parameters. * If the stock for a store without sublocations is requested, the stocks list in the response   will contain one entry containing the stock for the entire store.  * If a store has sublocations, the list of stocks in the response will contain an entry each   for the sublocations. * If a store has stock for both sublocations and store, the list of stocks in the response   contains entries for the store and the sublocations with stock.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class RetrieveAsGtin14Example
    {
        public void main()
        {
            
            var apiInstance = new RfidStockApi();
            var location = http://nedapretail.com/loc/26865;  // string | Location ID of a store or a sublocation.
            var locationTypes = new List<string>(); // List<string> | Filter on sublocation type(s): only stock on sublocations of the specified type(s) will be returned. Repeat key-value for specifying multiple sublocation types. Valid sublocation types are:     STOCK_ROOM, SALES_FLOOR,     OFFSITE_STORAGE and     FOREIGN. Default behavior is to return stocks for all sublocation types except FOREIGN. (optional) 
            var includeArticles = true;  // bool? | Include article information in the response. (optional)  (default to false)

            try
            {
                // Returns the real time stock at a certain location per GTIN (and not per unique item).
                GtinRfidStockResponse result = apiInstance.RetrieveAsGtin14(location, locationTypes, includeArticles);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling RfidStockApi.RetrieveAsGtin14: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string**| Location ID of a store or a sublocation. | 
 **locationTypes** | [**List&lt;string&gt;**](string.md)| Filter on sublocation type(s): only stock on sublocations of the specified type(s) will be returned. Repeat key-value for specifying multiple sublocation types. Valid sublocation types are:     STOCK_ROOM, SALES_FLOOR,     OFFSITE_STORAGE and     FOREIGN. Default behavior is to return stocks for all sublocation types except FOREIGN. | [optional] 
 **includeArticles** | **bool?**| Include article information in the response. | [optional] [default to false]

### Return type

[**GtinRfidStockResponse**](GtinRfidStockResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="retrievegroupedbydisposition"></a>
# **RetrieveGroupedByDisposition**
> SgtinRfidStockResponse RetrieveGroupedByDisposition (string location, List<string> locationTypes = null, List<string> dispositions = null)

Returns the real time stock at a certain location in unique items (SGTINs). Items are returned based on their last known bizLocation (location) as registered via the EPCIS Capture API.

The response contains a list of stocks for all matching (sub)locations based on the given parameters: * If the stock for a store without sublocations is requested, the stocks list in the response  will contain one entry containing the stock for the entire store.  * If a store has sublocations, the list of stocks in the response will contain an entry each   for the sublocations. * If a store has stock for both sublocations and store, the list of stocks in the response   contains entries for the store and the sublocations with stock.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class RetrieveGroupedByDispositionExample
    {
        public void main()
        {
            
            var apiInstance = new RfidStockApi();
            var location = http://nedapretail.com/loc/26865;  // string | Location ID of a store or a sublocation.
            var locationTypes = new List<string>(); // List<string> | Filter on sublocation type(s): only stock on sublocations of the specified type(s) will be returned. Repeat key-value for specifying multiple sublocation types. Valid sublocation types are:     STOCK_ROOM, SALES_FLOOR,     OFFSITE_STORAGE and     FOREIGN. Default behavior is to return stocks for all sublocation types except FOREIGN. (optional) 
            var dispositions = new List<string>(); // List<string> | Filter on disposition(s): only stock of the specified disposition(s) will be returned. (optional) 

            try
            {
                // Returns the real time stock at a certain location in unique items (SGTINs). Items are returned based on their last known bizLocation (location) as registered via the EPCIS Capture API.
                SgtinRfidStockResponse result = apiInstance.RetrieveGroupedByDisposition(location, locationTypes, dispositions);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling RfidStockApi.RetrieveGroupedByDisposition: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string**| Location ID of a store or a sublocation. | 
 **locationTypes** | [**List&lt;string&gt;**](string.md)| Filter on sublocation type(s): only stock on sublocations of the specified type(s) will be returned. Repeat key-value for specifying multiple sublocation types. Valid sublocation types are:     STOCK_ROOM, SALES_FLOOR,     OFFSITE_STORAGE and     FOREIGN. Default behavior is to return stocks for all sublocation types except FOREIGN. | [optional] 
 **dispositions** | [**List&lt;string&gt;**](string.md)| Filter on disposition(s): only stock of the specified disposition(s) will be returned. | [optional] 

### Return type

[**SgtinRfidStockResponse**](SgtinRfidStockResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

