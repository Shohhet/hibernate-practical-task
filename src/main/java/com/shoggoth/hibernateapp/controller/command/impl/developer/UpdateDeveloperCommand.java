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
public class UpdateDeveloperCommand implements Command {
    private final UserInterface ui;
    private final DeveloperServiceImpl service;

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_ID_MSG);
        String stringId = ui.readFromConsole();
        try {
            var id = Long.parseLong(stringId);
            ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_FIRSTNAME_MSG);
            String firstname = ui.readFromConsole();
            ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_LASTNAME_MSG);
            String lastName = ui.readFromConsole();
            String skillName;
            List<SkillDto> skillDtos = new ArrayList<>();
            do {
                ui.writeToConsole(CommandUtils.ENTER_SKILL_NAME_MSG);
                skillName = ui.readFromConsole();
                if (!skillName.isEmpty() && !skillName.isBlank()) {
                    skillDtos.add(new SkillDto(null, skillName));
                }
            } while (!skillName.isEmpty() || !skillName.isBlank());
            ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_NAME_MSG);
            String specialtyName = ui.readFromConsole();
            SpecialtyDto specialtyDto = null;
            if (!specialtyName.isEmpty() && !specialtyName.isBlank()) {
                specialtyDto = new SpecialtyDto(null, specialtyName);
            }
            if (service.update(new DeveloperDto(id, firstname, lastName, skillDtos, specialtyDto))) {
                ui.writeToConsole(String.format(CommandUtils.DEVELOPER_UPDATED_MSG, id));
            } else {
                ui.writeToConsole(String.format(CommandUtils.DEVELOPER_DOES_NOT_EXIST_MSG, stringId));
            }
        } catch (NumberFormatException e) {
            ui.writeToConsole(String.format(CommandUtils.WRONG_ID_FORMAT_MSG, stringId));
        } catch (ConstraintViolationException ce) {
            ui.writeToConsole(ce.getMessage());
        }

    }
}
