package com.github.livreprogramacao.registeringavehicle.app.service;

import com.github.livreprogramacao.registeringavehicle.app.port.inbound.RegisterVehicleUseCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;
import com.github.livreprogramacao.registeringavehicle.domain.model.exception.UnverifiedVinException;
import com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound.CaseReferenceProvider;
import com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound.RegistrationCaseRepository;
import com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound.VinValidator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterVehicleService implements RegisterVehicleUseCase {

    private final RegistrationCaseRepository repository;
    private final VinValidator vinValidator;
    private final CaseReferenceProvider referenceProvider;

    public RegisterVehicleService(RegistrationCaseRepository repository,
                                  VinValidator vinValidator,
                                  CaseReferenceProvider referenceProvider) {

        this.repository = repository;
        this.vinValidator = vinValidator;
        this.referenceProvider = referenceProvider;

    }

    @Transactional
    @PreAuthorize("hasAuthority('REGISTER_VEHICLE')")
    @Override
    public CaseReference register(VehicleIdentificationNumber vin) {

        CaseReference reference = referenceProvider.next();
        RegistrationCase registrationCase = RegistrationCase.open(reference, vin);

        try {

            registrationCase.approve(vinValidator.isValid(vin));

        } catch (UnverifiedVinException e) {
            throw new RuntimeException(e);
        }

        repository.save(registrationCase);
        return reference;

    }

}
