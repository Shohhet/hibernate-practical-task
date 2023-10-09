package com.shoggoth.hibernateapp.controller.command.impl;


import com.shoggoth.hibernateapp.controller.command.Command;
import com.shoggoth.hibernateapp.view.UserInterface;

public class DefaultCommand implements Command {
    private final UserInterface ui;

    public DefaultCommand(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.WRONG_COMMAND_NAME_MSG);
    }
}