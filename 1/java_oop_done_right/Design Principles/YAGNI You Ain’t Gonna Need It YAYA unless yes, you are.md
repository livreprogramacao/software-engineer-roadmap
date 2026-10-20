Esta conversa foi gerada com o Duck.ai (https://duck.ai) usando o modelo GPT-6 Luna da OpenAI. Os chats com IA podem apresentar informações incorretas ou ofensivas (consulta https://duckduckgo.com/duckai/privacy-terms para obter mais informações).

====================

Pedido do utilizador 1 de 1 - 20/10/2026, 20:08:30:
explain this design principle:
• YAGNI You Ain’t Gonna Need It (YAYA unless yes, you are)
use spring framework

GPT-6 Luna:
<!--  -->
**YAGNI** means **“You Aren’t Gonna Need It”**: don’t build features or abstractions until there’s a real need for them. “YAYA—unless yes, you are” is a playful reminder that adding something is reasonable when you *do* have a concrete requirement.

In Spring, for example, suppose an app currently sends email through one provider. You could start with a small service:

```java
@Service
public class NotificationService {
    private final EmailSender emailSender;

    public NotificationService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void notifyUser(String address, String message) {
        emailSender.send(address, message);
    }
}
```

If there’s no requirement to support multiple providers, avoid building a provider registry, database-driven routing, plugin system, and elaborate configuration “just in case.” Add that flexibility when a real use case appears. Spring can manage and inject the service and its dependencies, but YAGNI still applies to what you choose to build. [1]

YAGNI **doesn’t** mean “never plan ahead.” It means prefer the simplest design that meets today’s requirements, while keeping it straightforward to extend when tomorrow’s requirements become known.

Ler mais
[1] spring-beans-explained/README.md at main — https://github.com/kamilmazurek/spring-beans-explained/blob/main/README.md