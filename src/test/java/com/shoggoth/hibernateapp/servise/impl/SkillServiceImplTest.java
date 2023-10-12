package com.shoggoth.hibernateapp.servise.impl;

import com.shoggoth.hibernateapp.model.entity.SkillEntity;
import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.model.repository.impl.SkillRepositoryImpl;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;
import com.shoggoth.hibernateapp.servise.mapper.DtoToSkillMapper;
import com.shoggoth.hibernateapp.servise.mapper.SkillToDtoMapper;
import com.shoggoth.hibernateapp.servise.utils.ValidationUtils;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SkillServiceImplTest {
    static final String skillName = "Java";
    static final String notValidSkillName = "2424eszfsz";
    static final Long id = 1L;
    SkillRepositoryImpl skillRepository;
    SkillServiceImpl skillService;
    SkillEntity skill;
    SkillDto skillDto;

    @BeforeEach
    public void init() {
        skillRepository = Mockito.mock(SkillRepositoryImpl.class);
        skillService = new SkillServiceImpl(skillRepository, new DtoToSkillMapper(), new SkillToDtoMapper());
        skill = SkillEntity.builder()
                .id(id)
                .name(skillName)
                .status(Status.ACTIVE)
                .build();
        skillDto = new SkillDto(id, skillName);
    }

    @Test
    public void whenAddingSkillDoesNotExistThenReturnOptionalOfSkillId() {
        Mockito.when(skillRepository.add(Mockito.any(SkillEntity.class))).thenReturn(skill);
        Mockito.when(skillRepository.getByName(skillName)).thenReturn(Optional.empty());
        assertEquals(Optional.of(id), skillService.add(skillDto));
    }

    @Test
    public void whenAddingSkillAlreadyExistThenReturnOptionalOfEmpty() {
        Mockito.when(skillRepository.getByName(skillName)).thenReturn(Optional.of(skill));
        assertEquals(Optional.empty(), skillService.add(skillDto));
    }

    @Test
    public void whenAddingSkillNameNotValidThenThrowException() {
        var notValidSkillDto = new SkillDto(id, notValidSkillName);
        assertThrows(
                ConstraintViolationException.class,
                () -> skillService.add(notValidSkillDto),
                ValidationUtils.WRONG_SKILL_NAME_FORMAT);
    }

    @Test
    public void whenUpdatingSkillExistThenReturnTrue() {
        Mockito.when(skillRepository.get(id)).thenReturn(Optional.of(skill));
        assertTrue(skillService.update(skillDto));
    }

    @Test
    public void whenUpdatingSkillDoesNotExistThenReturnFalse() {
        Mockito.when(skillRepository.get(id)).thenReturn(Optional.empty());
        assertFalse(skillService.update(skillDto));
    }

    @Test
    public void whenUpdatingSkillNameNotValidThenThrowServiceException() {
        var notValidSkillDto = new SkillDto(id, notValidSkillName);
        assertThrows(
                ConstraintViolationException.class,
                () -> skillService.update(notValidSkillDto),
                ValidationUtils.WRONG_SKILL_NAME_FORMAT);
    }

    @Test
    public void whenDeletingSkillExistThenReturnTrue() {
        Mockito.when(skillRepository.get(id)).thenReturn(Optional.of(skill));
        assertTrue(skillService.delete(id));
    }

    @Test
    public void whenDeletingSkillDoesNotExistThenReturnFalse() {
        Mockito.when(skillRepository.get(id)).thenReturn(Optional.empty());
        assertFalse(skillService.delete(id));
    }

    @Test
    public void whenGettingSkillExistThenReturnOptionalOfSkill() {
        Mockito.when(skillRepository.get(id)).thenReturn(Optional.of(skill));
        assertEquals(Optional.of(skillDto), skillService.getById(id));
    }

    @Test
    public void whenGettingSkillDoesNotExistThenReturnOptionalOfEmpty() {
        Mockito.when(skillRepository.get(id)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), skillService.getById(id));
    }

}