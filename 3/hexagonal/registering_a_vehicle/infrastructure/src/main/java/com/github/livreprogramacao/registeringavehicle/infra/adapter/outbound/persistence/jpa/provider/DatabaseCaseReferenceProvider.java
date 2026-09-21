package com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa.provider;

import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound.CaseReferenceProvider;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCaseReferenceProvider implements CaseReferenceProvider {

    @Override
    public CaseReference next() {
        return CaseReference.of( "REG12345678" );
    }

}
