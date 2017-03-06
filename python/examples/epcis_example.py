from nedap_retail_api_client import *
from nedap_retail_api_client.api_client import ApiException
from dateutil.tz import tzlocal
import uuid
import datetime
import time

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
            epcisEventsList.events = EpcisExample.createEvents(LOCATION_ID)
            api.capture_epcis_events(epcisEventsList)
            print EpcisExample.printEpcisEvents(epcisEventsList.events)

            time.sleep(2)

            # Query EPCIS events
            print NEW_LINE + "Quering EPCIS events...";
            print NEW_LINE + "Retrieved EPCIS events within last minute:"
            events = api.query_epcis_events(EpcisExample.makeEpcisQueryParameters());

            print "Events: " + str(len(events))
            print EpcisExample.printEpcisEvents(events)

            print NEW_LINE + "--- EPCIS API example finished ---"

        except ApiException as e:
            print "Server responded with an error: " + e.reason

    @staticmethod
    def createEvents(locationId) :
        return [EpcisExample.createEpcisEvent1(locationId), EpcisExample.createEpcisEvent2(locationId)]

    @staticmethod
    def createEpcisEvent1(locationId) :
        event = EpcisEvent()
        event.type = "object_event"
        event.id = str(uuid.uuid4())
        event.event_time = datetime.datetime.now(tzlocal())
        event.action = "OBSERVE"
        event.disposition = "urn:epcglobal:cbv:disp:sellable_accessible"
        event.biz_location = locationId
        event.biz_step = "urn:epcglobal:cbv:bizstep:cycle_counting"
        event.read_point = locationId
        event.epc_list = EpcisExample.makeEpcList1()
        return event

    @staticmethod
    def createEpcisEvent2(locationId) :
        event = EpcisEvent()
        event.type = "object_event"
        event.id = str(uuid.uuid4())
        event.event_time = datetime.datetime.now(tzlocal())
        event.action = "OBSERVE"
        event.disposition = "urn:epcglobal:cbv:disp:sellable_accessible"
        event.biz_location = locationId
        event.biz_step = "urn:epcglobal:cbv:bizstep:cycle_counting"
        event.read_point = locationId
        event.epc_list = EpcisExample.makeEpcList2()
        return event;

    @staticmethod
    def makeEpcList1() :
        return [ "urn:epc:id:sgtin:2011200.000111.1",
                 "urn:epc:id:sgtin:2011200.090001.1",
                 "urn:epc:id:sgtin:2011200.000101.1",
                 "urn:epc:id:sgtin:2011200.000102.1",
                 "urn:epc:id:sgtin:2011200.000103.1"]

    @staticmethod
    def makeEpcList2() :
        return [ "urn:epc:id:sgtin:2011200.000111.2",
                 "urn:epc:id:sgtin:2011200.090002.1",
                 "urn:epc:id:sgtin:2011200.000201.1",
                 "urn:epc:id:sgtin:2011200.000202.1",
                 "urn:epc:id:sgtin:2011200.000203.1"]

    @staticmethod
    def makeEpcisQueryParameters() :
        queryParameters = EpcisQueryParameters();
        queryParameters.parameters = [EpcisExample.makeParameterObject1()]
        return queryParameters

    @staticmethod
    def makeParameterObject1() :
        parameter = ParameterObject()
        parameter.name = "GE_event_time";
        parameter.value = (datetime.datetime.now(tzlocal()) - datetime.timedelta(minutes=1)).isoformat()
        return parameter;

    @staticmethod
    def printEpcisEvents(events) :
        sb = ""
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
        sb += NEW_LINE + "\t" + "Event time: " + event.event_time.isoformat()
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