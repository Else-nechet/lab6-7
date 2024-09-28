package com.nechet.common.util.model.checkers;

public class VehicleEnginePowerChecker implements Checked<Long>{
    @Override
    public boolean check(Long obj){
        return (obj != null && obj >= 0);
    }
}
