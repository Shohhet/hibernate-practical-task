package com.shoggoth.hibernateapp.servise.dto;

import com.shoggoth.hibernateapp.servise.utils.ValidationUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SkillDto(
        Long id,
        @NotNull
        @NotBlank
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._-]{1,128}$", message = ValidationUtils.WRONG_SKILL_NAME_FORMAT)
        String name) {
}
