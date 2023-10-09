package com.shoggoth.hibernateapp.servise.mapper;

import com.shoggoth.hibernateapp.model.entity.SpecialtyEntity;
import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;

public class DtoToSpecialtyMapper implements Mapper<SpecialtyDto, SpecialtyEntity> {

    @Override
    public SpecialtyEntity mapFrom(SpecialtyDto dto) {
        return SpecialtyEntity.builder()
                .id(dto.id())
                .name(dto.name())
                .status(Status.ACTIVE)
                .build();
    }
}
