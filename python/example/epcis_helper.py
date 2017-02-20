import uuid
import datetime
from nedap_retail_api_client import *

class EpcisHelper(object) :

    @staticmethod
    def createEvents(locationId) :
        return [EpcisHelper.createEpcisEvent1(locationId), EpcisHelper.createEpcisEvent2(locationId)]

    @staticmethod
    def createEpcisEvent1(locationId) :
        event = EpcisEvent()
        event.type = "object_event"
        event.id = str(uuid.uuid4())
        event.event_time = datetime.datetime.now()
        event.action = "OBSERVE"
        event.disposition = "urn:epcglobal:cbv:disp:sellable_accessible"
        event.biz_location = locationId
        event.biz_step = "urn:epcglobal:cbv:bizstep:cycle_counting"
        event.read_point = locationId
        event.epc_list = EpcisHelper.makeEpcList1()
        return event

    @staticmethod
    def createEpcisEvent2(locationId) :
        event = EpcisEvent()
        event.type = "object_event"
        event.id = str(uuid.uuid4())
        event.event_time = datetime.datetime.now()
        event.action = "OBSERVE"
        event.disposition = "urn:epcglobal:cbv:disp:sellable_accessible"
        event.biz_location = locationId
        event.biz_step = "urn:epcglobal:cbv:bizstep:cycle_counting"
        event.read_point = locationId
        event.epc_list = EpcisHelper.makeEpcList2()
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
    def printCaptureEpcisEvents(container) :
        sb = "Captured EPCIS object events with ids:"

        print container

    #    sb.append(NEW_LINE).append(TAB);
    #sb.append(i + 1).append(DOT);
    #sb.append(epcisEventContainer.getEvents().get(i).getId());
