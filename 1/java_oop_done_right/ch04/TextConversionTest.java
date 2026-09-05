public class TextConversionTest {
    //@Test
    public void displayUpperCasedInput() {
        // Arrange
        var in = new StubInput("abcde123");
        var out = new mockOutput();
        var tc = new TextConversionTest( in, out );

        // Act
        tc.showInputUpperCase();

        // Assert
        assertThat( out.getActual() ).isEqualTo( "ABCDE123" );

    }

}