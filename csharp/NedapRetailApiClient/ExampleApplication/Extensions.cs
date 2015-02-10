using System;
using System.IO;
using System.Net;

namespace Nedap.Retail.Api.Example
{
    public static class Extensions
    {
        public static void Print(this WebException e)
        {
            Console.WriteLine("Exception occured:");
            Console.WriteLine(e.Status);
            HttpWebResponse r = (HttpWebResponse)e.Response;
            Console.WriteLine(r.StatusCode);
            Stream responseStream = r.GetResponseStream();

            string responseText;
            if (responseStream != null)
            {
                using (StreamReader reader = new StreamReader(responseStream))
                {
                    responseText = reader.ReadToEnd();
                }
                Console.WriteLine(responseText);
            }
        }
    }
}