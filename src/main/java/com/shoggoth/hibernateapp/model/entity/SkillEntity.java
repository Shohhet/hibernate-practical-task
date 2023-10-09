package com.shoggoth.hibernateapp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "skill", schema = "public")
public class SkillEntity extends BaseEntity<Long> {
    private String name;
}
