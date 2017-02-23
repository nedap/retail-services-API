import nedap_retail_api_oauth
import nedap_retail_api_client

from epcis_example import *

def main() :
    oauth = nedap_retail_api_oauth.AccessTokenResolverApi(nedap_retail_api_oauth.ApiClient("http://localhost:9091"))
    nedap_retail_api_client.Configuration().access_token = oauth.resolve("client_credentials", "com.nedap.retail.si.1234567ABC", "a").access_token;
    client = nedap_retail_api_client.ApiClient("http://localhost:9090")
    EpcisExample.run_example(client)

if __name__ == '__main__':
    main();