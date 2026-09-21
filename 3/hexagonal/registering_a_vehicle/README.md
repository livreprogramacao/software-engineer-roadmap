Separation of concerns

A typical service does three fundamentally different kinds of work: 
it decides things (business rules — is this registration valid, can this case be closed), 
it orchestrates things (call the validator, then save, then notify), and 
it talks to things (HTTP, databases, queues). 

These are different concerns with different reasons to change. 
A new business rule shouldn’t require touching your REST controller. 
A new database shouldn’t require touching your business rules. 

When one class does all three, 
every change becomes a change to everything, 
and every test needs the whole stack running to prove anything.


domain
---
    public final class RegistrationCase
    public interface RegistrationCaseRepository
    public interface VinValidator
    public interface CaseReferenceProvider


application
---
    public interface RegisterVehicleUseCase
    public interface FindRegistrationCaseUseCase
    public class RegisterVehicleService implements RegisterVehicleUseCase
    public class RegistrationCaseQueryService implements FindRegistrationCaseUseCase


infrastructure
---
    public class RegistrationController
    public class RegistrationResolver
    public class HibernateRegistrationCaseRepository implements RegistrationCaseRepository




Em resumo, o fluxo é: 
	criar a classe → 
	registrá-la com @Component ou @Bean → 
	garantir o escaneamento → 
	injetá-la onde necessário → 
	configurar propriedades e ciclo de vida, se aplicável.

