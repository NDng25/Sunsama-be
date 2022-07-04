package com.ces.intern.sunsama.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hashtag", schema = "public")

public class HashtagEntity {
    @Id
    @GeneratedValue
    private long id;

    public HashtagEntity(String name) {
        this.name = name;
    }

    @Column(name="hashtag_name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "hashtags" ,cascade =CascadeType.ALL,fetch =FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<TaskEntity> taskEntities=new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HashtagEntity that = (HashtagEntity) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}



