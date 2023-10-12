package com.shoggoth.hibernateapp.servise;

import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;

import java.util.List;
import java.util.Optional;

public interface SpecialtyService extends GenericService<Long, SpecialtyDto> {
    @Override
    Optional<Long> add(SpecialtyDto dto);

    @Override
    Optional<SpecialtyDto> getById(Long id);

    @Override
    List<SpecialtyDto> getAll();

    @Override
    boolean update(SpecialtyDto dto);

    @Override
    boolean delete(Long id);
}
