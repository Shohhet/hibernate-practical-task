package com.shoggoth.hibernateapp.servise.mapper;

import com.shoggoth.hibernateapp.model.entity.SpecialtyEntity;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;

public class SpecialtyToDtoMapper implements Mapper<SpecialtyEntity, SpecialtyDto> {
    @Override
    public SpecialtyDto mapFrom(SpecialtyEntity entity) {
        return new SpecialtyDto(entity.getId(), entity.getName());
    }
}
