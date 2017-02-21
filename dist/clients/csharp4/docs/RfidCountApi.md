# Nedap.Retail.Api.Idcloud.Api.RfidCountApi

All URIs are relative to *https://api.nedapretail.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**List**](RfidCountApi.md#list) | **GET** /rfid_count/v1/list | Get list of RFID counts
[**Retrieve**](RfidCountApi.md#retrieve) | **GET** /rfid_count/v1/retrieve | Retrieve RFID count


<a name="list"></a>
# **List**
> List<StockSummary> List (string location, DateTime? fromEventTime = null, DateTime? untilEventTime = null)

Get list of RFID counts

Retrieve list of RFID counts for a certain location.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ListExample
    {
        public void main()
        {
            
            var apiInstance = new RfidCountApi();
            var location = http://nedapretail.com/loc/26865;  // string | The location to retrieve the count for
            var fromEventTime = 2013-10-20T19:20:30+01:00;  // DateTime? | Limits the returned list to stocks with an event time after the specified date and time (optional) 
            var untilEventTime = 2013-10-20T19:20:30+01:00;  // DateTime? | Limits the returned list to stocks with an event time before the specified date and time (optional) 

            try
            {
                // Get list of RFID counts
                List&lt;StockSummary&gt; result = apiInstance.List(location, fromEventTime, untilEventTime);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling RfidCountApi.List: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string**| The location to retrieve the count for | 
 **fromEventTime** | **DateTime?**| Limits the returned list to stocks with an event time after the specified date and time | [optional] 
 **untilEventTime** | **DateTime?**| Limits the returned list to stocks with an event time before the specified date and time | [optional] 

### Return type

[**List<StockSummary>**](StockSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="retrieve"></a>
# **Retrieve**
> StockResponse Retrieve (string location, List<string> gtins = null, DateTime? time = null, bool? includeArticles = null)

Retrieve RFID count

Retrieve a RFID count based on GTIN and quantity.

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
            
            var apiInstance = new RfidCountApi();
            var location = http://nedapretail.com/loc/26865;  // string | The location to retrieve the count for
            var gtins = new List<string>(); // List<string> | Filter by GTIN. When omitted: returns all available GTINs, so no filtering based on GTIN is applied (optional) 
            var time = 2013-10-20T19:20:30+01:00;  // DateTime? | The time of the count to retrieve (relates to the event_time of the count). When omitted: the report will contain the latest available stock information. (optional) 
            var includeArticles = true;  // bool? | Include article information in the response. (optional)  (default to false)

            try
            {
                // Retrieve RFID count
                StockResponse result = apiInstance.Retrieve(location, gtins, time, includeArticles);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling RfidCountApi.Retrieve: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string**| The location to retrieve the count for | 
 **gtins** | [**List&lt;string&gt;**](string.md)| Filter by GTIN. When omitted: returns all available GTINs, so no filtering based on GTIN is applied | [optional] 
 **time** | **DateTime?**| The time of the count to retrieve (relates to the event_time of the count). When omitted: the report will contain the latest available stock information. | [optional] 
 **includeArticles** | **bool?**| Include article information in the response. | [optional] [default to false]

### Return type

[**StockResponse**](StockResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

