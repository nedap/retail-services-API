# Nedap.Retail.Api.Idcloud.Api.ErpStockApi

All URIs are relative to *https://api.nedapretail.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**ErpStockCreate**](ErpStockApi.md#erpstockcreate) | **POST** /erp_stock/v1/create | Create ERP stock
[**ErpStockList**](ErpStockApi.md#erpstocklist) | **GET** /erp_stock/v1/list | Get ERP stock summaries
[**ErpStockRetrieve**](ErpStockApi.md#erpstockretrieve) | **GET** /erp_stock/v1/retrieve | Retrieve a list of GTINs and quantities for a certain ERP stock.
[**ErpStockRetrieveSummary**](ErpStockApi.md#erpstockretrievesummary) | **GET** /erp_stock/v1/retrieve_summary | Retrieve ERP stock summary.


<a name="erpstockcreate"></a>
# **ErpStockCreate**
> ErpStockCreateResponse ErpStockCreate (Stock eRPStock)

Create ERP stock

Create an ERP stock.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ErpStockCreateExample
    {
        public void main()
        {
            
            var apiInstance = new ErpStockApi();
            var eRPStock = new Stock(); // Stock | The ERP stock to store.

            try
            {
                // Create ERP stock
                ErpStockCreateResponse result = apiInstance.ErpStockCreate(eRPStock);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ErpStockApi.ErpStockCreate: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eRPStock** | [**Stock**](Stock.md)| The ERP stock to store. | 

### Return type

[**ErpStockCreateResponse**](ErpStockCreateResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="erpstocklist"></a>
# **ErpStockList**
> List<StockSummary> ErpStockList (string location, DateTime? fromEventTime = null, DateTime? untilEventTime = null)

Get ERP stock summaries

Retrieves ERP stock summary list for given location.

### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ErpStockListExample
    {
        public void main()
        {
            
            var apiInstance = new ErpStockApi();
            var location = location_example;  // string | The location for which the stock is defined.
            var fromEventTime = 2013-10-20T19:20:30+01:00;  // DateTime? | Retrieve the ERP stock summaries from this time. (optional) 
            var untilEventTime = 2013-10-20T19:20:30+01:00;  // DateTime? | Retrieve the ERP stock summaries until this time. (optional) 

            try
            {
                // Get ERP stock summaries
                List&lt;StockSummary&gt; result = apiInstance.ErpStockList(location, fromEventTime, untilEventTime);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ErpStockApi.ErpStockList: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location** | **string**| The location for which the stock is defined. | 
 **fromEventTime** | **DateTime?**| Retrieve the ERP stock summaries from this time. | [optional] 
 **untilEventTime** | **DateTime?**| Retrieve the ERP stock summaries until this time. | [optional] 

### Return type

[**List<StockSummary>**](StockSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="erpstockretrieve"></a>
# **ErpStockRetrieve**
> Stock ErpStockRetrieve (string id, bool? withExcluded = null)

Retrieve a list of GTINs and quantities for a certain ERP stock.



### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ErpStockRetrieveExample
    {
        public void main()
        {
            
            var apiInstance = new ErpStockApi();
            var id = id_example;  // string | The ID of the ERP stock to retrieve.
            var withExcluded = true;  // bool? | Boolean parameter that indicates if we want to include excluded GTINs from ERP stock. (optional)  (default to false)

            try
            {
                // Retrieve a list of GTINs and quantities for a certain ERP stock.
                Stock result = apiInstance.ErpStockRetrieve(id, withExcluded);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ErpStockApi.ErpStockRetrieve: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **string**| The ID of the ERP stock to retrieve. | 
 **withExcluded** | **bool?**| Boolean parameter that indicates if we want to include excluded GTINs from ERP stock. | [optional] [default to false]

### Return type

[**Stock**](Stock.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

<a name="erpstockretrievesummary"></a>
# **ErpStockRetrieveSummary**
> StockSummary ErpStockRetrieveSummary (string id)

Retrieve ERP stock summary.



### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Idcloud.Api;
using Nedap.Retail.Api.Idcloud.Client;
using Nedap.Retail.Api.Idcloud.Model;

namespace Example
{
    public class ErpStockRetrieveSummaryExample
    {
        public void main()
        {
            
            var apiInstance = new ErpStockApi();
            var id = id_example;  // string | The ID of the ERP stock summary to retrieve.

            try
            {
                // Retrieve ERP stock summary.
                StockSummary result = apiInstance.ErpStockRetrieveSummary(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ErpStockApi.ErpStockRetrieveSummary: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **string**| The ID of the ERP stock summary to retrieve. | 

### Return type

[**StockSummary**](StockSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

