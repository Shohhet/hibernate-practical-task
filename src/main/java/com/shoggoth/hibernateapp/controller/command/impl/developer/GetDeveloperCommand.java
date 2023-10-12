package com.shoggoth.hibernateapp.controller.command.impl.developer;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.impl.DeveloperServiceImpl;
import com.shoggoth.hibernateapp.view.UserInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetDeveloperCommand implements Command {
    private final UserInterface ui;
    private final DeveloperServiceImpl service;

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_ID_MSG);
        String stringId = ui.readFromConsole();
        try {
            var id = Long.parseLong(stringId);
            service.getById(id).ifPresentOrElse(
                    developer -> ui.writeToConsole(developer.toString()),
                    () -> ui.writeToConsole(String.format(CommandUtils.DEVELOPER_DOES_NOT_EXIST_MSG, stringId))
            );
        } catch (NumberFormatException e) {
            ui.writeToConsole(String.format(CommandUtils.WRONG_ID_FORMAT_MSG, stringId));
        }
    }
}
