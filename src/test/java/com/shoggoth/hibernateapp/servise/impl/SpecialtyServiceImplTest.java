package com.shoggoth.hibernateapp.servise.impl;

import com.shoggoth.hibernateapp.model.entity.SpecialtyEntity;
import com.shoggoth.hibernateapp.model.entity.Status;
import com.shoggoth.hibernateapp.model.repository.impl.SpecialtyRepositoryImpl;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;
import com.shoggoth.hibernateapp.servise.mapper.DtoToSpecialtyMapper;
import com.shoggoth.hibernateapp.servise.mapper.SpecialtyToDtoMapper;
import com.shoggoth.hibernateapp.servise.utils.ValidationUtils;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceImplTest {
    static final String SpecialtyName = "backend";
    static final String notValidSpecialtyName = "2424eszfsz";
    static final Long id = 1L;
    SpecialtyRepositoryImpl SpecialtyRepository;
    SpecialtyServiceImpl SpecialtyService;
    SpecialtyEntity Specialty;
    SpecialtyDto SpecialtyDto;

    @BeforeEach
    public void init() {
        SpecialtyRepository = Mockito.mock(SpecialtyRepositoryImpl.class);
        SpecialtyService = new SpecialtyServiceImpl(SpecialtyRepository, new DtoToSpecialtyMapper(), new SpecialtyToDtoMapper());
        Specialty = SpecialtyEntity.builder()
                .id(id)
                .name(SpecialtyName)
                .status(Status.ACTIVE)
                .build();
        SpecialtyDto = new SpecialtyDto(id, SpecialtyName);
    }

    @Test
    public void whenAddingSpecialtyDoesNotExistThenReturnOptionalOfSpecialtyId() {
        Mockito.when(SpecialtyRepository.add(Mockito.any(SpecialtyEntity.class))).thenReturn(Specialty);
        Mockito.when(SpecialtyRepository.getByName(SpecialtyName)).thenReturn(Optional.empty());
        assertEquals(Optional.of(id), SpecialtyService.add(SpecialtyDto));
    }

    @Test
    public void whenAddingSpecialtyAlreadyExistThenReturnOptionalOfEmpty() {
        Mockito.when(SpecialtyRepository.getByName(SpecialtyName)).thenReturn(Optional.of(Specialty));
        assertEquals(Optional.empty(), SpecialtyService.add(SpecialtyDto));
    }

    @Test
    public void whenAddingSpecialtyNameNotValidThenThrowException() {
        var notValidSpecialtyDto = new SpecialtyDto(id, notValidSpecialtyName);
        assertThrows(
                ConstraintViolationException.class,
                () -> SpecialtyService.add(notValidSpecialtyDto),
                ValidationUtils.WRONG_SPECIALTY_NAME_FORMAT);
    }

    @Test
    public void whenUpdatingSpecialtyExistThenReturnTrue() {
        Mockito.when(SpecialtyRepository.get(id)).thenReturn(Optional.of(Specialty));
        assertTrue(SpecialtyService.update(SpecialtyDto));
    }

    @Test
    public void whenUpdatingSpecialtyDoesNotExistThenReturnFalse() {
        Mockito.when(SpecialtyRepository.get(id)).thenReturn(Optional.empty());
        assertFalse(SpecialtyService.update(SpecialtyDto));
    }

    @Test
    public void whenUpdatingSpecialtyNameNotValidThenThrowServiceException() {
        var notValidSpecialtyDto = new SpecialtyDto(id, notValidSpecialtyName);
        assertThrows(
                ConstraintViolationException.class,
                () -> SpecialtyService.update(notValidSpecialtyDto),
                ValidationUtils.WRONG_SPECIALTY_NAME_FORMAT);
    }

    @Test
    public void whenDeletingSpecialtyExistThenReturnTrue() {
        Mockito.when(SpecialtyRepository.get(id)).thenReturn(Optional.of(Specialty));
        assertTrue(SpecialtyService.delete(id));
    }

    @Test
    public void whenDeletingSpecialtyDoesNotExistThenReturnFalse() {
        Mockito.when(SpecialtyRepository.get(id)).thenReturn(Optional.empty());
        assertFalse(SpecialtyService.delete(id));
    }

    @Test
    public void whenGettingSpecialtyExistThenReturnOptionalOfSpecialty() {
        Mockito.when(SpecialtyRepository.get(id)).thenReturn(Optional.of(Specialty));
        assertEquals(Optional.of(SpecialtyDto), SpecialtyService.getById(id));
    }

    @Test
    public void whenGettingSpecialtyDoesNotExistThenReturnOptionalOfEmpty() {
        Mockito.when(SpecialtyRepository.get(id)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), SpecialtyService.getById(id));
    }


}