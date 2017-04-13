using Nedap.Retail.Api.Api;
using Nedap.Retail.Api.Client;
using Nedap.Retail.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nedap.Retail.Api.Example
{
    internal class EpcisExample
    {
        public static void RunExample(Configuration configuration)
        {
            try
            {
                // capture
                EpcisApi epcisApi = new EpcisApi(configuration);
                Console.WriteLine("------------- Capture epcis events");
                List<EpcisEvent> events = CreateEvents();
                epcisApi.CaptureEpcisEvents(events);
                printEpcisEvents(events);
                Console.WriteLine("captured epcis events");

                // query
                Console.WriteLine("------------- Query epcis events");
                List<ParameterObject> parameters = new List<ParameterObject>();
                parameters.Add(new ParameterObject("EQ_read_point", null, null, new List<string>(new string[] { "urn:epc:id:sgln:012345.67890.001" })));
                EpcisQueryParameters queryParameters = new EpcisQueryParameters(parameters);

                List<EpcisEvent> queryResult = epcisApi.QueryEpcisEvents(queryParameters);
                Console.WriteLine("Got " + queryResult.Count + " epcis events:");
                foreach (EpcisEvent epcisEvent in queryResult)
                {
                    switch (epcisEvent.Type)
                    {
                        case EpcisEvent.TypeEnum.Objectevent:
                            Console.WriteLine("Object event with " + epcisEvent.EpcList.Count + " EPCs");
                            printEpcisEvent(epcisEvent);
                            break;
                        case EpcisEvent.TypeEnum.Aggregationevent:
                            Console.WriteLine("Aggregation event with " + epcisEvent.ChildEpcs.Count + " EPCs");
                            printEpcisEvent(epcisEvent);
                            break;
                        case EpcisEvent.TypeEnum.Transactionevent:
                            Console.WriteLine("Transaction event with " + epcisEvent.EpcList.Count + " EPCs");
                            printEpcisEvent(epcisEvent);
                            break;
                        case EpcisEvent.TypeEnum.Transformationevent:
                            Console.WriteLine("Transformation event with " + epcisEvent.InputEpcList.Count + " EPCs as input");
                            printEpcisEvent(epcisEvent);
                            break;
                    }
                }
            }
            catch (ApiException e)
            {
                Console.WriteLine(e.ToString());
            }

            Console.ReadKey();
        }

        private static List<EpcisEvent> CreateEvents()
        {
            List<EpcisEvent> events = new List<EpcisEvent>();

            events.Add(new EpcisEvent()
            {
                Type = EpcisEvent.TypeEnum.Objectevent,
                Id = new Guid("12f03260-c56f-11e3-9c1a-0800200c9a66").ToString(),                
                EventTime = DateTime.Now, //TODO: Time format
                EventTimeZoneOffset = "+01:00",
                RecordTime = DateTime.Now,
                EpcList = new List<string>()
                {
                    "urn:epc:id:sgtin:1234567.000246.0001",
                    "urn:epc:id:sgtin:1234567.000246.0002",
                    "urn:epc:id:sgtin:1234567.000246.0003",
                    "urn:epc:id:sgtin:1234567.000246.0004"
                },
                Action = EpcisEvent.ActionEnum.OBSERVE,
                BizStep = "urn:epcglobal:cbv:bizstep:cycle_counting",
                Disposition = "urn:epcglobal:cbv:disp:sellable_accessible",
                ReadPoint = "urn:epc:id:sgln:012345.67890.001",
                BizLocation = "urn:epc:id:sgln:012345.67890.001",
                BizTransactionList = new List<BizTransactionElement>() {
                    new BizTransactionElement() { BizTransaction = "123" }
                }
            });
            events.Add(new EpcisEvent()
            {
                Type = EpcisEvent.TypeEnum.Objectevent,
                Id = new Guid("12f03260-c56f-11e3-9c1a-0800200c9a67").ToString(),
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
                Action = EpcisEvent.ActionEnum.OBSERVE,
                BizStep = "urn:epcglobal:cbv:bizstep:cycle_counting",
                Disposition = "urn:epcglobal:cbv:disp:sellable_accessible",
                ReadPoint = "urn:epc:id:sgln:012345.67890.001",
                BizLocation = "urn:epc:id:sgln:012345.67890.002",
                BizTransactionList = new List<BizTransactionElement>() {
                    new BizTransactionElement() { BizTransaction = "456" }
                }
            });
            events.Add(new EpcisEvent()
            {
                Type = EpcisEvent.TypeEnum.Objectevent,
                Id = new Guid("12f03260-c56f-11e3-9c1a-0800200c9a68").ToString(),
                EventTime = DateTime.Now,
                EventTimeZoneOffset = "+01:00",
                RecordTime = DateTime.Now,
                EpcList = new List<string>()
                {
                    "urn:epc:id:sgtin:1234567.000246.0009",
                    "urn:epc:id:sgtin:1234567.000246.0010",
                    "urn:epc:id:sgtin:1234567.000246.0011",
                    "urn:epc:id:sgtin:1234567.000246.0012"
                },
                Action = EpcisEvent.ActionEnum.OBSERVE,
                BizStep = "urn:epcglobal:cbv:bizstep:cycle_counting",
                Disposition = "urn:epcglobal:cbv:disp:sellable_accessible",
                ReadPoint = "urn:epc:id:sgln:012345.67890.001",
                BizLocation = "urn:epc:id:sgln:012345.67890.001",
                BizTransactionList = new List<BizTransactionElement>() {
                    new BizTransactionElement() { BizTransaction = "123" }
                }
            });
            events.Add(new EpcisEvent()
            {
                Type = EpcisEvent.TypeEnum.Objectevent,
                Id = new Guid("12f03260-c56f-11e3-9c1a-0800200c9a69").ToString(),
                EventTime = DateTime.Now,
                EventTimeZoneOffset = "+01:00",
                RecordTime = DateTime.Now,
                EpcList = new List<string>()
                {
                    "urn:epc:id:sgtin:1234567.000246.0013",
                    "urn:epc:id:sgtin:1234567.000246.0014",
                    "urn:epc:id:sgtin:1234567.000246.0015",
                    "urn:epc:id:sgtin:1234567.000246.0016"
                },
                Action = EpcisEvent.ActionEnum.OBSERVE,
                BizStep = "urn:epcglobal:cbv:bizstep:cycle_counting",
                Disposition = "urn:epcglobal:cbv:disp:sellable_accessible",
                ReadPoint = "urn:epc:id:sgln:012345.67890.001",
                BizLocation = "urn:epc:id:sgln:012345.67890.002",
                BizTransactionList = new List<BizTransactionElement>() {
                    new BizTransactionElement() { BizTransaction = "456" }
                }
            });

            return events;
        }

        private static void printEpcisEvents(List<EpcisEvent> events)
        {
            Console.WriteLine("Retrieved EPCIS events within last minute:");
            for (int i = 0; i < events.Count; i++)
            {
                Console.WriteLine("\n");
                Console.WriteLine("\tEPCIS event " + (i + 1) + ";");
                printEpcisEvent(events[i]);
            }
        }

        private static void printEpcisEvent(EpcisEvent epcisEvent) {
            Console.WriteLine("\tId: " + epcisEvent.Id);
            Console.WriteLine("\tType: " + epcisEvent.Type);
            Console.WriteLine("\tEvent time: " + epcisEvent.EventTime);
            Console.WriteLine("\tAction: " + epcisEvent.Action);
            Console.WriteLine("\tDisposition: " + epcisEvent.Disposition);
            Console.WriteLine("\tBiz location: " + epcisEvent.BizLocation);
            Console.WriteLine("\tBiz step: " + epcisEvent.BizStep);
            Console.WriteLine("\tRead point: " + epcisEvent.ReadPoint);
            Console.WriteLine("\tEpc list: ");
            printEpcList(epcisEvent);
        }

        private static void printEpcList(EpcisEvent epcisEvent) {

            for (int i = 0; i < epcisEvent.EpcList.Count; i++) {
                Console.WriteLine("\t\t" + (i + 1) + " " + epcisEvent.EpcList[i]);
            }
        }   
    }
}