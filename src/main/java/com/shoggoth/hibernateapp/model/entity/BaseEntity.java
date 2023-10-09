package com.shoggoth.hibernateapp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class BaseEntity<K extends Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    K id;

    @Enumerated(value = EnumType.STRING)
    Status status;
}
