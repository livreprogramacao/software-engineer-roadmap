
public class TextConversion {

    Input input;
    Output output;

    public TextConversion(final Input input, final Output output) {

        this.input = input;
        this.output = output;

    }


    public void showInputInUpperCase() {

        // Fetch Keyboard Input
        String inputText = input.fetch();

        // Covert
        String upperCaseText = inputText.toUpperCase();

        // Display
        output.toDisplay( upperCaseText );
    }

}