public class ItemFormat {

    private final Money price;
    private final String description;


    public ItemFormat(String description, Money price) {

        this.description = description;
        this.price = price;

    }


    public void print( Printer p ) {

        p.print(description);
        p.print(" ");
        price.print(p);

        p.newline();

    }
}