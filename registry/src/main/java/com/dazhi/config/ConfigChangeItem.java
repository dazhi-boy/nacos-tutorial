package com.dazhi.config;

public class ConfigChangeItem {
    private String key;
    private String oldValue;
    private String newValue;


    public ConfigChangeItem(String key, String oldValue, String newValue) {
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return "ConfigChangeItem{" +
                "key='" + key + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                '}';
    }
}
