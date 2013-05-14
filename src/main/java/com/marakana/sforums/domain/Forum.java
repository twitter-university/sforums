
package com.marakana.sforums.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "forum", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
            "name", "category_id"
    })
})
@NamedQueries({
        @NamedQuery(name = "all-forums", query = "from Forum order by category.name, name", hints = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
        }),
        @NamedQuery(name = "forum-by-category-and-name", query = "from Forum where category=:category and name=:name")
})
public class Forum extends IdentifiableEntity {

    private static final long serialVersionUID = 8322524543528154627L;

    private Category category;

    private String name;

    private String description;

    private List<Topic> topics = new ArrayList<Topic>();

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Size(max = 128)
    @NotEmpty
    @NotNull
    @Column(length = 128, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "forum", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("created")
    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Transient
    public String getCategoryAndName() {
        return this.getCategory() + "/" + this.getName();
    }

    @Override
    public String toString() {
        return this.getCategoryAndName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Forum))
            return false;
        Forum other = (Forum) obj;
        if (this.category == null) {
            if (other.category != null)
                return false;
        } else if (!this.category.equals(other.category))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        return true;
    }
}
