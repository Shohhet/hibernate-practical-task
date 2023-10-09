package com.shoggoth.hibernateapp.controller.command.impl;

import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.controller.command.CommandType;
import com.shoggoth.hibernateapp.view.UserInterface;

public class HelpCommand implements Command {
    private final UserInterface ui;

    public HelpCommand(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        for (CommandType commandType : CommandType.values()) {
            ui.writeToConsole(commandType.getName().toLowerCase());
        }
    }
}
