package com.shoggoth.hibernateapp.servise.mapper;

import com.shoggoth.hibernateapp.model.entity.DeveloperEntity;
import com.shoggoth.hibernateapp.servise.dto.DeveloperDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeveloperToDtoMapper implements Mapper<DeveloperEntity, DeveloperDto> {
    private final SkillToDtoMapper skillToDtoMapper;
    private final SpecialtyToDtoMapper specialtyToDtoMapper;
    @Override
    public DeveloperDto mapFrom(DeveloperEntity entity) {
        return new DeveloperDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getSkills().stream()
                        .map(skillToDtoMapper::mapFrom)
                        .toList(),
                specialtyToDtoMapper.mapFrom(entity.getSpecialty())
        );
    }
}
