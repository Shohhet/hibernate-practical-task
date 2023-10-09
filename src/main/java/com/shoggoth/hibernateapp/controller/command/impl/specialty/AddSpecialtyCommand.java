package com.shoggoth.hibernateapp.controller.command.impl.specialty;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.SpecialtyService;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;
import com.shoggoth.hibernateapp.view.UserInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddSpecialtyCommand implements Command {

    private final UserInterface ui;
    private final SpecialtyService service;

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_NAME_MSG);
        String name = ui.readFromConsole();
        var specialtyDto = new SpecialtyDto(null, name);
        service.add(specialtyDto).ifPresentOrElse(
                (id) -> ui.writeToConsole(String.format(CommandUtils.SPECIALTY_ADDED_MSG, name, id)),
                () -> ui.writeToConsole(String.format(CommandUtils.SPECIALTY_ALREADY_EXIST_MSG, name)));
    }
}