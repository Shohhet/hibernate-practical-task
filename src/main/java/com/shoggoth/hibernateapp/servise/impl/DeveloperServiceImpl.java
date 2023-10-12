package com.shoggoth.hibernateapp.servise.impl;

import com.shoggoth.hibernateapp.model.entity.DeveloperEntity;
import com.shoggoth.hibernateapp.model.entity.SkillEntity;
import com.shoggoth.hibernateapp.model.entity.SpecialtyEntity;
import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.model.repository.impl.DeveloperRepositoryImpl;
import com.shoggoth.hibernateapp.model.repository.impl.SkillRepositoryImpl;
import com.shoggoth.hibernateapp.model.repository.impl.SpecialtyRepositoryImpl;
import com.shoggoth.hibernateapp.servise.DeveloperService;
import com.shoggoth.hibernateapp.servise.dto.DeveloperDto;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;
import com.shoggoth.hibernateapp.servise.mapper.DeveloperToDtoMapper;
import com.shoggoth.hibernateapp.servise.mapper.DtoToDeveloperMapper;
import com.shoggoth.hibernateapp.servise.mapper.DtoToSkillMapper;
import com.shoggoth.hibernateapp.servise.mapper.DtoToSpecialtyMapper;
import com.shoggoth.hibernateapp.servise.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperRepositoryImpl developerRepository;
    private final SkillRepositoryImpl skillRepository;
    private final SpecialtyRepositoryImpl specialtyRepository;
    private final DtoToSpecialtyMapper dtoToSpecialtyMapper;
    private final DtoToSkillMapper dtoToSkillMapper;
    private final DeveloperToDtoMapper developerToDtoMapper;
    private final DtoToDeveloperMapper dtoToDeveloperMapper;

    @Override
    @Transactional
    public Optional<Long> add(DeveloperDto dto) {
        var validationResult = ValidationUtils.getValidator().validate(dto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
        DeveloperEntity developer;
        var maybeDeveloper = developerRepository.getByName(dto.firstName(), dto.lastName());
        if (maybeDeveloper.isPresent()) {
            developer = maybeDeveloper.get();
            if (developer.getStatus() == Status.DELETED) {
                developer.setStatus(Status.ACTIVE);
                developer.setSpecialty(getDeveloperSpecialty(dto.specialty()));
                developer.setSkills(dto.skills().stream().map(this::getDeveloperSkill).toList());
                developerRepository.update(developer);
                return Optional.of(developer.getId());
            } else {
                return Optional.empty();
            }
        } else {
            developer = DeveloperEntity.builder()
                    .firstName(dto.firstName())
                    .lastName(dto.lastName())
                    .skills(dto.skills().stream().map(this::getDeveloperSkill).toList())
                    .specialty(getDeveloperSpecialty(dto.specialty()))
                    .status(Status.ACTIVE)
                    .build();
            return Optional.of(developerRepository.add(developer).getId());
        }
    }

    @Override
    @Transactional
    public Optional<DeveloperDto> getById(Long id) {
        return developerRepository.get(id)
                .filter(e -> e.getStatus() == Status.ACTIVE)
                .map(e -> {
                    var skills = e.getSkills();
                    skills.removeIf(s -> s.getStatus() == Status.DELETED);
                    e.setSkills(skills);
                    if (e.getSpecialty() != null && e.getSpecialty().getStatus() == Status.DELETED) {
                        e.setSpecialty(null);
                    }
                    return e;
                })
                .map(developerToDtoMapper::mapFrom);
    }

    @Override
    @Transactional
    public List<DeveloperDto> getAll() {
        return developerRepository.getAll()
                .stream()
                .filter(e -> e.getStatus() == Status.ACTIVE)
                .peek(e -> {
                    var skills = e.getSkills();
                    skills.removeIf(s -> s.getStatus() == Status.DELETED);
                    e.setSkills(skills);
                    if (e.getSpecialty() != null && e.getSpecialty().getStatus() == Status.DELETED) {
                        e.setSpecialty(null);
                    }
                })
                .map(developerToDtoMapper::mapFrom)
                .toList();
    }

    @Override
    @Transactional
    public boolean update(DeveloperDto dto) {
        var validationResult = ValidationUtils.getValidator().validate(dto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
        var maybeDeveloper = developerRepository.get(dto.id());
        if (maybeDeveloper.isPresent() && maybeDeveloper.get().getStatus() != Status.DELETED) {
            var developer = dtoToDeveloperMapper.mapFrom(dto);
            developer.setSpecialty(getDeveloperSpecialty(dto.specialty()));
            developer.setSkills(dto.skills().stream().map(this::getDeveloperSkill).toList());
            developerRepository.update(developer);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        var maybeDeveloper = developerRepository.get(id);
        if (maybeDeveloper.isPresent()) {
            var developer = maybeDeveloper.get();
            developer.setStatus(Status.DELETED);
            developerRepository.update(developer);
            return true;
        }
        return false;
    }

    private SpecialtyEntity getDeveloperSpecialty(SpecialtyDto dto) {
        if (dto == null) return null;
        SpecialtyEntity specialty;
        var maybeSpecialty = specialtyRepository.getByName(dto.name());
        if (maybeSpecialty.isPresent()) {
            specialty = maybeSpecialty.get();
            if (specialty.getStatus() == Status.DELETED) {
                specialty.setStatus(Status.ACTIVE);
                specialtyRepository.update(specialty);
                return specialty;
            }
            return specialty;
        } else {
            return specialtyRepository.add(dtoToSpecialtyMapper.mapFrom(dto));
        }
    }

    private SkillEntity getDeveloperSkill(SkillDto dto) {
        SkillEntity skill;
        var maybeSkill = skillRepository.getByName(dto.name());
        if (maybeSkill.isPresent()) {
            skill = maybeSkill.get();
            if (skill.getStatus() == Status.DELETED) {
                skill.setStatus(Status.ACTIVE);
                skillRepository.update(skill);
                return skill;
            }
            return skill;
        } else {
            return skillRepository.add(dtoToSkillMapper.mapFrom(dto));
        }
    }
}
