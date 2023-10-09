package com.shoggoth.hibernateapp.model.repository.impl;

import com.shoggoth.hibernateapp.model.entity.SkillEntity;
import com.shoggoth.hibernateapp.model.entity.SpecialtyEntity;
import jakarta.transaction.Transactional;
import org.hibernate.Session;

import java.util.Optional;

public class SkillRepositoryImpl extends GenericRepositoryImpl<Long, SkillEntity>{
    public SkillRepositoryImpl(Class<SkillEntity> clazz, Session session) {
        super(clazz, session);
    }

    public Optional<SkillEntity> getByName(String name) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(SkillEntity.class);
        var root = criteria.from(SkillEntity.class);
        criteria.select(root).where(criteriaBuilder.like(root.get("name"), name));
        return session.createQuery(criteria)
                .getResultStream()
                .findFirst();
    }

    public void deleteSkillForDevelopers() {

    }
}
