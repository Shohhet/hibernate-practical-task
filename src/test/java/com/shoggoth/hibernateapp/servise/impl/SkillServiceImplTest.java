package com.shoggoth.hibernateapp.servise.impl;

import com.shoggoth.hibernateapp.model.entity.SkillEntity;
import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.model.repository.impl.SkillRepositoryImpl;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;
import com.shoggoth.hibernateapp.servise.mapper.DtoToSkillMapper;
import com.shoggoth.hibernateapp.servise.mapper.SkillToDtoMapper;
import org.hibernate.annotations.AttributeAccessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SkillServiceImplTest {
    static final String skillName = "Java";
    static final String notValidSkillName = "2424eszfsz";
    static final String stringId = "1";
    static final String notValidStringId = "1ef";
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
    public void whenAddingSkillDoesNotExistThenReturnOptionalOfSkill() {

        Mockito.when(skillRepository.add(Mockito.any(SkillEntity.class))).thenReturn(skill);
        Mockito.when(skillRepository.getByName(skillName)).thenReturn(Optional.empty());
        assertEquals(Optional.of(id), skillService.add(skillDto));


    }

}