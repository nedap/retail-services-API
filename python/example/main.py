import nedap_retail_api
from epcis_example import *

def main() :
    oauth = nedap_retail_api.AccessTokenResolverApi(nedap_retail_api.ApiClient("http://localhost:9091"))
    nedap_retail_api.Configuration().access_token = oauth.resolve("client_credentials",
                                                                  "com.nedap.retail.si.1234567ABC", "a").access_token;
    client = nedap_retail_api.ApiClient("http://localhost:9090")
    EpcisExample.run_example(client)

if __name__ == '__main__':
    main();