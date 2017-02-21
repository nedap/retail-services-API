# Nedap.Retail.Api.Idcloud.Api.WorkflowApi

All URIs are relative to *https://api.nedapretail.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**Capture**](WorkflowApi.md#capture) | **POST** /workflow/v2/capture | Captures and stores workflow events in !D Cloud.
[**Query**](WorkflowApi.md#query) | **GET** /workflow/v2/query | Query and retrieve workflow event(s) from !D Cloud.


<a name="capture"></a>
# **Capture**
> void Capture (WorkflowEvent workflowEvent)

Captures and stores workflow events in !D Cloud.

The capture endpoint captures one workflow events at a time.

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

            var apiInstance = new WorkflowApi();
            var workflowEvent = new WorkflowEvent(); // WorkflowEvent | A workflow event to capture and store.

            try
            {
                // Captures and stores workflow events in !D Cloud.
                apiInstance.Capture(workflowEvent);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling WorkflowApi.Capture: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workflowEvent** | [**WorkflowEvent**](WorkflowEvent.md)| A workflow event to capture and store. | 

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
> List<WorkflowEvent> Query (string location = null, string type = null, DateTime? fromEventTime = null, DateTime? untilEventTime = null)

Query and retrieve workflow event(s) from !D Cloud.

Returns a set of workflow events that matches the criteria specified in the query parameters.

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

            var apiInstance = new WorkflowApi();
            var location = location_example;  // string | Identifies location. When omitted: return all workflow events for all locations. (optional) 
            var type = type_example;  // string | Defines the type of the workflow event. When omitted: return all workflow events for all types. (optional) 
            var fromEventTime = 2013-10-20T19:20:30+01:00;  // DateTime? | Defines date+time since when the counts are required. When omitted: returns all workflow events for specified location since the epoch. (optional) 
            var untilEventTime = 2013-10-20T19:20:30+01:00;  // DateTime? | Defines date+time until when the counts are required.  When omitted: returns all workflow events for specified location up until now. (optional) 

            try
            {
                // Query and retrieve workflow event(s) from !D Cloud.
                List&lt;WorkflowEvent&gt; result = apiInstance.Query(location, type, fromEventTime, untilEventTime);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling WorkflowApi.Query: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string**| Identifies location. When omitted: return all workflow events for all locations. | [optional] 
 **type** | **string**| Defines the type of the workflow event. When omitted: return all workflow events for all types. | [optional] 
 **fromEventTime** | **DateTime?**| Defines date+time since when the counts are required. When omitted: returns all workflow events for specified location since the epoch. | [optional] 
 **untilEventTime** | **DateTime?**| Defines date+time until when the counts are required.  When omitted: returns all workflow events for specified location up until now. | [optional] 

### Return type

[**List<WorkflowEvent>**](WorkflowEvent.md)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

