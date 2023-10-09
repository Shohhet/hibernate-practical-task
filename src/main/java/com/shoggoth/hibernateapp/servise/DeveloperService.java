package com.shoggoth.hibernateapp.servise;

import com.shoggoth.hibernateapp.model.repository.impl.DeveloperRepositoryImpl;
import com.shoggoth.hibernateapp.model.repository.impl.SkillRepositoryImpl;
import com.shoggoth.hibernateapp.model.repository.impl.SpecialtyRepositoryImpl;
import com.shoggoth.hibernateapp.servise.dto.DeveloperDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DeveloperService implements GenericService<Long, DeveloperDto> {
    private final DeveloperRepositoryImpl developerRepository;
    private final SkillRepositoryImpl skillRepository;
    private final SpecialtyRepositoryImpl specialtyRepository;

    @Override
    public Optional<Long> add(DeveloperDto dto) {

        return Optional.empty();
    }

    @Override
    public Optional<DeveloperDto> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<DeveloperDto> getAll() {
        return null;
    }

    @Override
    public boolean update(DeveloperDto dto) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
