from nedap_retail_api_client import *
from epcis_helper import *
from nedap_retail_api_client.api_client import ApiException

NEW_LINE = "\n"
LOCATION_ID = "http://nedapretail.com/loc/testing"

class EpcisExample(object) :

    @staticmethod
    def run_example(client) :
        print NEW_LINE + "*** EPCIS API example ***"

        api = EpcisApi(client)
        #Configuration().debug = True;
        try:
            # Capture EPCIS events
            print NEW_LINE + "Capturing EPCIS events..."
            epcisEventsList = EpcisEventContainer()
            epcisEventsList.events = EpcisHelper.createEvents(LOCATION_ID)
            api.capture(epcisEventsList)
            EpcisHelper.printCaptureEpcisEvents(epcisEventsList)

            # Query EPCIS events
            print NEW_LINE + "Quering EPCIS events...";
            events = api.query(EpcisExample.makeEpcisQueryParameters());

            print "Events: " + str(len(events))
            print EpcisExample.printEpcisEvents(events)

            print NEW_LINE + "--- EPCIS API example finished ---"

        except ApiException as e:
            print "Server responded with an error: " + e.reason

    @staticmethod
    def makeEpcisQueryParameters() :
        queryParameters = EpcisQueryParameters();
        queryParameters.parameters = [EpcisExample.makeParameterObject1()]
        return queryParameters


    @staticmethod
    def makeParameterObject1() :
        parameter = ParameterObject()
        parameter.name = "GE_event_time";
        parameter.value = "123" #TODO: date time format now() - 1 minute
        return parameter;

    @staticmethod
    def printEpcisEvents(events) :
        sb = "Retrieved EPCIS events within last minute:"
        for i in range(0, len(events)):
            sb += NEW_LINE + NEW_LINE + "\t";
            sb += "EPCIS event " + str(i + 1) + ";"
            sb += EpcisExample.printEpcisEvent(events[i])
        
        return sb

    @staticmethod
    def printEpcisEvent(event) :
        sb = ""
        sb += NEW_LINE + "\t" + "Id: " + event.id
        sb += NEW_LINE + "\t" + "Type: " + event.type
        sb += NEW_LINE + "\t" + "Event time: " + str(event.event_time)
        sb += NEW_LINE + "\t" + "Action: " + event.action
        sb += NEW_LINE + "\t" + "Disposition: " + event.disposition
        sb += NEW_LINE + "\t" + "Biz location: " + event.biz_location
        sb += NEW_LINE + "\t" + "Biz step: " + event.biz_step
        sb += NEW_LINE + "\t" + "Read point: " + event.read_point
        sb += NEW_LINE + "\t" + "Epc list: "
        sb += EpcisExample.printEpcList(event)
        return sb

    @staticmethod
    def printEpcList(event):
        sb = ""

        for i in range(0, len(event.epc_list)):
            sb += NEW_LINE + "\t\t";
            sb += str(i + 1) + ". " + event.epc_list[i]

        return sb
