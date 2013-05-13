
package com.marakana.sforums.domain;

public class Category extends IdentifiableEntity {

    private static final long serialVersionUID = 124961586053250629L;

    private String name;

    private String description;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (this.getName() == null) {
            return false;
        } else if (o instanceof Category) {
            Category that = (Category) o;
            return this.getName().equals(that.getName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getName() == null ? System.identityHashCode(this) : 17 * this.getName()
                .hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
