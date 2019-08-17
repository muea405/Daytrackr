package com.aesse.daytrackr;

public class ItemDbSchema {
    public static final class ItemTable {
        public static final String NAME = "days";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String MOOD = "mood";
            public static final String STRESS = "stress";
            public static final String EAT = "eat";
            public static final String SLEEP = "sleep";
            public static final String EX = "ex";
            public static final String NOTES = "notes";
            public static final String DATE = "date";
        }
    }
}
