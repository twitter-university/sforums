
package com.marakana.sforums.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class Name implements Serializable {

    private static final long serialVersionUID = -6645706242888900930L;

    private String first;

    private String last;

    @Size(max = 20)
    @NotEmpty
    @NotNull
    @Column(name = "firstName", length = 20, nullable = false)
    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    @Size(max = 20)
    @NotEmpty
    @NotNull
    @Column(name = "lastName", length = 20, nullable = false)
    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return this.getFirst() + " " + this.getLast();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.first == null) ? 0 : this.first.hashCode());
        result = prime * result + ((this.last == null) ? 0 : this.last.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Name))
            return false;
        Name other = (Name) obj;
        if (this.first == null) {
            if (other.first != null)
                return false;
        } else if (!this.first.equals(other.first))
            return false;
        if (this.last == null) {
            if (other.last != null)
                return false;
        } else if (!this.last.equals(other.last))
            return false;
        return true;
    }
}
