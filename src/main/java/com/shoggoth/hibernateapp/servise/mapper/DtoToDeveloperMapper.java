package com.shoggoth.hibernateapp.servise.mapper;

import com.shoggoth.hibernateapp.model.entity.DeveloperEntity;
import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.servise.dto.DeveloperDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DtoToDeveloperMapper implements Mapper<DeveloperDto, DeveloperEntity> {
    private final DtoToSkillMapper dtoToSkillMapper;
    private final DtoToSpecialtyMapper dtoToSpecialtyMapper;

    @Override
    public DeveloperEntity mapFrom(DeveloperDto dto) {
        return DeveloperEntity.builder()
                .id(dto.id())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .status(Status.ACTIVE)
                .build();
    }
}
