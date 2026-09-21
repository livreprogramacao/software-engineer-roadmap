package com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound;

import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;

public interface CaseReferenceProvider {
    CaseReference next();
}
