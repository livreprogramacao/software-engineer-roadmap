
public class ConsoleOutput implements Output {

    @Override
    public void toDisplay( final String toDisplay) {
        System.out.println( toDisplay );
    }

}