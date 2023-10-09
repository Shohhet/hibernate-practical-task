package com.shoggoth.hibernateapp.model.repository.impl;

import com.shoggoth.hibernateapp.model.entity.DeveloperEntity;
import org.hibernate.Session;

import java.util.Optional;

public class DeveloperRepositoryImpl extends GenericRepositoryImpl<Long, DeveloperEntity>{
    public DeveloperRepositoryImpl(Class<DeveloperEntity> clazz, Session session) {
        super(clazz, session);
    }

    public Optional<DeveloperEntity> getByName(String firstName, String lastName) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(DeveloperEntity.class);
        var root = criteria.from(DeveloperEntity.class);

        var firstNameEquals = criteriaBuilder.like(root.get("firstName"), firstName);
        var lastNameEquals = criteriaBuilder.like(root.get("lastName"), lastName);

        criteria.select(root).where(criteriaBuilder.and(firstNameEquals, lastNameEquals));
        return session.createQuery(criteria)
                .getResultStream()
                .findFirst();
    }
}
