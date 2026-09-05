package com.github.livreprogramacao.registeringavehicle.app.service.exception;

import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;

public class RegistrationCaseNotFoundException extends Throwable {

    public RegistrationCaseNotFoundException(CaseReference reference) {
    }
}
