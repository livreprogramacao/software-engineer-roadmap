README
====================

“**Prefer interfaces/implementation over inheritance trees**” means: define what a component *can do* with an interface, then provide one or more classes that do it. Avoid building deep `extends` chains just to share code.

In Spring, this fits dependency injection: application code depends on an interface, and Spring supplies an implementation. That keeps the code less tied to a particular class and makes implementations easier to replace or test.

```java
public interface NotificationSender {
    void send(String recipient, String message);
}

@Service
public class EmailNotificationSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        // send email
    }
}

@Service
public class OrderService {
    private final NotificationSender notificationSender;

    public OrderService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    public void confirmOrder(String email) {
        notificationSender.send(email, "Your order is confirmed.");
    }
}
```

`OrderService` relies on the capability `NotificationSender`, not on the concrete email class. You could add an SMS implementation or inject a test double without changing `OrderService`.

By contrast, an inheritance tree might have `BaseNotificationSender` → `EmailSender` → `TransactionalEmailSender`. That can become brittle: subclasses inherit behavior and assumptions they may not need, and changes to a parent can unexpectedly affect children.

This principle is related to—but not exactly the same as—**composition over inheritance**. Use interfaces to define contracts; use composition when a class needs to reuse or combine behavior from other objects. Inheritance is still appropriate for a genuine “is-a” relationship and a stable shared contract, but avoid using it just to borrow implementation. [1,2]


Ler mais
---

[Replacing Inheritance with Composition](https://www.javacodegeeks.com/2015/05/replacing-inheritance-with-composition.html)
[Inheritance and Composition (Is-a vs Has-a relationship) in ...](https://www.baeldung.com/java-inheritance-composition)