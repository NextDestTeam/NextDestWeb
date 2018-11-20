package com.happyweekend.enumarator;

public enum PersonTypeEnum {
    ADM(1),
    EVENT_MANAGER(2),
    COMMON_USER(3);

    PersonTypeEnum(int type){
        value = type;
    }

    private int value;

    public int getValue(){
        return value;
    }
}
