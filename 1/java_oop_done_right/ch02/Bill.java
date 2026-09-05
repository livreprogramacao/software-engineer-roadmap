public class Bill {

    private float total;

    public void add(float itemPrice) {
        this.total += itemPrice;
    }

    public float getTotal() {
        return total;
    }

}