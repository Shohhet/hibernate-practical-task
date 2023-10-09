package com.shoggoth.hibernateapp.controller.command.impl.skill;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.controller.command.impl.specialty.UpdateSpecialtyCommand;
import com.shoggoth.hibernateapp.servise.SkillService;
import com.shoggoth.hibernateapp.view.UserInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSkillCommand implements Command {

    private final UserInterface ui;
    private final SkillService service;

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SKILL_ID_MSG);
        var stringId = ui.readFromConsole();
        try {
            var id = Long.parseLong(stringId);
            service.getById(id).ifPresentOrElse(
                    skill -> ui.writeToConsole(skill.toString()),
                    () -> ui.writeToConsole(String.format(CommandUtils.SKILL_DOES_NOT_EXIST_MSG, id))
            );
        } catch (NumberFormatException e) {
            ui.writeToConsole(String.format(CommandUtils.WRONG_ID_FORMAT_MSG, stringId));
        }
    }
}
