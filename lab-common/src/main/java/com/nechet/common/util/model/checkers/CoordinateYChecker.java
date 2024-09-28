package com.nechet.common.util.model.checkers;

public class CoordinateYChecker implements Checked<Integer>{
    @Override
    public boolean check(Integer obj){
        return (obj != null && obj >= -512);
    }
}
