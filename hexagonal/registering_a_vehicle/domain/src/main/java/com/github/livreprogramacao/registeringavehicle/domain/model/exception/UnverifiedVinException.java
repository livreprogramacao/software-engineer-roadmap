package com.github.livreprogramacao.registeringavehicle.domain.model.exception;

import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;

public class UnverifiedVinException extends Throwable {

    private final VehicleIdentificationNumber vin;

    public UnverifiedVinException(VehicleIdentificationNumber vin) {
        this.vin = vin;
    }

}
