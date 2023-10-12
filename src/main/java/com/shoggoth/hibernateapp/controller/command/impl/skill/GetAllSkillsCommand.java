package com.shoggoth.hibernateapp.controller.command.impl.skill;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.SkillService;
import com.shoggoth.hibernateapp.servise.impl.SkillServiceImpl;
import com.shoggoth.hibernateapp.servise.dto.SkillDto;
import com.shoggoth.hibernateapp.view.UserInterface;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllSkillsCommand implements Command {
    private final UserInterface ui;
    private final SkillService service;

    @Override
    public void execute() {
        List<SkillDto> skills = service.getAll();
        if (skills.isEmpty()) {
            ui.writeToConsole(CommandUtils.EMPTY_SKILL_LIST_MSG);
        } else {
            skills.forEach(skill -> ui.writeToConsole(skill.toString()));
        }
    }
}
