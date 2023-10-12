package com.shoggoth.hibernateapp.servise.dto;


import com.shoggoth.hibernateapp.servise.utils.ValidationUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record DeveloperDto(
        Long id,
        @NotNull
        @NotBlank
        @NotEmpty
        @Pattern(regexp = "^[A-Z][a-z]{2,128}$", message = ValidationUtils.WRONG_FIRST_NAME_FORMAT)
        String firstName,
        @NotNull
        @NotBlank
        @NotEmpty
        @Pattern(regexp = "^[A-Z][a-z]{2,128}$", message = ValidationUtils.WRONG_LAST_NAME_FORMAT)
        String lastName,
        @Valid
        List<SkillDto> skills,
        @Valid
        SpecialtyDto specialty) {
}
