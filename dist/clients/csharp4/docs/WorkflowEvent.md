# Nedap.Retail.Api.Idcloud.Model.WorkflowEvent
## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**Type** | **string** | Type of the workflow | 
**Location** | **string** | The location for which the stock is defined. | 
**EventTime** | **DateTime?** | Client-side date+time of registering that the count was finished. This is not the moment of sending the workflow event nor is it the moment when the last EPC was observed. | 
**StartTime** | **DateTime?** | Optional. This is moment when the first EPC was observed.  | [optional] 
**EpcCount** | **long?** | The total number of EPCs that were counted in the referenced message_ids. | [optional] 
**MessageIds** | **List&lt;string&gt;** | Array of EPCIS event message IDs. These events contain the actual EPCs that were observed in this count. | 
**ClientId** | **string** |  | [optional] 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)

