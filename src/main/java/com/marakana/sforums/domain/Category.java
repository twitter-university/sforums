
package com.marakana.sforums.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = "all-categories", query = "from Category order by name", hints = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
        })
})
public class Category extends IdentifiableEntity {

    private static final long serialVersionUID = 124961586053250629L;

    private String name;

    private String description;

    private List<Forum> forums = new ArrayList<Forum>();

    @Size(max = 64)
    @NotEmpty
    @NotNull
    @Column(length = 64, unique = true, nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("name")
    @LazyCollection(LazyCollectionOption.EXTRA)
    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
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
