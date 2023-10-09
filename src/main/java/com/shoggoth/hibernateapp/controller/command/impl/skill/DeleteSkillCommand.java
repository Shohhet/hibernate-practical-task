package com.shoggoth.hibernateapp.controller.command.impl.skill;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.SkillService;
import com.shoggoth.hibernateapp.view.UserInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteSkillCommand implements Command {
    private final UserInterface ui;
    private final SkillService service;
    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SKILL_ID_MSG);
        String stringId = ui.readFromConsole();
        try {
            var id = Long.parseLong(stringId);
            if (service.delete(id)) {
                ui.writeToConsole(String.format(CommandUtils.SKILL_DELETED_MSG, id));
            } else {
                ui.writeToConsole(String.format(CommandUtils.SKILL_DOES_NOT_EXIST_MSG, id));
            }
        } catch (NumberFormatException e) {
            ui.writeToConsole(String.format(CommandUtils.WRONG_ID_FORMAT_MSG, stringId));
        }

    }
}
