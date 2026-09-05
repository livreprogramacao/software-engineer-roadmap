package com.github.livreprogramacao.registeringavehicle.app.port.inbound;

import com.github.livreprogramacao.registeringavehicle.app.service.exception.RegistrationCaseNotFoundException;
import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;

public interface FindRegistrationCaseUseCase {
    RegistrationCase findByReference(CaseReference reference) throws RegistrationCaseNotFoundException;
}
