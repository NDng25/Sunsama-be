package com.ces.intern.sunsama.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import static com.fasterxml.jackson.databind.util.ClassUtil.name;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task", schema ="public")

public class TaskEntity  {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "Parent_id")
    private long parentId;
    @Column(name = "title")
    private String title;
    @Column(name = "_describe")
    private String describe;
    @Column(name = "date")
    private Date date;
    @Column(name = "due_date")
    private Date dueDate;
    @Column(name = "is_status")
    private boolean isStatus;


    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    }, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    }, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "task_hashtag",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Collection<HashtagEntity> hashtags;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TaskEntity that = (TaskEntity) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
