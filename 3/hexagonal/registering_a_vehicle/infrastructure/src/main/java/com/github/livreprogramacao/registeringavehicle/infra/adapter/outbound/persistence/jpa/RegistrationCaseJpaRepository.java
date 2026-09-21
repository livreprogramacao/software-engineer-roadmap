package com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa;

import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RegistrationCaseJpaRepository {

    public Optional<RegistrationCase> findByVin(VehicleIdentificationNumber value) {
        return Optional.empty();
    }

    public void save(Object entity) {
        // TODO
    }

}