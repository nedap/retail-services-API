# Nedap.Retail.Api.Idcloud.Api.ShipmentApi

All URIs are relative to *https://api.nedapretail.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**Capture**](ShipmentApi.md#capture) | **POST** /shipment/v1/capture | Captures and stores a shipment notice in !D Cloud.
[**Delete**](ShipmentApi.md#delete) | **DELETE** /shipment/v1/delete | Deletes a shipment notice from !D Cloud.
[**List**](ShipmentApi.md#list) | **GET** /shipment/v1/list | Retrieves a list of shipment summaries.
[**Retrieve**](ShipmentApi.md#retrieve) | **GET** /shipment/v1/retrieve | Retrieves a shipment from !D Cloud.


<a name="capture"></a>
# **Capture**
> void Capture (Shipment shipmentNotice)

Captures and stores a shipment notice in !D Cloud.

Only shipment notices are allowed. These are represented by the status INTRANSIT.

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

            var apiInstance = new ShipmentApi();
            var shipmentNotice = new Shipment(); // Shipment | Shipment notice to capture.

            try
            {
                // Captures and stores a shipment notice in !D Cloud.
                apiInstance.Capture(shipmentNotice);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ShipmentApi.Capture: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **shipmentNotice** | [**Shipment**](Shipment.md)| Shipment notice to capture. | 

### Return type

void (empty response body)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="delete"></a>
# **Delete**
> void Delete (string parentId)

Deletes a shipment notice from !D Cloud.

If the shipment notice was created by an aggregation event, this event will be retracted without a reason.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class DeleteExample
    {
        public void main()
        {
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new ShipmentApi();
            var parentId = parentId_example;  // string | Parent ID of a shipment.

            try
            {
                // Deletes a shipment notice from !D Cloud.
                apiInstance.Delete(parentId);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ShipmentApi.Delete: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentId** | **string**| Parent ID of a shipment. | 

### Return type

void (empty response body)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="list"></a>
# **List**
> List<ShipmentSummary> List (string source = null, string destination = null, string status = null, DateTime? lastModifiedSince = null)

Retrieves a list of shipment summaries.

Either source or destination is required, not both. This endpoint can produce: JSON, CSV and Excel.

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
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new ShipmentApi();
            var source = source_example;  // string | Source of a shipment. (optional) 
            var destination = destination_example;  // string | Destination of a shipment. (optional) 
            var status = status_example;  // string | Status of the shipment. (optional)  (default to INTRANSIT)
            var lastModifiedSince = 2013-10-20T19:20:30+01:00;  // DateTime? | Find shipments that have been modified since specified time. When omitted: default is present minus one month. (optional) 

            try
            {
                // Retrieves a list of shipment summaries.
                List&lt;ShipmentSummary&gt; result = apiInstance.List(source, destination, status, lastModifiedSince);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ShipmentApi.List: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **source** | **string**| Source of a shipment. | [optional] 
 **destination** | **string**| Destination of a shipment. | [optional] 
 **status** | **string**| Status of the shipment. | [optional] [default to INTRANSIT]
 **lastModifiedSince** | **DateTime?**| Find shipments that have been modified since specified time. When omitted: default is present minus one month. | [optional] 

### Return type

[**List<ShipmentSummary>**](ShipmentSummary.md)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="retrieve"></a>
# **Retrieve**
> Shipment Retrieve (string parentId, bool? includeArticles = null)

Retrieves a shipment from !D Cloud.



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
            
            // Configure OAuth2 access token for authorization: auth
            Configuration.Default.AccessToken = "YOUR_ACCESS_TOKEN";

            var apiInstance = new ShipmentApi();
            var parentId = parentId_example;  // string | Parent ID of a shipment.
            var includeArticles = true;  // bool? | When set to true the article master data will be included in the response. (optional)  (default to false)

            try
            {
                // Retrieves a shipment from !D Cloud.
                Shipment result = apiInstance.Retrieve(parentId, includeArticles);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ShipmentApi.Retrieve: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentId** | **string**| Parent ID of a shipment. | 
 **includeArticles** | **bool?**| When set to true the article master data will be included in the response. | [optional] [default to false]

### Return type

[**Shipment**](Shipment.md)

### Authorization

[auth](../README.md#auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

