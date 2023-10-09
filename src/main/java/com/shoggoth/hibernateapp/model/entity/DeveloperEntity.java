package com.shoggoth.hibernateapp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {"skills", "specialty"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "developer", schema = "public")
public class DeveloperEntity extends BaseEntity<Long> {
    private String firstName;
    private String lastName;

    @Builder.Default
    @ManyToMany()
    @JoinTable(
            name = "developer_skill",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<SkillEntity> skills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private SpecialtyEntity specialty;
}


