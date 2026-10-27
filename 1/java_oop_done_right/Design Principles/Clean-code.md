README
====================

For clean code with Spring, aim for code that’s easy to read, test, and change—not just code that follows a particular style. These guidelines are a practical starting point:

- **Give each class one clear responsibility.** Keep web, business, and persistence concerns separate: controllers handle HTTP, services coordinate business operations, and repositories handle data access.
- **Use meaningful names.** Prefer `createOrder` or `findCustomerById` over vague names like `process` or `getData`.
- **Keep methods focused.** Break up long methods and complex conditionals; avoid classes with too many unrelated duties.
- **Prefer constructor injection.** It makes dependencies explicit and supports unit testing. Use `final` fields when possible; avoid field injection.
- **Keep controllers thin.** Validate requests, call application logic, and shape responses there; put business rules in services or domain objects.
- **Use Spring features intentionally.** Choose annotations such as `@Service`, `@Repository`, and `@Transactional` where they clarify responsibilities. Avoid adding abstractions or annotations without a concrete need.
- **Handle errors deliberately.** Don’t swallow exceptions or return ambiguous values. Use meaningful domain exceptions and consistent API error responses.
- **Make state and side effects clear.** Prefer immutable data where practical; be especially careful with mutable singleton Spring beans, which are shared across requests.
- **Test behavior at the right level.** Unit-test business logic, and use Spring integration tests where framework wiring or persistence behavior matters.
- **Keep configuration out of business logic.** Use externalized configuration and typed configuration properties for settings.
- **Refactor and automate checks.** Remove duplication when it improves clarity, and use formatting, static analysis, tests, and code review as part of the build.

A simple flow might be: `Controller → Service → Repository`, but don’t create a layer for every class by default. The goal is clarity, not ceremony. [1,2,3]

Ler mais
[1] Best practices in Coding, Designing and Architecting Java ... — https://github.com/in28minutes/java-best-practices
[2] Clean Coding in Java — https://www.baeldung.com/java-clean-code
[3] Best Practices for Writing Clean Code in Java — https://javapro.io/2025/11/25/best-practices-for-writing-clean-code-in-java/