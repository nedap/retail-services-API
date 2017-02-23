/**
The MIT License (MIT)

Copyright (c) 2015 Nedap Retail

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package main

import (
    "fmt"
    "os"
    "time"
    "nedap.com/retail/oauth"
    "nedap.com/retail/idcloud"
    "github.com/nathanwinther/go-uuid4"
)

const LOCATION_ID string = "http://nedapretail.com/loc/testing"

func main() {

    if len(os.Args) < 5 {
        printSyntax()
    }

    fmt.Printf("*** EPCIS API example ***\n")

    apiUrl := os.Args[1]
    oauthUrl := os.Args[2]
    clientId := os.Args[3]
    secret := os.Args[4]

    oauthClient := oauth.NewAccessTokenResolverApiWithBasePath(oauthUrl)
    oauthResp, _, err := oauthClient.Resolve("client_credentials", clientId, secret)

    if err != nil {
        fmt.Printf("ERROR: Unable to retrieve an access token from url: %s\n", oauthUrl)
        os.Exit(2)
    }

    epcisClient := idcloud.NewEpcisApiWithBasePath(apiUrl)
    epcisClient.Configuration.AccessToken = oauthResp.AccessToken

    epcisEventsList := new(idcloud.EpcisEventContainer)
    epcisEventsList.Events = append(epcisEventsList.Events, *createEpcisEvent1(LOCATION_ID))
    epcisEventsList.Events = append(epcisEventsList.Events, *createEpcisEvent2(LOCATION_ID))

    fmt.Printf("Capturing EPCIS events...\n")
    _, err = epcisClient.Capture(*epcisEventsList)

    if err != nil {
        fmt.Printf("ERROR: Unable to capture EPCIS events\n")
        os.Exit(2)
    }

    printEpcisEvents(epcisEventsList.Events)

    fmt.Printf("Retrieved EPCIS events within last minute:\n\n")
    events, _, err := epcisClient.Query(*makeEpcisQueryParameters());

    if err != nil {
        fmt.Printf("ERROR: Unable to query EPCIS events\n")
        os.Exit(2)
    }

    printEpcisEvents(events)
}

func printSyntax() {
    fmt.Printf("Syntax: ./main <api_url> <oauth_url> <clientid> <secret>\n\n")
    os.Exit(3)
}

func printEpcisEvents(events []idcloud.EpcisEvent) {

    for i, v := range events {
        fmt.Printf("EPCIS event %d\n", (i + 1))
        printEpcisEvent(v)
    }
}

func printEpcisEvent(event idcloud.EpcisEvent) {
    fmt.Println("")
    fmt.Printf("\tId: %s\n", event.Id)
    fmt.Printf("\tType: %s\n", event.Type_)
    fmt.Printf("\tEvent time: %s\n", event.EventTime.Format(time.RFC3339))
    fmt.Printf("\tAction: %s\n", event.Action)
    fmt.Printf("\tDisposition: %s\n", event.Disposition)
    fmt.Printf("\tBiz location: %s\n", event.BizLocation)
    fmt.Printf("\tBiz step: %s\n", event.BizStep)
    fmt.Printf("\tRead point: %s\n", event.ReadPoint)
    printEpcList(event)
}

func printEpcList(event idcloud.EpcisEvent) {
    fmt.Printf("\n")

    for i, v := range event.EpcList {
        fmt.Printf("\t\t%d.%s\n", (i + 1), v)
    }
}

func createUuid4() string {
    u, err := uuid4.New()
    if err != nil {
        panic(err)
    }
    return u
}

func createEpcisEvent1(locationId string) *idcloud.EpcisEvent {
    event := new(idcloud.EpcisEvent)
    event.Type_ = "object_event"
    event.Id = createUuid4()
    event.EventTime = time.Now()
    event.Action = "OBSERVE"
    event.Disposition = "urn:epcglobal:cbv:disp:sellable_accessible"
    event.BizLocation = locationId
    event.BizStep = "urn:epcglobal:cbv:bizstep:cycle_counting"
    event.ReadPoint = locationId
    event.EpcList = makeEpcList1()

    return event;
}

func createEpcisEvent2(locationId string) *idcloud.EpcisEvent {
    event := new(idcloud.EpcisEvent)
    event.Type_ = "object_event"
    event.Id = createUuid4()
    event.EventTime = time.Now()
    event.Action = "OBSERVE"
    event.Disposition = "urn:epcglobal:cbv:disp:sellable_accessible"
    event.BizLocation = locationId
    event.BizStep = "urn:epcglobal:cbv:bizstep:cycle_counting"
    event.ReadPoint = locationId
    event.EpcList = makeEpcList2()
    event.ErrorDeclaration =

    return event;
}

func makeEpcList1() []string {
    return []string{"urn:epc:id:sgtin:2011200.000111.1",
        "urn:epc:id:sgtin:2011200.090001.1",
        "urn:epc:id:sgtin:2011200.000101.1",
        "urn:epc:id:sgtin:2011200.000102.1",
        "urn:epc:id:sgtin:2011200.000103.1"}
}

func makeEpcList2() []string {
    return []string{"urn:epc:id:sgtin:2011200.000111.2",
        "urn:epc:id:sgtin:2011200.090002.1",
        "urn:epc:id:sgtin:2011200.000201.1",
        "urn:epc:id:sgtin:2011200.000202.1",
        "urn:epc:id:sgtin:2011200.000203.1"}
}

func makeEpcisQueryParameters() *idcloud.EpcisQueryParameters {
    queryParameters := new(idcloud.EpcisQueryParameters);
    queryParameters.Parameters = []idcloud.ParameterObject{*makeParameterObject1()}
    return queryParameters
}

func makeParameterObject1() *idcloud.ParameterObject {
    parameter := new(idcloud.ParameterObject)
    parameter.Name = "GE_event_time";
    parameter.Value = (time.Now().Add(time.Duration(-10)*time.Minute)).Format(time.RFC3339)
    return parameter;
}