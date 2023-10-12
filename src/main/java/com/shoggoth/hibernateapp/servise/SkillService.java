package com.shoggoth.hibernateapp.servise;

import com.shoggoth.hibernateapp.servise.dto.SkillDto;

import java.util.List;
import java.util.Optional;

public interface SkillService extends GenericService<Long, SkillDto> {
    @Override
    Optional<Long> add(SkillDto dto);

    @Override
    Optional<SkillDto> getById(Long id);

    @Override
    List<SkillDto> getAll();

    @Override
    boolean update(SkillDto dto);

    @Override
    boolean delete(Long id);
}
