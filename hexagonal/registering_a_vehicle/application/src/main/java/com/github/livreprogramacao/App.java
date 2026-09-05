package com.github.livreprogramacao;

import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;
import com.github.livreprogramacao.registeringavehicle.domain.model.exception.UnverifiedVinException;

import static com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference.reference;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )
    {
        new App().testUseCase();
        System.out.println( "Hello World!" );
    }

    private void testUseCase() {

        CaseReference reference = CaseReference.of( "REG12345678" );
        VehicleIdentificationNumber vin = VehicleIdentificationNumber.of( "1HGCM82633A123456" );

        RegistrationCase registrationCase = RegistrationCase.open(reference, vin);

        try {

            registrationCase.approve( Boolean.TRUE );

        } catch (UnverifiedVinException e) {
            throw new RuntimeException(e);
        }

    }

}
