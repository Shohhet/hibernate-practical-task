package com.shoggoth.hibernateapp.controller.command.impl.skill;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.SkillService;
import com.shoggoth.hibernateapp.servise.impl.SkillServiceImpl;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;
import com.shoggoth.hibernateapp.view.UserInterface;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateSkillCommand implements Command {

    private final UserInterface ui;
    private final SkillService service;

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SKILL_ID_MSG);
        String stringId = ui.readFromConsole();
        ui.writeToConsole(CommandUtils.ENTER_SKILL_NAME_MSG);
        String name = ui.readFromConsole();
        try {
            var id = Long.parseLong(stringId);
            if (service.update(new SkillDto(id, name))) {
                ui.writeToConsole(String.format(CommandUtils.SKILL_UPDATED_MSG, id));
            } else {
                ui.writeToConsole(String.format(CommandUtils.SKILL_DOES_NOT_EXIST_MSG, stringId));
            }
        } catch (NumberFormatException e) {
            ui.writeToConsole(String.format(CommandUtils.WRONG_ID_FORMAT_MSG, stringId));
        } catch (ConstraintViolationException ce) {
            ui.writeToConsole(ce.getMessage());
        }
    }
}
