package com.bardab.budgettracker.model;

public class TransactionType {
    public static final byte FOOD_0 = 0;
    public static final byte HOUSEHOLD_1 = 1;
    public static final byte SHOPPING_2 = 2;
    public static final byte HEALTH_3 = 3;
    public static final byte BILLS_4 = 4;
    public static final byte TRANSPORT_5 = 5;
    public static final byte HOBBY_6 = 6;
    public static final byte DINING_7 = 7;
    public static final byte EVENTS_8 = 8;
    public static final byte GIFTS_9 = 9;
    public static final byte INVESTMENTS_10 = 10;
    public static final byte TRAVEL_11 = 11;
    public static final byte FEES_12 = 12;
    public static final byte OTHER_13 = 13;

    public static byte[] getTransactionTypes() {
        byte[] transactionTypes = new byte[14];
        for(byte i=0;i<14;i++){ transactionTypes[i]=i;}
        return transactionTypes;
    }

}
