package com.example.mrzhang.newsmarttraffic.enent;

/**
 *
 */
public class MessageEvent {
//    public final String message;
    private String from;
    private String date;
    private int value;


//    public MessageEvent(String message) {
//        this.message = message;
//    }

    public MessageEvent(String from, String date, int value) {
        this.from = from;
        this.date = date;
        this.value = value;
    }


    public String getFrom() {
        return from;
    }

    public String getDate() {
        return date;
    }

    public int getValue() {
        return value;
    }
}
