
package com.marakana.sforums.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

@MappedSuperclass
public abstract class TimestampedEntity extends IdentifiableEntity {

    private static final long serialVersionUID = -8049861637728316966L;

    private Date created;

    @Past
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public synchronized void markCreated() {
        if (this.getCreated() == null) {
            this.setCreated(new Date());
        }
    }
}
