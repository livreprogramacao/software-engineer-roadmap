package com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa;

import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;
import com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound.RegistrationCaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HibernateRegistrationCaseRepository implements RegistrationCaseRepository {

    private final RegistrationCaseJpaRepository jpaRepository;
    private final RegistrationCaseEntityMapper mapper;

    public HibernateRegistrationCaseRepository(RegistrationCaseJpaRepository jpaRepository,
                                               RegistrationCaseEntityMapper mapper) {

        this.jpaRepository = jpaRepository;
        this.mapper = mapper;

    }

    @Override
    public void save(RegistrationCase registrationCase) {
        jpaRepository.save(mapper.toEntity(registrationCase));
    }

    @Override
    public Optional<RegistrationCase> findByVin(VehicleIdentificationNumber vin) {
        // TODO
        return Optional.empty(); // jpaRepository.findByVin(vin.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<RegistrationCase> findByReference(CaseReference caseReference) {
        return Optional.empty();
    }

}
