public class UserGreetingApp {
    public static void main(String args[]) {
        User nanisca = new User("Nanisca");
        User fabio = new User("Fabio");

        nanisca.greet();
        fabio.greet();

        Users users = new Users();
        users.add( new User("Joana") );
        users.add( new User("Fabia") );
        users.greet();
    }
}