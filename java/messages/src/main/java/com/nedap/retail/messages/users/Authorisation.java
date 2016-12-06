package com.nedap.retail.messages.users;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Authorisation implements Serializable {

    private static final long serialVersionUID = -961930253964396609L;

    public String role;
    public DateTime expiresAt;

    public Authorisation() {
    }

    public Authorisation(final String role) {
        this.role = role;
    }

    public Authorisation(final Role role) {
        this.role = role.name();
    }
}
