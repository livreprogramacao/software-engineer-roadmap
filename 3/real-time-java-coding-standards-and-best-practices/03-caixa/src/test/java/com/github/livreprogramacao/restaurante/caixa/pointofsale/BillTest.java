package com.github.livreprogramacao.restaurante.caixa.pointofsale;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BillCalculatorTest {

    @Test
    public void totalStartAtZero() {
        // Arrange
        var calculator = new BillCalculator();

        // Act
        float total = calculator.getTotal();

        // Assert
        assertThat(total).isZero();
    }

    @Test
    public void correctTotalForOneItem() {
        // Arrange
        var calculator = new BillCalculator();

        // Act
        calculator.add( 12.95F);
        float total = calculator.getTotal();

        // Assert
        assertThat(total).isEqualTo(12.95F);
    }
}


