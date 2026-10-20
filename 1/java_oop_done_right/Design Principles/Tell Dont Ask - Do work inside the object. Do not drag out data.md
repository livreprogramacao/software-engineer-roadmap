explain this principle:
Tell Don’t Ask - Do work inside the object. Do not drag out data.
use java

GPT-6 Luna:
**“Tell, Don’t Ask”** means: instead of asking an object for its data, making a decision elsewhere, and then telling it what to do, **tell the object what you want done and let it use its own data to do the work**.

### Asking for data

```java
if (order.getStatus() == OrderStatus.PENDING) {
    order.setStatus(OrderStatus.PAID);
}
```

The caller knows the order’s internal state and decides how to change it.

### Telling the object

```java
order.markPaid();
```

```java
public class Order {
    private OrderStatus status;

    public void markPaid() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be paid");
        }

        status = OrderStatus.PAID;
    }
}
```

Now `Order` owns the rule about when it can be marked paid. Callers do not need to retrieve its status or know how to update it.

A useful rule of thumb: **ask objects for information when you need to display or report it; tell them to perform behavior when that behavior depends on their own state.**

This helps keep rules together, reduces duplicated logic, and prevents invalid state changes. It does not mean “never use getters”—the goal is to avoid using getters to make decisions about another object’s internal state.