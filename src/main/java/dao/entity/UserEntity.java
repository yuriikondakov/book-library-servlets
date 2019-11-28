package dao.entity;

import domain.Role;

public class UserEntity {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                '}';
    }

    public UserEntity(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.role = builder.role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public static class Builder {
        private Integer id;
        private String name;
        private String email;
        private String password;
        private String phoneNumber;
        private Role role;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}
