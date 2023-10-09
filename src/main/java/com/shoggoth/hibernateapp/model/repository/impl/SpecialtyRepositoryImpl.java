package com.shoggoth.hibernateapp.model.repository.impl;

import com.shoggoth.hibernateapp.model.entity.SpecialtyEntity;
import org.hibernate.Session;

import java.util.Optional;

public class SpecialtyRepositoryImpl extends GenericRepositoryImpl<Long, SpecialtyEntity> {

    public SpecialtyRepositoryImpl(Class<SpecialtyEntity> clazz, Session session) {
        super(clazz, session);
    }

    public Optional<SpecialtyEntity> getByName(String name) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(SpecialtyEntity.class);
        var root = criteria.from(SpecialtyEntity.class);
        criteria.select(root).where(criteriaBuilder.like(root.get("name"), name));
        return session.createQuery(criteria)
                .getResultStream()
                .findFirst();
    }
}
