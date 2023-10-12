package com.shoggoth.hibernateapp.servise.impl;

import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.model.repository.impl.SkillRepositoryImpl;
import com.shoggoth.hibernateapp.servise.SkillService;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;
import com.shoggoth.hibernateapp.servise.mapper.DtoToSkillMapper;
import com.shoggoth.hibernateapp.servise.mapper.SkillToDtoMapper;
import com.shoggoth.hibernateapp.servise.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepositoryImpl skillRepository;
    private final DtoToSkillMapper dtoToSkillMapper;
    private final SkillToDtoMapper skillToDtoMapper;

    @Override
    @Transactional
    public Optional<Long> add(SkillDto dto) {
        var validationResult = ValidationUtils.getValidator().validate(dto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
        var maybeSkill = skillRepository.getByName(dto.name());
        if (maybeSkill.isPresent()) {
            var skill = maybeSkill.get();
            if (skill.getStatus() == Status.DELETED) {
                skill.setStatus(Status.ACTIVE);
                skillRepository.update(skill);
                return Optional.of(skill.getId());
            } else {
                return Optional.empty();
            }
        } else {
            var specialty = skillRepository.add(dtoToSkillMapper.mapFrom(dto));
            return Optional.of(specialty.getId());
        }

    }

    @Override
    @Transactional
    public Optional<SkillDto> getById(Long id) {
        return skillRepository.get(id)
                .filter(e -> e.getStatus() == Status.ACTIVE)
                .map(skillToDtoMapper::mapFrom);
    }

    @Override
    @Transactional
    public List<SkillDto> getAll() {
        return skillRepository.getAll()
                .stream()
                .filter(e -> e.getStatus() == Status.ACTIVE)
                .map(skillToDtoMapper::mapFrom)
                .toList();
    }

    @Override
    @Transactional
    public boolean update(SkillDto dto) {
        var validationResult = ValidationUtils.getValidator().validate(dto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
        var maybeSpecialty = skillRepository.get(dto.id());
        if (maybeSpecialty.isPresent()) {
            skillRepository.update(dtoToSkillMapper.mapFrom(dto));
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        var maybeSpecialty = skillRepository.get(id);
        if (maybeSpecialty.isPresent()) {
            var skill = maybeSpecialty.get();
            skill.setStatus(Status.DELETED);
            skillRepository.update(skill);
            return true;
        }
        return false;
    }
}
