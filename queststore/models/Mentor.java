package queststore.models;

public class Mentor extends User {
    private Class class_;

    public Mentor(String name, String login, String password, String email, Class class_) {
        super(name, login, password, email);
        this.class_ = class_;
    }

    public Mentor(String name, String login, String password, String email,Class class_, Integer id) {
        super(name, login, password, email, id);
        this.class_ = class_;
    }
}
