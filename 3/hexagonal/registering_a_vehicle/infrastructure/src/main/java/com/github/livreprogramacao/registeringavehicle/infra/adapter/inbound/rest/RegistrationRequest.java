package com.github.livreprogramacao.registeringavehicle.infra.adapter.inbound.rest;

import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.CaseStatus;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;

public record RegistrationRequest( CaseReference reference, VehicleIdentificationNumber vin, CaseStatus status ) {

    public RegistrationRequest {
    }

    //    public String vin() {
//        // TODO
//        return "1HGCM82633A123456";
//    }

}
