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
                string cap = client.EpcisV1_1.Capture(events);
                Console.WriteLine("captured epcis events");
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
                EventTime = DateTime.Parse("2014-02-01T12:00:00.000+01:00"),
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
                EventTime = DateTime.Parse("2014-02-01T12:01:00.000+01:00"),
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