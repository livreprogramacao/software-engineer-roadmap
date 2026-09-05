package com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound;

import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound.CaseReferenceProvider;

public class DatabaseReferenceProvider implements CaseReferenceProvider {

    @Override
    public CaseReference next() {
        return new CaseReference("REG12345678");
    }

}
