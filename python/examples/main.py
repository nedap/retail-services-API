import nedap_retail_api_client

from epcis_example import *
from dateutil.tz import tzlocal

def main() :
    client = nedap_retail_api_client.ApiClient("https://api-test.nedapretail.com")
    nedap_retail_api_client.Configuration().access_token = ""
    EpcisExample.run_example(client)

if __name__ == '__main__':
    print (datetime.datetime.now(tzlocal()) - datetime.timedelta(minutes=10)).isoformat()
    main();