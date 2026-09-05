package com.github.livreprogramacao.registeringavehicle.app.port.inbound;

import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;

public interface RegisterVehicleUseCase {
    CaseReference register(VehicleIdentificationNumber vin);
}
