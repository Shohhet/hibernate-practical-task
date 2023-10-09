package com.shoggoth.hibernateapp.controller.command.impl.specialty;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.impl.CommandUtils;
import com.shoggoth.hibernateapp.model.entity.SpecialtyEntity;
import com.shoggoth.hibernateapp.servise.SpecialtyService;
import com.shoggoth.hibernateapp.servise.dto.SpecialtyDto;
import com.shoggoth.hibernateapp.view.UserInterface;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllSpecialtysCommand implements Command {
    private final UserInterface ui;
    private final SpecialtyService service;

    @Override
    public void execute() {
        List<SpecialtyDto> specialtys = service.getAll();
        if (specialtys.isEmpty()) {
            ui.writeToConsole(CommandUtils.EMPTY_SPECIALTY_LIST_MSG);
        } else {
            specialtys.forEach(specialty -> ui.writeToConsole(specialty.toString()));
        }
    }
}