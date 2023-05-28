package com.phonebook.constant;

public enum  PhonebookConstants {
    RESTORE("restore");

    private final String value ;

    PhonebookConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
