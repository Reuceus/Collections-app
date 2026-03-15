package city.commands;

import city.manager.CommandManager;
import city.manager.InputManager;

import java.io.*;

/**
 * Команда execute_script для чтения и выполнения команд из указанного файла скрипта.
 */
public class ExecuteScriptCommand implements Command{
    private final InputManager im;

    public ExecuteScriptCommand(InputManager im) {
        this.im = im;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "Cчитать и исполнить скрипт из указанного файла";
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Использование: execute_script file_name");
            return;
        }

        try {
            im.pushScript(args[0]);
        } catch (Exception e) {
            System.out.println("Ошибка открытия файла");
        }
    }
}
