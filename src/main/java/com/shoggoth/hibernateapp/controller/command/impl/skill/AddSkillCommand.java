package com.shoggoth.hibernateapp.controller.command.impl.skill;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.SkillService;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;
import com.shoggoth.hibernateapp.view.UserInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddSkillCommand implements Command {
    private final UserInterface ui;

    private final SkillService service;

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SKILL_NAME_MSG);
        String name = ui.readFromConsole();
        var skillDto = new SkillDto(null, name);
        service.add(skillDto).ifPresentOrElse(
                (id) -> ui.writeToConsole(String.format(CommandUtils.SKILL_ADDED_MSG, name, id)),
                () -> ui.writeToConsole(String.format(CommandUtils.SKILL_ALREADY_EXIST_MSG, name)));
    }
}
