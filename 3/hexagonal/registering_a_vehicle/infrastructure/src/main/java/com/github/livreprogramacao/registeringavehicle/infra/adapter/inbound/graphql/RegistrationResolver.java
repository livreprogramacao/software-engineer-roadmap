package com.github.livreprogramacao.registeringavehicle.infra.adapter.inbound.graphql;

import com.github.livreprogramacao.registeringavehicle.app.port.inbound.FindRegistrationCaseUseCase;
import com.github.livreprogramacao.registeringavehicle.app.port.inbound.RegisterVehicleUseCase;
import com.github.livreprogramacao.registeringavehicle.app.service.exception.RegistrationCaseNotFoundException;
import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.CaseStatus;
import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;
import com.github.livreprogramacao.registeringavehicle.domain.model.exception.UnverifiedVinException;
import graphql.GraphQLError;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Controller;

@Controller
public class RegistrationResolver {

    private final RegisterVehicleUseCase registerVehicleUseCase;
    private final FindRegistrationCaseUseCase findRegistrationCase;

    public RegistrationResolver(RegisterVehicleUseCase registerVehicleUseCase,
                                FindRegistrationCaseUseCase findRegistrationCase) {

        this.registerVehicleUseCase = registerVehicleUseCase;
        this.findRegistrationCase = findRegistrationCase;

    }

    // Inbound: GraphQL input -> value object, same discipline as the REST
    // controller — no entity construction happens here.
    @MutationMapping
    public RegistrationPayload registerVehicle(@Argument String vin) {

        CaseReference reference = registerVehicleUseCase.register( VehicleIdentificationNumber.of(vin) );
        return new RegistrationPayload(reference.value(), CaseStatus.PENDING.name());

    }

    // Outbound: same read-and-flatten job as the REST controller's GET,
    // just mapped into a GraphQL payload type instead of a JSON DTO.
    @QueryMapping
    public RegistrationPayload registrationCase(@Argument String reference) {

        RegistrationCase registrationCase = null;

        try {
            registrationCase = findRegistrationCase.findByReference(CaseReference.of(reference));
        } catch (RegistrationCaseNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new RegistrationPayload(
                registrationCase.reference().value(),
                registrationCase.status().name());

    }

    // Spring for GraphQL routes exceptions through a resolver rather than
    // @ExceptionHandler, but the job is identical: translate a domain/app
    // exception into a transport-appropriate error, once, at the edge.
    @GraphQlExceptionHandler
    public GraphQLError handleUnverifiedVin(UnverifiedVinException ex) {

        return GraphQLError.newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage())
                .build();

    }

}