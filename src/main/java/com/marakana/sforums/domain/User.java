
package com.marakana.sforums.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends IdentifiableEntity {

    private static final long serialVersionUID = 2993569267760500809L;

    private String firstName;

    private String lastName;

    private String organization;

    private String title;

    private String email;

    private String passwordDigest;

    private Date created;

    private boolean admin = false;

    private boolean enabled = true;

    @Size(max = 64)
    @Column(length = 64)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Size(max = 20)
    @NotEmpty
    @NotNull
    @Column(length = 20, nullable = false)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Size(max = 20)
    @NotEmpty
    @NotNull
    @Column(length = 20, nullable = false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Email
    @Size(max = 64)
    @NotEmpty
    @NotNull
    @Column(length = 64, unique = true, nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(length = 80, nullable = false)
    public String getPasswordDigest() {
        return this.passwordDigest;
    }

    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
    }

    @Size(max = 64)
    @Column(length = 64)
    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Past
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(nullable = false, columnDefinition = "BIT")
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Column(nullable = false, columnDefinition = "BIT")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (this.getEmail() == null) {
            return false;
        } else if (o instanceof User) {
            User that = (User) o;
            return this.getEmail().equals(that.getEmail());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getEmail() == null ? System.identityHashCode(this) : 17 * this.getEmail()
                .hashCode();
    }

    @Transient
    public String getName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
