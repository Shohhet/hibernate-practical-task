package com.shoggoth.hibernateapp.controller.command.impl.specialty;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.servise.impl.SpecialtyServiceImpl;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;
import com.shoggoth.hibernateapp.view.UserInterface;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateSpecialtyCommand implements Command {
    private final UserInterface ui;
    private final SpecialtyServiceImpl service;

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_ID_MSG);
        String stringId = ui.readFromConsole();
        ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_NAME_MSG);
        String name = ui.readFromConsole();
        try {
            var id = Long.parseLong(stringId);
            if (service.update(new SpecialtyDto(id, name))) {
                ui.writeToConsole(String.format(CommandUtils.SPECIALTY_UPDATED_MSG, id));
            } else {
                ui.writeToConsole(String.format(CommandUtils.SPECIALTY_DOES_NOT_EXIST_MSG, stringId));
            }
        } catch (NumberFormatException e) {
            ui.writeToConsole(String.format(CommandUtils.WRONG_ID_FORMAT_MSG, stringId));
        } catch (ConstraintViolationException ce) {
            ui.writeToConsole(ce.getMessage());
        }
    }
}