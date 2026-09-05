public class CardDealer {

    private final Deck cards;

    CardDealer( final Deck cards ) {
        this.cards = cards;
    }

    public void draw() {
        Card next = cards.next();
        System.out.println( next.asText() );
    }

}