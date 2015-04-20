using Nedap.Retail.Api.Epcis.V1_1;
using System;
using System.Collections.Generic;
using System.Net;

namespace Nedap.Retail.Api.Example
{
    internal class EpcisExample
    {
        public static void RunExample(Client client)
        {
            var events = CreateEvents();

            try
            {
                // capture
                Console.WriteLine("------------- Capture epcis events");
                string captureResult = client.EpcisV1_1.Capture(events);
                Console.WriteLine("captured epcis events");

                // query
                Console.WriteLine("------------- Query epcis events");
                List<ParameterObject> parameters = new List<ParameterObject>();
                parameters.Add(new ParameterObject("EQ_read_point", new List<string>(new string[] {"urn:epc:id:sgln:012345.67890.001"})));
                List<EpcisEvent> queryResult = client.EpcisV1_1.Query(parameters);
                Console.WriteLine("Got " + queryResult.Count + " epcis events:");
                foreach (EpcisEvent epcisEvent in queryResult)
                {
                    switch (epcisEvent.Type)
                    {
                        case "object_event":
                            ObjectEvent objectEvent = (ObjectEvent)epcisEvent;
                            Console.WriteLine("Object event with " + objectEvent.EpcList.Count + " EPCs");
                            break;
                        case "aggregation_event":
                            AggregationEvent aggregationEvent = (AggregationEvent)epcisEvent;
                            Console.WriteLine("Aggregation event with " + aggregationEvent.ChildEpcs.Count + " EPCs");
                            break;
                        case "transaction_event":
                            TransactionEvent transactionEvent = (TransactionEvent)epcisEvent;
                            Console.WriteLine("Transaction event with " + transactionEvent.EpcList.Count + " EPCs");
                            break;
                        case "transformation_event":
                            TransformationEvent transformationEvent = (TransformationEvent)epcisEvent;
                            Console.WriteLine("Transformation event with " + transformationEvent.InputEpcList.Count + " EPCs as input");
                            break;
                    }
                }
            }
            catch (WebException e)
            {
                e.Print();
            }

            Console.ReadKey();
        }

        private static List<EpcisEvent> CreateEvents()
        {
            List<EpcisEvent> events = new List<EpcisEvent>();

            events.Add(new ObjectEvent()
            {
                Id = new Guid("12f03260-c56f-11e3-9c1a-0800200c9a66"),
                EventTime = DateTime.Now,
                EventTimeZoneOffset = "+01:00",
                RecordTime = DateTime.Now,
                EpcList = new List<string>()
                {
                    "urn:epc:id:sgtin:1234567.000246.0001",
                    "urn:epc:id:sgtin:1234567.000246.0002",
                    "urn:epc:id:sgtin:1234567.000246.0003",
                    "urn:epc:id:sgtin:1234567.000246.0004"
                },
                Action = Epcis.V1_1.Action.OBSERVE,
                BizStep = "urn:epcglobal:cbv:bizstep:cycle_counting",
                Disposition = "urn:epcglobal:cbv:disp:sellable_accessible",
                ReadPoint = "urn:epc:id:sgln:012345.67890.001",
                BizLocation = "urn:epc:id:sgln:012345.67890.001"
            });
            events.Add(new ObjectEvent()
            {
                Id = new Guid("12f03260-c56f-11e3-9c1a-0800200c9a67"),
                EventTime = DateTime.Now,
                EventTimeZoneOffset = "+01:00",
                RecordTime = DateTime.Now,
                EpcList = new List<string>()
                {
                    "urn:epc:id:sgtin:1234567.000246.0005",
                    "urn:epc:id:sgtin:1234567.000246.0006",
                    "urn:epc:id:sgtin:1234567.000246.0007",
                    "urn:epc:id:sgtin:1234567.000246.0008"
                },
                Action = Epcis.V1_1.Action.OBSERVE,
                BizStep = "urn:epcglobal:cbv:bizstep:cycle_counting",
                Disposition = "urn:epcglobal:cbv:disp:sellable_accessible",
                ReadPoint = "urn:epc:id:sgln:012345.67890.001",
                BizLocation = "urn:epc:id:sgln:012345.67890.002"
            });

            return events;
        }
    }
}