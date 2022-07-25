package com.ces.intern.sunsama.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema ="public")
public class UserEntity  {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<TaskEntity> tasks = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserEntity that = (UserEntity) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
