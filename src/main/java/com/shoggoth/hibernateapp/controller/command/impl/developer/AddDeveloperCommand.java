package com.shoggoth.hibernateapp.controller.command.impl.developer;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.impl.DeveloperServiceImpl;
import com.shoggoth.hibernateapp.servise.dto.DeveloperDto;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;
import com.shoggoth.hibernateapp.view.UserInterface;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AddDeveloperCommand implements Command {

    private final UserInterface ui;
    private final DeveloperServiceImpl service;

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_FIRSTNAME_MSG);
        String firstname = ui.readFromConsole();
        ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_LASTNAME_MSG);
        String lastName = ui.readFromConsole();
        String skillName;
        List<SkillDto> skills = new ArrayList<>();
        do {
            ui.writeToConsole(CommandUtils.ENTER_SKILL_NAME_MSG);
            skillName = ui.readFromConsole();
            if (!skillName.isEmpty() && !skillName.isBlank()) {
                skills.add(new SkillDto(null, skillName));
            }
        } while (!skillName.isEmpty() || !skillName.isBlank());
        ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_NAME_MSG);
        String specialtyName = ui.readFromConsole();
        SpecialtyDto specialty;
        if (specialtyName.isEmpty() || specialtyName.isBlank()) {
            specialty = null;
        } else {
            specialty = new SpecialtyDto(null, specialtyName);
        }
        var developer = new DeveloperDto(null, firstname, lastName, skills, specialty);
        try {
            service.add(developer).ifPresentOrElse(
                    (id) -> ui.writeToConsole(String.format(CommandUtils.DEVELOPER_ADDED_MSG, developer.firstName(), developer.lastName(), id)),
                    () -> ui.writeToConsole(String.format(CommandUtils.DEVELOPER_ALREADY_EXIST, firstname, lastName))
            );
        } catch (ConstraintViolationException e) {
            ui.writeToConsole(e.getMessage());
        }
    }
}

