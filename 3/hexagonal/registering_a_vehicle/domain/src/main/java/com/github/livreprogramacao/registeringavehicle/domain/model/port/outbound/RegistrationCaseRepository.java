package com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound;

import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;

import java.util.Optional;

public interface RegistrationCaseRepository {
    void save(RegistrationCase registrationCase);
    Optional<RegistrationCase> findByVin(VehicleIdentificationNumber vin);
    Optional<RegistrationCase> findByReference(CaseReference reference);
}
