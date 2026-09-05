package com.github.livreprogramacao.restaurante.caixa.pointofsale;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BillTest {

    @Test
    public void totalStartAtZero() {
        // Arrange
        var bill = new Bill();

        // Act
        float total = bill.getTotal();

        // Assert
        assertThat(total).isZero();
    }

    @Test
    public void correctTotalForOneItem() {
        // Arrange
        var bill = new Bill();

        // Act
        bill.add( 12.95F);
        float total = bill.getTotal();

        // Assert
        assertThat(total).isEqualTo(12.95F);
    }

    @Test
    public void correctTotalForTwoItems() {

        // Arrange
        var bill = new Bill();

        // Act
        bill.add( 12.95F);
        bill.add( 2.05F);
        float total = bill.getTotal();

        // Assert
        assertThat(total).isEqualTo(12.95F + 2.05F);
    }
}


