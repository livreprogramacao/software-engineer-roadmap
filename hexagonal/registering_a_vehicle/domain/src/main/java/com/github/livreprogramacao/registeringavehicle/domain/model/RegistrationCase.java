package com.github.livreprogramacao.registeringavehicle.domain.model;

import com.github.livreprogramacao.registeringavehicle.domain.model.exception.UnverifiedVinException;
import org.jetbrains.annotations.NotNull;

/**
 *
 * Domain defines a RegistrationCase entity with real invariants — it can't move to APPROVED
 * status without a validated Vin — and a value object Vin that refuses to be constructed
 * from a malformed string.
 * <br/>
 * It declares an outbound port, RegistrationCaseRepository,
 * describing persistence purely in domain terms: save, findByVin.
 * <br/>
 * It also declares a VinValidator port for whatever external check determines
 * whether a VIN is legitimate.
 *
 */
public final class RegistrationCase {

    private final CaseReference reference;
    private final VehicleIdentificationNumber vin;
    private CaseStatus status;

    private RegistrationCase(CaseReference reference, VehicleIdentificationNumber vin, CaseStatus status) {

        this.reference = reference;
        this.vin = vin;
        this.status = status;

    }

    public static @NotNull RegistrationCase open(CaseReference reference, VehicleIdentificationNumber vin) {

        return new RegistrationCase(reference, vin, CaseStatus.PENDING);

    }

    public void approve(boolean vinValidated) throws UnverifiedVinException {

        if (!vinValidated) {
            throw new UnverifiedVinException(this.vin);
        }

        this.status = CaseStatus.APPROVED;

    }

    public CaseReference reference() {
        return reference;
    }

    public CaseStatus status() {
        return status;
    }

}
