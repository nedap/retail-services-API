# Nedap.Retail.Api.Oauth.Api.AccessTokenResolverApi

All URIs are relative to *https://api.nedapretail.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**Resolve**](AccessTokenResolverApi.md#resolve) | **GET** /login/oauth/token | Resolved OAuth 2.0 access tokens


<a name="resolve"></a>
# **Resolve**
> OAuthResponse Resolve (string grantType, string clientId, string clientSecret)

Resolved OAuth 2.0 access tokens



### Example
```csharp
using System;
using System.Diagnostics;
using Nedap.Retail.Api.Oauth.Api;
using Nedap.Retail.Api.Oauth.Client;
using Nedap.Retail.Api.Oauth.Model;

namespace Example
{
    public class ResolveExample
    {
        public void main()
        {
            
            var apiInstance = new AccessTokenResolverApi();
            var grantType = grantType_example;  // string | Grant type.
            var clientId = clientId_example;  // string | Client id.
            var clientSecret = clientSecret_example;  // string | Client secret.

            try
            {
                // Resolved OAuth 2.0 access tokens
                OAuthResponse result = apiInstance.Resolve(grantType, clientId, clientSecret);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling AccessTokenResolverApi.Resolve: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **grantType** | **string**| Grant type. | 
 **clientId** | **string**| Client id. | 
 **clientSecret** | **string**| Client secret. | 

### Return type

[**OAuthResponse**](OAuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

