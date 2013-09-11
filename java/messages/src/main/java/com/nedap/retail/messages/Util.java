package com.nedap.retail.messages;

import java.util.*;
import javax.xml.bind.DatatypeConverter;

/**
 * Utilities.
 */
public class Util {

    public static String getString(Map params, String key) throws InvalidMessage {
        if (params != null) {
            Object value = params.get(key);
            if (value != null) {
                return value.toString();
            }
        }
        throw new InvalidMessage("required field missing: " + key);
    }

    public static String getString(Map params, String key, String defaultValue) {
        if (params != null) {
            Object value = params.get(key);
            if (value != null) {
                return value.toString();
            }
            return defaultValue;
        }
        return defaultValue;
    }

    public static int getInt(Map params, String key) throws InvalidMessage {
        Object value = params.get(key);
        if (value == null) {
            throw new InvalidMessage("required field missing: " + key);
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (Exception ex) {
                throw new InvalidMessage("invalid numeric value: " + value);
            }
        } else {
            throw new InvalidMessage("invalid numeric value: " + value);
        }
    }

    public static boolean getBool(Map params, String key, boolean defaultValue) throws InvalidMessage {
           if (params == null) {
            return defaultValue;
        }
        Object value = params.get(key);
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            try {
                return Boolean.parseBoolean((String) value);
            } catch (Exception ex) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static int getInt(Map params, String key, int defaultValue) {
        if (params == null) {
            return defaultValue;
        }
        Object value = params.get(key);
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (Exception ex) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static long getLong(Map params, String key) throws InvalidMessage {
        Object value = params.get(key);
        if (value == null) {
            throw new InvalidMessage("required field missing: " + key);
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (Exception ex) {
                throw new InvalidMessage("invalid numeric value: " + value);
            }
        } else {
            throw new InvalidMessage("invalid numeric value: " + value);
        }
    }

    public static long getLong(Map params, String key, long defaultValue) {
        Object value = params.get(key);
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (Exception ex) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static Date getDate(Object time) throws InvalidMessage {
        if (time == null) {
            return null;
        } else if (time instanceof String) {
            // time=2011-05-04T11:26:45+02:00,
            String f = (String) time;
            try {
                Calendar cal = DatatypeConverter.parseDateTime(f);
                return cal.getTime();
            } catch (java.lang.IllegalArgumentException ex) {
                throw new InvalidMessage("Invalid timestamp: " + f);
            }
        } else if (time instanceof Date) {
            return (Date) time;
        } else if (time instanceof Long) {
            long t = (Long) time;
            return new Date(t);
        } else {
            // Invalid timestamp. Just use the timestamp in the message.
            return null;
        }
    }

    public static Date getDate(Map params, String key) throws InvalidMessage {
        final Object time = params.get(key);
        if (time == null) {
            throw new InvalidMessage("required field missing: " + key);
        } else  {
            return getDate(time);
        }
    }

    public static List getList(Map params, String key) throws InvalidMessage {
        Object value = params.get(key);
        if (value == null) {
            throw new InvalidMessage("required field missing: " + key);
        } else if (value instanceof List) {
            return (List) value;
        } else {
            throw new InvalidMessage("expected 'list' for: " + key);
        }
    }

    /**
     * Checks if List is null, and if so, returns an empty list
     * @param other
     * @return 
     */
    public static List safe( List other ) {
        return other == null ? Collections.EMPTY_LIST : other;
    }


    public static Map getMap(Map params, String key, Map defaultValue) {
        Object value = params.get(key);
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Map) {
            return (Map) value;
        } else {
            return defaultValue;
        }
    }
}
