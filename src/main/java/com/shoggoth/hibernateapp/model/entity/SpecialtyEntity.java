package com.shoggoth.hibernateapp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "specialty", schema = "public")
public class SpecialtyEntity extends BaseEntity<Long> {
    private String name;
}
