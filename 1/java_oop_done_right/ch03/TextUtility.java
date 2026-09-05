public class TextUtility {

    void main() {

        Input input = new DatabaseInput();
        Output output = new ConsoleOutput();

        new TextConversion( input, output ).showInputInUpperCase();

    }

}