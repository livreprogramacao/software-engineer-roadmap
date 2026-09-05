public class BillCalculatorTest {

    @Test
    public void correctTotalForTwoItems() {

        // Arrange
        var bill = new Bill();

        // Act
        bill.add(12.95);
        bill.add(2.95);

        // Assert
        assertThat(total).isEqualTo(12.95 + 2.05);
    }

    @Test
    public void correctTotalForOneItem() {

        // Arrange
        var calculator = new BillCalculator();

        // Act
        calculator.add(12.95);
        float total = calculator.getTotal();

        // Assert
        assertThat(total).isEqualTo(12.95);

    }

    @Test
    public void totalAmountIsZero() {
        // Arrange
        var calculator = new BillCalculator();

        // Act
        float total = calculator.getTotal();

        // Assert
        assertThat(total).isZero();
    }
}