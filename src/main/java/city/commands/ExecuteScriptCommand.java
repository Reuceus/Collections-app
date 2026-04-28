package city.commands;

import city.manager.CommandManager;
import city.manager.InputManager;

import java.io.*;

/**
 * Команда execute_script для чтения и выполнения команд из указанного файла скрипта.
 */
public class ExecuteScriptCommand implements Command {

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "Выполнить скрипт из файла";
    }

    @Override
    public String execute(String[] args, Object obj) {

        if (args == null || args.length != 1) {
            return "Использование: execute_script file_name";
        }

        return "EXEC_SCRIPT:" + args[0];
    }
}
