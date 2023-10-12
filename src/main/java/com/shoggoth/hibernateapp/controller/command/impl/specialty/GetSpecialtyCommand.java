package com.shoggoth.hibernateapp.controller.command.impl.specialty;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.impl.SpecialtyServiceImpl;
import com.shoggoth.hibernateapp.view.UserInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSpecialtyCommand implements Command {
    private final UserInterface ui;
    private final SpecialtyServiceImpl service;


    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_ID_MSG);
        var stringId = ui.readFromConsole();
        try {
            var id = Long.parseLong(stringId);
            service.getById(id).ifPresentOrElse(
                    specialty -> ui.writeToConsole(specialty.toString()),
                    () -> ui.writeToConsole(String.format(CommandUtils.SPECIALTY_DOES_NOT_EXIST_MSG, id))
            );
        } catch (NumberFormatException e) {
            ui.writeToConsole(String.format(CommandUtils.WRONG_ID_FORMAT_MSG, stringId));
        }
    }
}