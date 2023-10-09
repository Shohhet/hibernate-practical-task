package com.shoggoth.hibernateapp.servise;

import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.model.repository.impl.SpecialtyRepositoryImpl;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;
import com.shoggoth.hibernateapp.servise.mapper.DtoToSpecialtyMapper;
import com.shoggoth.hibernateapp.servise.mapper.SpecialtyToDtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SpecialtyService implements GenericService<Long, SpecialtyDto> {
    private final SpecialtyRepositoryImpl specialtyRepository;
    private final DtoToSpecialtyMapper dtoToSpecialtyMapper;
    private final SpecialtyToDtoMapper specialtyToDtoMapper;

    @Override
    @Transactional
    public Optional<Long> add(SpecialtyDto dto) {
        var maybeSpecialty = specialtyRepository.getByName(dto.name());
        if (maybeSpecialty.isPresent()) {
            var specialty = maybeSpecialty.get();
            if (specialty.getStatus() == Status.DELETED) {
                specialty.setStatus(Status.ACTIVE);
                specialtyRepository.update(specialty);
                return Optional.of(specialty.getId());
            } else {
                return Optional.empty();
            }
        } else {
            var specialty = specialtyRepository.add(dtoToSpecialtyMapper.mapFrom(dto));
            return Optional.of(specialty.getId());
        }
    }

    @Override
    @Transactional
    public Optional<SpecialtyDto> getById(Long id) {
        return specialtyRepository.get(id)
                .filter(e -> e.getStatus() == Status.ACTIVE)
                .map(specialtyToDtoMapper::mapFrom);
    }

    @Override
    @Transactional
    public List<SpecialtyDto> getAll() {
        return specialtyRepository.getAll()
                .stream()
                .filter(e -> e.getStatus() == Status.ACTIVE)
                .map(specialtyToDtoMapper::mapFrom)
                .toList();
    }

    @Override
    @Transactional
    public boolean update(SpecialtyDto dto) {
        var maybeSpecialty = specialtyRepository.get(dto.id());
        if (maybeSpecialty.isPresent()) {
            specialtyRepository.update(dtoToSpecialtyMapper.mapFrom(dto));
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        var maybeSpecialty = specialtyRepository.get(id);
        if (maybeSpecialty.isPresent()) {
            var specialty = maybeSpecialty.get();
            specialty.setStatus(Status.DELETED);
            specialtyRepository.update(specialty);
            return true;
        }
        return false;
    }
}
