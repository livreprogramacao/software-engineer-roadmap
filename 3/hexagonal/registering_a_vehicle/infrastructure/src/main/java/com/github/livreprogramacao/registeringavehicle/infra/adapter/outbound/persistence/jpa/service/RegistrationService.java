package com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa.service;

import com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa.entity.Registration;
import com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final RegistrationRepository repository;

    public RegistrationService(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Registration registerVehicle(Registration registration) {
        return repository.save(registration);
    }

}
