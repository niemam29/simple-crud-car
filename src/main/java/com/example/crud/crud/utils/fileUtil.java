package com.example.crud.crud.utils;
import org.joda.time.DateTime;

public class fileUtil {
    public static String generateFileNameWithTimestamp(String filename) {
        return DateTime.now().toString("yyyy-dd-M--HH-mm-ss") + filename;
    }
}
