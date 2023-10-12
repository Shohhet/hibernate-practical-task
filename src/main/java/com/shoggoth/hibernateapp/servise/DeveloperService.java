package com.shoggoth.hibernateapp.servise;

import com.shoggoth.hibernateapp.servise.dto.DeveloperDto;

import java.util.List;
import java.util.Optional;

public interface DeveloperService extends GenericService<Long, DeveloperDto> {
    @Override
    Optional<Long> add(DeveloperDto dto);

    @Override
    Optional<DeveloperDto> getById(Long id);

    @Override
    List<DeveloperDto> getAll();

    @Override
    boolean update(DeveloperDto dto);

    @Override
    boolean delete(Long id);
}
