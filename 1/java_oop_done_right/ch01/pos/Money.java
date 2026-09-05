import java.math.BigDecimal;

public class Money {

    private final BigDecimal amount;
    private final String currency;


    public Money(BigDecimal amount, String currency) {

        this.amount = amount;
        this.currency = currency;

    }


    public void print( Printer p ) {

        p.print(currency);
        p.print(" ");
        p.print(amount.toString());

    }


    public void addAmount( Receipt r ) {

        r.addTotalAmount( amount );

    }

}