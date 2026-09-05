import com.github.livreprogramacao.registeringavehicle.domain.model.CaseReference;
import com.github.livreprogramacao.registeringavehicle.domain.model.RegistrationCase;
import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;
import com.github.livreprogramacao.registeringavehicle.domain.model.exception.UnverifiedVinException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );

        new App().testUseCase();
    }

    private void testUseCase() {

        CaseReference refence = new CaseReference( "REG12345678" );
        VehicleIdentificationNumber vin = VehicleIdentificationNumber.of( "1HGCM82633A123456" );

        RegistrationCase registrationCase = RegistrationCase.open(refence, vin);
        System.out.format("Status %s for a new registration\n", registrationCase.status());

        try {

            registrationCase.approve(Boolean.TRUE);
            System.out.format("Status %s for a approved registration.\n", registrationCase.status());

        } catch (UnverifiedVinException e) {

            throw new RuntimeException(e);

        }
    }
}
