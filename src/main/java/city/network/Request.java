package city.network;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private String[] args;
    private Object objectArg;

    public Request () {
    }

    public Request(String commandName, String[] args, Object argument) {
        this.commandName = commandName;
        this.args = args;
        this.objectArg = argument;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public Object getObjectArg() {
        return this.objectArg;
    }

    public String[] getArgs() {
        return this.args;
    }
}
