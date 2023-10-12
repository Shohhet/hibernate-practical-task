package com.shoggoth.hibernateapp.controller.command.impl.developer;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.impl.DeveloperServiceImpl;
import com.shoggoth.hibernateapp.view.UserInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllDevelopersCommand implements Command {
    private final UserInterface ui;
    private final DeveloperServiceImpl service;

    @Override
    public void execute() {
        var developers = service.getAll();
        if(developers.isEmpty()) {
            ui.writeToConsole(CommandUtils.EMPTY_DEVELOPER_LIST_MSG);
        } else{
            developers.forEach(developerDto -> ui.writeToConsole(developerDto.toString()));
        }
    }
}
