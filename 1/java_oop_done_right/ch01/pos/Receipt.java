import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class Receipt {

    private final Printer printer;
    private final List<Item> items = new ArrayList<>();

    private BigDecimal totalAmount = BigDecimal.ZERO;

    public Receipt(Printer p) {
        this.printer = p;
    }

    public void add(String description, Money price) {
        items.add( new Item(description, price) );
        price.addAmount( this );
    }

    public void print() {

        // todo - using the field 'printer'
        items.forEach( item -> item.print(printer) );

    }

    void addTotalAmount(BigDecimal amount ) {
        totalAmount = totalAmount.add( amount );
    }

    public void totalPrice() {

        Money totalPrice = new Money( totalAmount, "GBP");

        printer.newline();

        printer.print( "Total price: ");
        totalPrice.print( printer );

        printer.newline();

    }

}