# Nedap.Retail.Api.Idcloud.Model.DashboardKeyFigures
## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**DifferenceListAccuracy** | **double?** |  | [optional] 
**OrganizationId** | **long?** |  | [optional] 
**LocationId** | **string** |  | [optional] 
**AbsoluteDifference** | **int?** | Absolute difference of this difference list in number of items. | [optional] 
**GrossDifference** | **double?** | Gross difference of this difference list as a percentage. | [optional] 
**NotOnShelfGtinQuantity** | **int?** | The number of articles (GTINs) that are not available on shelf, but are available in a stock room. | [optional] 
**NotOnShelfPercentage** | **double?** | Same as above, but as a percentage of the total number of GTINs in the store. | [optional] 
**StoreQuantity** | **int?** | The number of items in the entire store. | [optional] 
**StockRatio** | **double?** | Percentage of items in the entire store that are in stock rooms. | [optional] 
**ErpStockId** | **string** | ID of ERP stock used to generate the summary. | [optional] 
**ErpStockTime** | **DateTime?** | Time of ERP stock used to generate the summary. | [optional] 
**RfidStockTime** | **DateTime?** | Time of RFID stock used to generate the summary. | [optional] 
**ErpQuantity** | **int?** | Number of items in ERP stock | [optional] 
**RfidQuantity** | **int?** | Number of items in RFID stock | [optional] 
**ErpGtinQuantity** | **int?** | Number of GTINs in ERP stock | [optional] 
**RfidGtinQuantity** | **int?** | Number of GTINs in RFID stock. The number of articles (GTINs) that are available in the store (including sales floors and stock rooms). | [optional] 
**PlusDifference** | **int?** | Positive difference of this difference list in number of items (where there is more items in rfid count than in erp import). | [optional] 
**MinusDifference** | **int?** | Negative difference of this difference list in number of items (where there is more items in erp import than in rfid count). | [optional] 
**StockRoomsQuantity** | **int?** | The number of items in all stock rooms. | [optional] 
**SalesFloorsQuantity** | **int?** | The number of items in all sales floors. | [optional] 
**CreationDate** | **DateTime?** |  | [optional] 
**DifferenceListApprovedOn** | **DateTime?** |  | [optional] 
**DifferenceListStatus** | **string** |  | [optional] 
**ShipmentsInTransit** | **int?** | The number of shipments that are in transit | [optional] 
**ShipmentsAverageReceivedPercentage** | **float?** | The average received percentage over the shipments in the last 10 weeks | [optional] 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)

