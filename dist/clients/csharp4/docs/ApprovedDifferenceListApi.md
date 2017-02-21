# Nedap.Retail.Api.Idcloud.Api.ApprovedDifferenceListApi

All URIs are relative to *https://api.nedapretail.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**Export**](ApprovedDifferenceListApi.md#export) | **GET** /approved_difference_list/v1/export | Export an approved difference list
[**List**](ApprovedDifferenceListApi.md#list) | **GET** /approved_difference_list/v1/list | List of approved difference list summaries
[**Retrieve**](ApprovedDifferenceListApi.md#retrieve) | **GET** /approved_difference_list/v1/retrieve | Retrieve an approved difference list
[**RetrieveSummary**](ApprovedDifferenceListApi.md#retrievesummary) | **GET** /approved_difference_list/v1/retrieve_summary | Retrieve the summary an approved difference list
[**UpdateExportStatus**](ApprovedDifferenceListApi.md#updateexportstatus) | **POST** /approved_difference_list/v1/update_export_status | Update the export status of an approved difference list


<a name="export"></a>
# **Export**
> ApprovedDifferenceListExportResponse Export (Guid? id, bool? includeArticles = null, string accept = null)

Export an approved difference list

This endpoint returns the approved difference list with only one quantity per GTIN: the RFID quantity if the difference for that GTIN has been approved, otherwise the ERP quantity. This endpoint returns all GTINs, even the ones where RFID and ERP quantities are equal.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ExportExample
    {
        public void main()
        {
            
            var apiInstance = new ApprovedDifferenceListApi();
            var id = id_example;  // Guid? | Approved difference list ID.
            var includeArticles = true;  // bool? | When set to true the article master data will be included in the response. (optional)  (default to false)
            var accept = accept_example;  // string | Approved difference list ID. (optional) 

            try
            {
                // Export an approved difference list
                ApprovedDifferenceListExportResponse result = apiInstance.Export(id, includeArticles, accept);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ApprovedDifferenceListApi.Export: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Guid?**| Approved difference list ID. | 
 **includeArticles** | **bool?**| When set to true the article master data will be included in the response. | [optional] [default to false]
 **accept** | **string**| Approved difference list ID. | [optional] 

### Return type

[**ApprovedDifferenceListExportResponse**](ApprovedDifferenceListExportResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="list"></a>
# **List**
> List<ApprovedDifferenceListSummary> List (string location, DateTime? fromRfidTime = null, DateTime? untilRfidTime = null)

List of approved difference list summaries

Retrieves list of approved difference lists for given location.

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
            
            var apiInstance = new ApprovedDifferenceListApi();
            var location = location_example;  // string | The location identifier for list of approved difference lists.
            var fromRfidTime = 2013-10-20T19:20:30+01:00;  // DateTime? | Defines date+time since when approved difference lists are required. When omitted: returns all approved difference lists for specified location since the epoch. (optional) 
            var untilRfidTime = 2013-10-20T19:20:30+01:00;  // DateTime? | Defines date+time until when approved difference lists are required. When omitted: returns all approved difference lists for specified location up until now. (optional) 

            try
            {
                // List of approved difference list summaries
                List&lt;ApprovedDifferenceListSummary&gt; result = apiInstance.List(location, fromRfidTime, untilRfidTime);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ApprovedDifferenceListApi.List: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string**| The location identifier for list of approved difference lists. | 
 **fromRfidTime** | **DateTime?**| Defines date+time since when approved difference lists are required. When omitted: returns all approved difference lists for specified location since the epoch. | [optional] 
 **untilRfidTime** | **DateTime?**| Defines date+time until when approved difference lists are required. When omitted: returns all approved difference lists for specified location up until now. | [optional] 

### Return type

[**List<ApprovedDifferenceListSummary>**](ApprovedDifferenceListSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="retrieve"></a>
# **Retrieve**
> ApprovedDifferenceListResponse Retrieve (Guid? id, bool? includeArticles = null, string accept = null)

Retrieve an approved difference list

Retrieve an approved difference list using the difference list ID. The response contains GTIN, ERP quantity, RFID quantity and an approved flag. This endpoint returns all GTINs, even the ones where RFID and ERP quantities are equal.

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
            
            var apiInstance = new ApprovedDifferenceListApi();
            var id = id_example;  // Guid? | Approved difference list ID.
            var includeArticles = true;  // bool? | When set to true the article master data will be included in the response. (optional)  (default to false)
            var accept = accept_example;  // string | Approved difference list ID. (optional) 

            try
            {
                // Retrieve an approved difference list
                ApprovedDifferenceListResponse result = apiInstance.Retrieve(id, includeArticles, accept);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ApprovedDifferenceListApi.Retrieve: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Guid?**| Approved difference list ID. | 
 **includeArticles** | **bool?**| When set to true the article master data will be included in the response. | [optional] [default to false]
 **accept** | **string**| Approved difference list ID. | [optional] 

### Return type

[**ApprovedDifferenceListResponse**](ApprovedDifferenceListResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="retrievesummary"></a>
# **RetrieveSummary**
> ApprovedDifferenceListSummary RetrieveSummary (string location, DateTime? rfidTime)

Retrieve the summary an approved difference list

Returns the summary of an approved difference list for a certain location and RFID time.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class RetrieveSummaryExample
    {
        public void main()
        {
            
            var apiInstance = new ApprovedDifferenceListApi();
            var location = location_example;  // string | The location identifier for list of approved difference lists.
            var rfidTime = 2013-10-20T19:20:30+01:00;  // DateTime? | Date and time of an RFID stock.

            try
            {
                // Retrieve the summary an approved difference list
                ApprovedDifferenceListSummary result = apiInstance.RetrieveSummary(location, rfidTime);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ApprovedDifferenceListApi.RetrieveSummary: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string**| The location identifier for list of approved difference lists. | 
 **rfidTime** | **DateTime?**| Date and time of an RFID stock. | 

### Return type

[**ApprovedDifferenceListSummary**](ApprovedDifferenceListSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="updateexportstatus"></a>
# **UpdateExportStatus**
> void UpdateExportStatus (Guid? id, string exportStatus)

Update the export status of an approved difference list

!D Cloud keeps track of the export status of approved difference lists. This endpoint can be used to update the status in !D Cloud.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class UpdateExportStatusExample
    {
        public void main()
        {
            
            var apiInstance = new ApprovedDifferenceListApi();
            var id = id_example;  // Guid? | ID of approved difference list.
            var exportStatus = exportStatus_example;  // string | New export status of approved difference list.

            try
            {
                // Update the export status of an approved difference list
                apiInstance.UpdateExportStatus(id, exportStatus);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ApprovedDifferenceListApi.UpdateExportStatus: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Guid?**| ID of approved difference list. | 
 **exportStatus** | **string**| New export status of approved difference list. | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

