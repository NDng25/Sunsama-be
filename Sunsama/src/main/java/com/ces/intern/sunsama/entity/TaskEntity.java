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
//@AttributeOverride(name = "id", column =  @Column(name="task_id"))
//@SequenceGenerator(name = "generator", sequenceName = "task_id_seq", schema = "public", allocationSize = 1)

public class TaskEntity  {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "Parent_id")
    private int parentId;
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


    @ManyToOne(cascade =CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "task_hastag",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "hastag_id")
    )
    private Collection<HashtagEntity> hastags;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TaskEntity that = (TaskEntity) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
