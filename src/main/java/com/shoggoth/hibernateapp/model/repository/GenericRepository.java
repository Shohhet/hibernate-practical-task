package com.shoggoth.hibernateapp.model.repository;

import com.shoggoth.hibernateapp.model.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericRepository <K extends Serializable, E  extends BaseEntity<K>> {
    E add(E entity);
    Optional<E> get(K id);
    List<E> getAll();
    void update(E entity);

}
