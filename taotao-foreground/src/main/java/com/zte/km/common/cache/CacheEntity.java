package com.zte.km.common.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CacheEntity {
    private String Key;
    private Integer TimeOut;
    private TimeUnit TimeUnitValue;

    public CacheEntity(String key, Integer timeOut, TimeUnit timeUnitValue) {
        Key = key;
        TimeOut = timeOut;
        TimeUnitValue = timeUnitValue;
    }

    public CacheEntity(String key, Integer timeOut) {
        Key = key;
        TimeOut = timeOut;
        TimeUnitValue = TimeUnit.MINUTES;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public Integer getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(Integer timeOut) {
        TimeOut = timeOut;
    }

    public TimeUnit getTimeUnitValue() {
        return TimeUnitValue;
    }

    public void setTimeUnitValue(TimeUnit timeUnitValue) {
        TimeUnitValue = timeUnitValue;
    }
}
