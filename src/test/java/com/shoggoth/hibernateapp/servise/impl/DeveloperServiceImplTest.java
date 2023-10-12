package com.shoggoth.hibernateapp.servise.impl;

import com.shoggoth.hibernateapp.model.entity.DeveloperEntity;
import com.shoggoth.hibernateapp.model.entity.SkillEntity;
import com.shoggoth.hibernateapp.model.entity.SpecialtyEntity;
import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.model.repository.impl.DeveloperRepositoryImpl;
import com.shoggoth.hibernateapp.model.repository.impl.SkillRepositoryImpl;
import com.shoggoth.hibernateapp.model.repository.impl.SpecialtyRepositoryImpl;
import com.shoggoth.hibernateapp.servise.dto.DeveloperDto;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;
import com.shoggoth.hibernateapp.servise.mapper.*;
import com.shoggoth.hibernateapp.servise.utils.ValidationUtils;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperServiceImplTest {
    static final String validFirstName = "Ivan";
    static final String validLastName = "Ivanov";
    static final String notValidFirstName = "12Ivan";
    static final String notValidLastName = "12Ivanov";
    static final String specialtyName = "backend";
    static final String skillName = "Java";
    static final Long id = 1L;

    SpecialtyRepositoryImpl specialtyRepository;
    SkillRepositoryImpl skillRepository;
    DeveloperRepositoryImpl developerRepository;
    DeveloperServiceImpl developerService;
    SkillEntity skill;
    List<SkillEntity> skills;
    List<SkillDto> skillDtos;
    SpecialtyEntity specialty;
    SpecialtyDto specialtyDto;
    DeveloperEntity developer;
    DeveloperDto developerDto;

    @BeforeEach
    public void init() {
        specialtyRepository = Mockito.mock(SpecialtyRepositoryImpl.class);
        skillRepository = Mockito.mock(SkillRepositoryImpl.class);
        developerRepository = Mockito.mock(DeveloperRepositoryImpl.class);
        var dtoToSpecialtyMapper = new DtoToSpecialtyMapper();
        var dtoToSkillMapper = new DtoToSkillMapper();
        var skillToDtoMapper = new SkillToDtoMapper();
        var specialtyToDtoMapper = new SpecialtyToDtoMapper();
        var developerToDtoMapper = new DeveloperToDtoMapper(skillToDtoMapper, specialtyToDtoMapper);
        var dtoToDeveloperMapper = new DtoToDeveloperMapper(dtoToSkillMapper, dtoToSpecialtyMapper);
        developerService = new DeveloperServiceImpl(
                developerRepository,
                skillRepository,
                specialtyRepository,
                dtoToSpecialtyMapper,
                dtoToSkillMapper,
                developerToDtoMapper,
                dtoToDeveloperMapper);

        skill = SkillEntity.builder()
                .id(id)
                .name(skillName)
                .status(Status.ACTIVE)
                .build();
        skills = new ArrayList<>();
        skills.add(skill);
        var skillDto = new SkillDto(id, skillName);
        skillDtos = List.of(skillDto);
        specialty = SpecialtyEntity.builder()
                .id(id)
                .name(specialtyName)
                .status(Status.ACTIVE)
                .build();
        specialtyDto = new SpecialtyDto(id, specialtyName);
        developer = DeveloperEntity.builder()
                .id(id)
                .firstName(validFirstName)
                .lastName(validLastName)
                .skills(skills)
                .specialty(specialty)
                .status(Status.ACTIVE)
                .build();
        developerDto = new DeveloperDto(id, validFirstName, validLastName, skillDtos, specialtyDto);

    }

    @Test
    public void whenAddingDeveloperDoesNotExistThenReturnOptionalOfDeveloperId() throws Exception {
        Mockito.when(developerRepository.getByName(validFirstName, validLastName)).thenReturn(Optional.empty());
        Mockito.when(developerRepository.add(Mockito.any(DeveloperEntity.class))).thenReturn(developer);
        Mockito.when(specialtyRepository.getByName(specialtyName)).thenReturn(Optional.of(specialty));
        Mockito.when(skillRepository.getByName(skillName)).thenReturn(Optional.of(skill));
        assertEquals(Optional.of(id), developerService.add(developerDto));
    }

    @Test
    public void whenAddingDeveloperAlreadyExistThenReturnOptionalOfEmpty() {
        Mockito.when(developerRepository.getByName(validFirstName, validLastName)).thenReturn(Optional.of(developer));
        assertEquals(Optional.empty(), developerService.add(developerDto));
    }

    @Test
    public void whenAddingDeveloperFirstNameNotValidThenThrowException() {
        var notValidDeveloperDto = new DeveloperDto(id, notValidFirstName, validLastName, skillDtos, specialtyDto);
        assertThrows(
                ConstraintViolationException.class,
                () -> developerService.add(notValidDeveloperDto),
                ValidationUtils.WRONG_FIRST_NAME_FORMAT);
    }

    @Test
    public void whenAddingDeveloperLastNameNotValidThenThrowException() {
        var notValidDeveloperDto = new DeveloperDto(id, validFirstName, notValidLastName, skillDtos, specialtyDto);
        assertThrows(
                ConstraintViolationException.class,
                () -> developerService.add(notValidDeveloperDto),
                ValidationUtils.WRONG_LAST_NAME_FORMAT);
    }

    @Test
    public void whenUpdatingDeveloperExistThenReturnTrue() {
        Mockito.when(developerRepository.get(id)).thenReturn(Optional.of(developer));
        Mockito.when(specialtyRepository.getByName(specialtyName)).thenReturn(Optional.of(specialty));
        Mockito.when(skillRepository.getByName(skillName)).thenReturn(Optional.of(skill));
        assertTrue(developerService.update(developerDto));
    }

    @Test
    public void whenUpdatingDeveloperDoesNotExistThenReturnFalse() {
        Mockito.when(developerRepository.get(id)).thenReturn(Optional.empty());
        assertFalse(developerService.update(developerDto));
    }

    @Test
    public void whenUpdatingDeveloperFirstNameNotValidThenThrowException() {
        var notValidDeveloperDto = new DeveloperDto(id, notValidFirstName, validLastName, skillDtos, specialtyDto);
        assertThrows(
                ConstraintViolationException.class,
                () -> developerService.update(notValidDeveloperDto),
                ValidationUtils.WRONG_FIRST_NAME_FORMAT);
    }
    @Test
    public void whenUpdatingDeveloperLastNameNotValidThenThrowException() {
        var notValidDeveloperDto = new DeveloperDto(id, validFirstName, notValidLastName, skillDtos, specialtyDto);
        assertThrows(
                ConstraintViolationException.class,
                () -> developerService.update(notValidDeveloperDto),
                ValidationUtils.WRONG_LAST_NAME_FORMAT);
    }

    @Test
    public void whenDeletingDeveloperExistThenReturnTrue() {
        Mockito.when(developerRepository.get(id)).thenReturn(Optional.of(developer));
        assertTrue(developerService.delete(id));
    }

    @Test
    public void whenDeletingDeveloperDoesNotExistThenReturnFalse() {
        Mockito.when(developerRepository.get(id)).thenReturn(Optional.empty());;
        assertFalse(developerService.delete(id));
    }

    @Test
    public void whenGettingDeveloperExistThenReturnOptionalOfDeveloper() {
        Mockito.when(developerRepository.get(id)).thenReturn(Optional.of(developer));
        assertEquals(Optional.of(developerDto), developerService.getById(id));
    }

    @Test
    public void whenGettingDeveloperDoesNotExistThenReturnOptionalOfEmpty() {
        Mockito.when(developerRepository.get(id)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), developerService.getById(id));
    }
}


