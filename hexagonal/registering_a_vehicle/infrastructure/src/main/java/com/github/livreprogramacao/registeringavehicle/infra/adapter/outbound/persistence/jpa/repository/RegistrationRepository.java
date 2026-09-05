package com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa.repository;


import com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Optional<Registration> findByLicensePlate(String licensePlate);

}
