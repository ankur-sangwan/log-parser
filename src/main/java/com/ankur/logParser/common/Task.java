package com.ankur.logParser.common;

public class Task {
    public String key;
    public int occurrence;

    public Task(String key, int priority) {
        this.occurrence = priority;
        this.key = key;
    }

    @Override
    public String toString() {
        return "Task{" +
                "key='" + key + '\'' +
                ", occurrence=" + occurrence +
                '}';
    }
}
