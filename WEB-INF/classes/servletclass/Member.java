package servletclass;

public class Member {
    private String email;
    private String name;
    private String password;

    public Member() {
    }

    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }
    
    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }
}