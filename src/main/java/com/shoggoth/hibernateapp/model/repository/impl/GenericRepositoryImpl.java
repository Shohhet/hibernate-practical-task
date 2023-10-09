package com.shoggoth.hibernateapp.model.repository.impl;

import com.shoggoth.hibernateapp.model.entity.BaseEntity;
import com.shoggoth.hibernateapp.model.repository.GenericRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class GenericRepositoryImpl<K extends Serializable, E extends BaseEntity<K>> implements GenericRepository<K, E> {
    protected final Class<E> clazz;
    protected final Session session;

    @Override
    public E add(E entity) {
        session.persist(entity);
        return entity;
    }

    @Override
    public Optional<E> get(K id) {
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public List<E> getAll() {
        var criteria = session.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public void update(E entity) {
        session.merge(entity);
    }


}
