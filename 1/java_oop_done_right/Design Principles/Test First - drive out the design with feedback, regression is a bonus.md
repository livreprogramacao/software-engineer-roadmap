Esta conversa foi gerada com o Duck.ai (https://duck.ai) usando o modelo GPT-6 Luna da OpenAI. Os chats com IA podem apresentar informações incorretas ou ofensivas (consulta https://duckduckgo.com/duckai/privacy-terms para obter mais informações).

====================

Pedido do utilizador 1 de 1 - 20/10/2026, 20:17:44:
explain this design principle:
• Test First - drive out the design with feedback, regression is a bonus
use spring framework


GPT-6 Luna:
<!-- **Explaining Spring principles**

I want to explain the principles in the Spring context, with an emphasis on Test-Driven Development (TDD). I should clarify that the regression bonus refers to tests designed primarily for feedback rather than just defect detection. It might be useful to provide an example with a code sample, perhaps showing a service alongside its test. Using Spring Boot, JUnit, and Mockito would be great for illustrating this. -->
**“Test First”** means writing a test for a behavior before implementing it, then letting the test’s feedback shape the design.

The cycle is:

1. **Write a failing test** for a small piece of behavior.
2. **Write the simplest implementation** that makes it pass.
3. **Refactor** while keeping the test green.
4. Repeat.

The key idea is that tests are not only a safety net. They make you decide how a component should be used, what its responsibilities are, and where its boundaries belong. If a test is awkward to write—perhaps it needs a full application context just to test a small rule—that can be feedback that the design is too tightly coupled.

In Spring, you might test a service independently of the database and web layer:

```java
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock OrderRepository repository;
    @InjectMocks OrderService service;

    @Test
    void placesOrderAndSavesIt() {
        var request = new CreateOrderRequest("book", 2);

        service.placeOrder(request);

        verify(repository).save(argThat(order ->
            order.getProduct().equals("book")
                && order.getQuantity() == 2
        ));
    }
}
```

That test nudges the design toward a service with a clear job and a repository boundary that can be substituted. You can then add Spring-specific tests separately—for example, a `@WebMvcTest` for a controller’s HTTP behavior or a `@DataJpaTest` for repository queries—rather than loading the entire application for every test.

**“Regression is a bonus”** means catching future breakages matters, but it is not the main reason to test first. The main payoff is discovering and improving the design as you build it. A test suite can do both: guide the design now and help prevent regressions later.