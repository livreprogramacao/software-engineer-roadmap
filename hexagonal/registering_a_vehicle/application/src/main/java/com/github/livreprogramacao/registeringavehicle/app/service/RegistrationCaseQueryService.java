package com.github.livreprogramacao.registeringavehicle.app.service;

import com.github.livreprogramacao.registeringavehicle.app.port.inbound.FindRegistrationCaseUseCase;
import com.github.livreprogramacao.registeringavehicle.app.service.exception.RegistrationCaseNotFoundException;
import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound.RegistrationCaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationCaseQueryService implements FindRegistrationCaseUseCase {

    private final RegistrationCaseRepository repository;

    public RegistrationCaseQueryService(RegistrationCaseRepository repository) {

        this.repository = repository;

    }

    @Transactional(readOnly = true)
    @Override
    public RegistrationCase findByReference(CaseReference reference) throws RegistrationCaseNotFoundException {

        return repository.findByReference(reference)
                .orElseThrow(() -> new RegistrationCaseNotFoundException(reference));

    }

}
