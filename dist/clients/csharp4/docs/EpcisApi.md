# Nedap.Retail.Api.Idcloud.Api.EpcisApi

All URIs are relative to *https://api.nedapretail.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**Capture**](EpcisApi.md#capture) | **POST** /epcis/v2/capture | Captures and stores EPCIS events in !D Cloud.
[**Query**](EpcisApi.md#query) | **POST** /epcis/v2/query | Query and retrieve EPCIS event(s) from !D Cloud.


<a name="capture"></a>
# **Capture**
> void Capture (EpcisEventContainer events)

Captures and stores EPCIS events in !D Cloud.

The capture endpoint captures one or more EPCIS events at a time. This does not imply any relationship between these EPCIS events. Each element of the argument list is accepted if it is a valid EPCIS event or subtype that conforms to the above EPCIS event types.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class CaptureExample
    {
        public void main()
        {
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new EpcisApi();
            var events = new EpcisEventContainer(); // EpcisEventContainer | An array of EPCISEvent(s) to capture and store.

            try
            {
                // Captures and stores EPCIS events in !D Cloud.
                apiInstance.Capture(events);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling EpcisApi.Capture: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **events** | [**EpcisEventContainer**](EpcisEventContainer.md)| An array of EPCISEvent(s) to capture and store. | 

### Return type

void (empty response body)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="query"></a>
# **Query**
> List<EpcisEvent> Query (EpcisQueryParameters parameters)

Query and retrieve EPCIS event(s) from !D Cloud.

Returns a set of EPCIS Events that matches the criteria specified in the query parameters.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class QueryExample
    {
        public void main()
        {
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new EpcisApi();
            var parameters = new EpcisQueryParameters(); // EpcisQueryParameters | Query parameter object. If two or more query parameters are defined, then the AND operation is always applied.

            try
            {
                // Query and retrieve EPCIS event(s) from !D Cloud.
                List&lt;EpcisEvent&gt; result = apiInstance.Query(parameters);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling EpcisApi.Query: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parameters** | [**EpcisQueryParameters**](EpcisQueryParameters.md)| Query parameter object. If two or more query parameters are defined, then the AND operation is always applied. | 

### Return type

[**List<EpcisEvent>**](EpcisEvent.md)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

