package com.gmail.roadtojob2019.task.entity;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private String name;
    private String surname;
    private String email;
    private Set<String> phones = new HashSet<>();
    private Set<Role> roles = EnumSet.noneOf(Role.class);

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(phones, user.phones) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, phones, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phones +
                ", roles=" + roles +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final User user;

        public Builder() {
            user = new User();
        }

        public Builder withName(String name) {
            user.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            user.surname = surname;
            return this;
        }

        public Builder withEmail(String email) {
            user.email = email;
            return this;
        }

        public Builder withPhones(Set<String> phones) {
            user.phones = phones;
            return this;
        }

        public Builder withRoles(Set<Role> roles) {
            user.roles = roles;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
