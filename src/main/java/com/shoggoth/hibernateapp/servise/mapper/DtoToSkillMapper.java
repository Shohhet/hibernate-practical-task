package com.shoggoth.hibernateapp.servise.mapper;

import com.shoggoth.hibernateapp.model.entity.SkillEntity;
import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;

public class DtoToSkillMapper implements Mapper <SkillDto, SkillEntity>{
    @Override
    public SkillEntity mapFrom(SkillDto dto) {
        return SkillEntity.builder()
                .id(dto.id())
                .name(dto.name())
                .status(Status.ACTIVE)
                .build();
    }
}
