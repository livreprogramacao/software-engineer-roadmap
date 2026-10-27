README
====================

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
---

[spring-beans-explained/README.md at main](https://github.com/kamilmazurek/spring-beans-explained/blob/main/README.md)