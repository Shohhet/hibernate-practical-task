package com.shoggoth.hibernateapp.servise.mapper;

import com.shoggoth.hibernateapp.model.entity.SkillEntity;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;

public class SkillToDtoMapper implements Mapper<SkillEntity, SkillDto> {
    @Override
    public SkillDto mapFrom(SkillEntity entity) {
        return new SkillDto(entity.getId(), entity.getName());
    }
}
