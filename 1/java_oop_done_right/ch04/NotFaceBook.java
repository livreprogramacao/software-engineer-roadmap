public class NotFaceBook {

    private final UserRepository users;
    private final Display display;

    public NotFaceBook( UserRepository users, Display display ) {

        this.users = users;
        this.display = display;

    }

    public void showProfile() {

        String userId = "almellor_19019";
        User u = users.findUserById( userId );
        u.displayProfile( display );

    }

}