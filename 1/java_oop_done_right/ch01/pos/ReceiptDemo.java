import java.math.BigDecimal;

public class ReceiptDemo {

    public static void main(String args[]) {

        new ReceiptDemo().run();
    }

    private void run() {

        Receipt r = new Receipt( new Printer() );

        // Nice cheese and wine evening.
        r.add("Brie", new Money( new BigDecimal("1.95"), "GBP") );
        r.add("Tiger bread", new Money( new BigDecimal("0.95"), "GBP") );
        r.add("Merlot", new Money( new BigDecimal("7.95"), "GBP") );

        r.print();
        r.totalPrice();
    }
}