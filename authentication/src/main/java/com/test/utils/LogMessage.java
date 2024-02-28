package com.test.utils;

public class LogMessage {

    public static String logInfo(String log) {
        String[] fieldsToMask = {"password"};

        String maskedLog = log;
        for (String field : fieldsToMask) {
            maskedLog = maskedLog.replaceAll(field + "=\\S+", field + "=?");
        }

        return maskedLog;
    }
}
