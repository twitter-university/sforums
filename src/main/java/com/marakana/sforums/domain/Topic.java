
package com.marakana.sforums.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
}
