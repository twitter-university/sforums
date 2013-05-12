
package com.marakana.sforums.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "topic", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
            "title", "forum_id"
    })
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = "all-topics", query = "from Topic order by created", hints = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
        }),
        @NamedQuery(name = "topic-by-forum-and-title", query = "from Topic where forum=:forum and title=:title")
})
public class Topic extends Post {

    private static final long serialVersionUID = -8501774350808842862L;

    private Forum forum;

    private String title;

    private List<Reply> replies = new ArrayList<Reply>();

    @NotNull
    @ManyToOne(optional = false)
    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    @Size(max = 128)
    @NotEmpty
    @NotNull
    @Column(nullable = false, length = 128)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("created")
    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return super.toString() + "/" + this.getTitle();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.forum == null) ? 0 : this.forum.hashCode());
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Topic))
            return false;
        Topic other = (Topic) obj;
        if (this.forum == null) {
            if (other.forum != null)
                return false;
        } else if (!this.forum.equals(other.forum))
            return false;
        if (this.title == null) {
            if (other.title != null)
                return false;
        } else if (!this.title.equals(other.title))
            return false;
        return true;
    }
}
