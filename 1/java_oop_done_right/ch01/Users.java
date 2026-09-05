import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Users {

    private List<User> users = new LinkedList<>();

    public void add(User u) {
        users.add(u);
    }

    public void greet() {
        users.forEach( User::greet );
    }

}