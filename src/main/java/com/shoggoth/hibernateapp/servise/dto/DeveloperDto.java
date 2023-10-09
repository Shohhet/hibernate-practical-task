package com.shoggoth.hibernateapp.servise.dto;


import java.util.List;

public record DeveloperDto(
        Long id,
        String firstName,
        String lastName,
        List<SkillDto> skills,
        SpecialtyDto specialty) {
}
