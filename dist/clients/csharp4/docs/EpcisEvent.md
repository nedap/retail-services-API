# Nedap.Retail.Api.Idcloud.Model.EpcisEvent
## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**Id** | **string** | Identifier, unique per organization, that identifies the EPCIS event. | [optional] 
**Type** | **string** | See EPCIS version 1.1 | [optional] 
**Action** | **string** | See EPCIS version 1.1 | [optional] 
**Disposition** | **string** | See EPCIS version 1.1 | [optional] 
**StoredId** | **Guid?** | Identifier, globally unique. Automatically created by !D Cloud when it&#39;s stored in !DCloud. | [optional] 
**EventId** | **string** | (Optional) A \&quot;globally unique identifier\&quot; for this event. This is used to mark the events as a corrective event for an event which is marked as in error by an ErrorDeclaration. As this \&quot;corrective\&quot; event can become erroneous as well, it&#39;s not so globally unique. This is specified in EPCIS-standard-1.2-draft and is not implemented in !DCloud. | [optional] 
**EventTime** | **DateTime?** | The date and time at which the EPCIS Capturing Applications asserts the event occurred. See EPCIS version 1.1 | [optional] 
**RecordTime** | **DateTime?** | The date and time at which this event was recorded by an EPCIS Repository. If a client adds record_time, it will be overridden by the server. See EPCIS version 1.1 | [optional] 
**EventTimeZoneOffset** | **string** | The time zone offset in effect at the time and place the event occurred, expressed as an offset from UTC. Values range from -14:59 to +14:59. See EPCIS version 1.1 | [optional] 
**BizStep** | **string** | See EPCIS version 1.1 | [optional] 
**BizLocation** | **string** | See EPCIS version 1.1 | [optional] 
**ReadPoint** | **string** | See EPCIS version 1.1 | [optional] 
**SourceList** | [**List&lt;SourceElement&gt;**](SourceElement.md) | See EPCIS version 1.1 | [optional] 
**DestinationList** | [**List&lt;DestinationElement&gt;**](DestinationElement.md) | See EPCIS version 1.1 | [optional] 
**BizTransactionList** | [**List&lt;BizTransactionElement&gt;**](BizTransactionElement.md) | See EPCIS version 1.1 | [optional] 
**ErrorDeclaration** | [**ErrorDeclaration**](ErrorDeclaration.md) | See EPCIS version 1.1 | [optional] 
**ParentId** | **string** |  | [optional] 
**ChildEpcs** | **List&lt;string&gt;** |  | [optional] 
**ChildQuantityList** | [**List&lt;QuantityElement&gt;**](QuantityElement.md) |  | [optional] 
**Ilmd** | **string** |  | [optional] 
**EpcList** | **List&lt;string&gt;** |  | [optional] 
**QuantityList** | [**List&lt;QuantityElement&gt;**](QuantityElement.md) |  | [optional] 
**InputEpcList** | **List&lt;string&gt;** |  | [optional] 
**OutputEpcList** | **List&lt;string&gt;** |  | [optional] 
**InputQuantityList** | [**List&lt;QuantityElement&gt;**](QuantityElement.md) |  | [optional] 
**OutputQuantityList** | [**List&lt;QuantityElement&gt;**](QuantityElement.md) |  | [optional] 
**TransformationId** | **string** |  | [optional] 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)

