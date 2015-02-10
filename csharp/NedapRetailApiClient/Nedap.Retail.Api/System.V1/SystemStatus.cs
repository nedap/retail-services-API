using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.System.V1
{
    /// <summary>
    /// Status of a system
    /// </summary>
    public class SystemStatus : JsonPrintableObject
    {
        /// <summary>
        /// System ID that uniquely identifies the system
        /// </summary>
        [JsonProperty("system_id")]
        public String SystemId { get; private set; }

        /// <summary>
        /// Firmware version of the system
        /// </summary>
        [JsonProperty("firmware_version")]
        public String FirmwareVersion { get; private set; }

        /// <summary>
        /// Overall system status
        /// </summary>
        [JsonProperty("status")]
        [JsonConverter(typeof(StringEnumConverter))]
        public Status Status { get; private set; }

        /// <summary>
        /// Detailed status per status type (if available)
        /// </summary>
        [JsonProperty("detailed_status")]
        public List<StatusDetail> DetailedStatus { get; private set; }

        /// <summary>
        /// If status is offline, this contains the date and time since when the system has been offline
        /// </summary>
        [JsonProperty("offline_since")]
        public DateTime? OfflineSince { get; private set; }
    }

    /// <summary>
    /// Possible statusses
    /// </summary>
    public enum Status
    {
        /// <summary>
        /// OK
        /// </summary>
        OK,
        /// <summary>
        /// Warning
        /// </summary>
        WARNING,
        /// <summary>
        /// Critical
        /// </summary>
        CRITICAL,
        /// <summary>
        /// Unkown
        /// </summary>
        UNKNOWN,
        /// <summary>
        /// Offline
        /// </summary>
        OFFLINE
    }

    /// <summary>
    /// All types of statuses we may know
    /// </summary>
    public enum StatusType
    {
        /// <summary>
        /// Unknown
        /// </summary>
        Unknown = 0,
        /// <summary>
        /// Online
        /// </summary>
        Online = 1,
        /// <summary>
        /// Excessive alarms
        /// </summary>
        ExcessiveAlarms = 10,
        /// <summary>
        /// EAS protocol error
        /// </summary>
        EASProtocolError = 12,
        /// <summary>
        /// EAS protocol slow
        /// </summary>
        EASProtocolResponsiveness = 14,
        /// <summary>
        /// False alarms are suspected
        /// </summary>
        FalseAlarmsSuspected = 16,
        /// <summary>
        /// RFID reader is connected
        /// </summary>
        RFIDReaderConnected = 20,
        /// <summary>
        /// RFID reader automatic reconnects
        /// </summary>
        RFIDReaderReconnects = 21,
        /// <summary>
        /// RFID reader is running
        /// </summary>
        RFIDReaderRunning = 22,
        /// <summary>
        /// RFID reader power error
        /// </summary>
        RFIDReaderPowerError = 24,
        /// <summary>
        /// RFID reader antenna error
        /// </summary>
        RFIDAntennaError = 25,
        /// <summary>
        /// RFID reader sync error
        /// </summary>
        RFIDReaderSyncError = 26,
        /// <summary>
        /// RFID reader other error
        /// </summary>
        RFIDReaderOtherError = 27,
        /// <summary>
        /// RFID Coax cables
        /// </summary>
        RFIDCoaxCables = 28,
        /// <summary>
        /// All RFID readers have the same firmware
        /// </summary>
        RFIDReadersSameFirmware = 29,
        /// <summary>
        /// All units are active
        /// </summary>
        AllUnitsActive = 30,
        /// <summary>
        /// All units have the same firmware version
        /// </summary>
        UnitsSameFirmware = 32,
        /// <summary>
        /// Free disk space
        /// </summary>
        UnitsDiskSpace = 34,
        /// <summary>
        /// Direction sensors are connected
        /// </summary>
        DirectionSensorsConnected = 40,
        /// <summary>
        /// Direction sensors are not blocked
        /// </summary>
        DirectionSensorsNotBlocked = 42,
        /// <summary>
        /// System is muted
        /// </summary>
        MutingStatus = 50,
        /// <summary>
        /// Connection to OST
        /// </summary>
        OSTConnected = 60,
        /// <summary>
        /// Local device database
        /// </summary>
        RenosDatabase = 70
    }
}