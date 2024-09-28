package com.nechet.common.util.model.checkers;

public class NameChecker implements Checked<String>{
    @Override
    public boolean check(String obj) {
        return (obj != null && !obj.trim().isEmpty());
    }
}
