package com.github.livreprogramacao.registeringavehicle.infra.adapter.inbound.rest;

import com.github.livreprogramacao.registeringavehicle.app.port.inbound.FindRegistrationCaseUseCase;
import com.github.livreprogramacao.registeringavehicle.app.port.inbound.RegisterVehicleUseCase;
import com.github.livreprogramacao.registeringavehicle.app.service.exception.RegistrationCaseNotFoundException;
import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;
import com.github.livreprogramacao.registeringavehicle.domain.model.exception.UnverifiedVinException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegisterVehicleUseCase registerVehicle;
    private final FindRegistrationCaseUseCase findRegistrationCase;
    private RegistrationCase registrationCase;

    public RegistrationController(RegisterVehicleUseCase registerVehicle,
                                  FindRegistrationCaseUseCase findRegistrationCase) {
        this.registerVehicle = registerVehicle;
        this.findRegistrationCase = findRegistrationCase;
    }

    // Inbound: wire request -> value object. Never a domain entity here;
    // constructing RegistrationCase is the application/domain's job.
    @PostMapping
    public ResponseEntity<RegistrationResponse> register( @RequestBody RegistrationRequest request ) {

        CaseReference reference = registerVehicle.register(VehicleIdentificationNumber.of(request.vin()));
        return ResponseEntity.ok(new RegistrationResponse(reference.value(), registrationCase.status().name()));

    }

    // Outbound: reading a domain entity to populate the wire shape is the
    // controller's ordinary job — flattening, not constructing.
    @GetMapping("/{reference}")
    public ResponseEntity<RegistrationResponse> get( @PathVariable String reference ) {

        RegistrationCase registrationCase;

        try {

            registrationCase = findRegistrationCase.findByReference(CaseReference.of(reference));

        } catch (RegistrationCaseNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(new RegistrationResponse(
                registrationCase.reference().value(),
                registrationCase.status().name()));

    }

    // Domain/app exceptions carry no HTTP concept of their own; translation
    // to a status happens once, here, at the edge.
    @ExceptionHandler(UnverifiedVinException.class)
    public ProblemDetail handleUnverifiedVin(UnverifiedVinException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT);
        problem.setTitle("VIN could not be verified");
        problem.setDetail(ex.getMessage());

        return problem;

    }

    @ExceptionHandler(RegistrationCaseNotFoundException.class)
    public ProblemDetail handleNotFound(RegistrationCaseNotFoundException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Registration case not found");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/registrations/vehicle-not-found"));

        return problem;

    }

}
