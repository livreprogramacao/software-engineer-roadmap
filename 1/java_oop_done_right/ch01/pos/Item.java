import java.math.BigDecimal;

public class Item {

    private final String description;
    private final Money price;

    public Item( String description, Money price ) {
        this.description = description;
        this.price = price;
    }

    public void print( Printer p ) {
        new ItemFormat(description, price).print(p);
    }

}