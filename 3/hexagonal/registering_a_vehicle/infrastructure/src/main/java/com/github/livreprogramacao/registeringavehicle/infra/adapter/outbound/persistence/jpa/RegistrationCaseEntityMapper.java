package com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa;

import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegistrationCaseEntityMapper {

    public Optional<RegistrationCase> toDomain() {
        // TODO
        return Optional.empty();
    }

    public Object toEntity(RegistrationCase registrationCase) {
        // TODO
        return null;
    }

}
