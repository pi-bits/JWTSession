package com.session.jwt.model;

public class User {
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean isActivated() {
        return activated;
    }

    public static String getRoleAdmin() {
        return ROLE_ADMIN;
    }

    public static String getRoleUser() {
        return ROLE_USER;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String userName;
    private String password;
    private String email;
    private String role;
    private boolean activated;

    public final static String ROLE_ADMIN = "admin";
    public final static String ROLE_USER = "user";

public User()
{

}
    private User(UserBuilder userBuilder) {
        this.userName = userBuilder.userName;
        this.password = userBuilder.password;
        this.email = userBuilder.email;
        this.role = userBuilder.role;
        this.activated = userBuilder.activated;

    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String userName;
        private String password;
        private String email;
        private String role;
        private boolean activated;

        private UserBuilder() {

        }

        public UserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder passWord(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserBuilder isActivated(boolean authenticated) {
            this.activated = activated;
            return this;

        }

        public User build() {
            return new User(this);
        }

    }
}
