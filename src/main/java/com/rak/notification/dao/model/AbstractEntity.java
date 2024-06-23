package com.rak.notification.dao.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_timestamp")
    private Date creationTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_timestamp")
    private Date modificationTimestamp;

    @PrePersist
    public void onCreate() {
        creationTimestamp = new Date();
        modificationTimestamp = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        modificationTimestamp = new Date();
    }

}
