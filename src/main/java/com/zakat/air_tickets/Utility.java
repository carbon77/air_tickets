package com.zakat.air_tickets;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utility {
    public static String timestampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        return dateFormat.format(timestamp);
    }
}
