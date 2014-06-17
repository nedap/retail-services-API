using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Example
{
    class SystemExample
    {
        public static void RunExample(Client client)
        {
            // get list of systems
            List<Nedap.Retail.Api.System.V1.System> list = client.SystemV1List();

            // show list of systems and wait for key press
            Console.WriteLine("Get system list.");
            Console.WriteLine("Got " + list.Count + " systems:");
            foreach (System.V1.System system in list)
            {
                Console.WriteLine("  SystemId: " + system.SystemId);
                Console.WriteLine("  Name:     " + system.Name);
                Console.WriteLine("  Location: " + system.Location);
            }
            Console.ReadKey();

            // get systems status
            List<Nedap.Retail.Api.System.V1.SystemStatus> status = client.SystemV1Status();

            // show systems status and wait for key press
            Console.WriteLine("Get system list.");
            Console.WriteLine("Got " + status.Count + " systems:");
            foreach (Nedap.Retail.Api.System.V1.SystemStatus system in status)
            {
                Console.WriteLine("  SystemId: " + system.SystemId);
                Console.WriteLine("  Firmware: " + system.FirmwareVersion);
                Console.WriteLine("  Status:   " + system.Status);
                if (system.DetailedStatus != null)
                {
                    foreach (System.V1.StatusDetail detailedStatus in system.DetailedStatus)
                    {
                        Console.WriteLine("    " + detailedStatus.Type + " = " + detailedStatus.Status);
                    }
                }
                if (system.OfflineSince.HasValue)
                {
                    Console.WriteLine("  Offline since: " + system.OfflineSince.Value.ToLongDateString() + " " + system.OfflineSince.Value.ToLongTimeString());
                }
            }
            Console.ReadKey();
        }
    }
}
