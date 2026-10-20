Esta conversa foi gerada com o Duck.ai (https://duck.ai) usando o modelo GPT-6 Luna da OpenAI. Os chats com IA podem apresentar informações incorretas ou ofensivas (consulta https://duckduckgo.com/duckai/privacy-terms para obter mais informações).

====================

Pedido do utilizador 1 de 1 - 20/10/2026, 20:20:57:

explain this design principle:
• Prefer Composition over Inheritance - nearly always
use spring framework


GPT-6 Luna:
“Prefer composition over inheritance” means **build a class by giving it collaborators that provide behavior, rather than making it a subclass of a class that bundles that behavior**.

In Spring, this fits naturally with dependency injection: a class declares what it needs, and Spring supplies those objects. Constructor injection makes those dependencies clear. Spring’s container assembles collaborating objects into the application. [1]

For example, inheritance might look like this:

```java
class EmailNotificationService extends SmtpClient {
    void notifyUser(String message) {
        sendEmail(message);
    }
}
```

Now the notification service is tied to `SmtpClient`’s implementation and inherits all its behavior, whether it needs it or not.

With composition, the service *uses* a mail sender instead:

```java
public interface MailSender {
    void send(String message);
}

@Service
public class NotificationService {
    private final MailSender mailSender;

    public NotificationService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void notifyUser(String message) {
        mailSender.send(message);
    }
}
```

Spring can inject the `MailSender` bean. You can provide different implementations—for example, an SMTP sender in production and a fake sender in a test—without changing `NotificationService`.

Composition is usually preferable because it keeps classes focused, makes dependencies explicit, and makes behavior easier to swap or test. Inheritance is still useful when there’s a genuine “is-a” relationship and subclasses can safely honor the parent’s contract; “nearly always” is a guideline, not an absolute ban.

Ler mais
[1] Spring Framework Reference Documentation - DevDoc — https://devdoc.net/javaweb/spring/Spring-4.2.3/spring-framework-reference/htmlsingle/