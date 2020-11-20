package homework.task_01_03.four_question;

public enum COMMAND {
    EXIT("0"), ADD("1"), REMOVE("2"), UPDATE("3"), FIND("4"), FIND_ALL("5");

    private final String command;

    COMMAND(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static COMMAND fromCommand(String command) {
        for (COMMAND value : COMMAND.values()) {
            if (value.getCommand().equals(command)) {
                return value;
            }
        }
        return null;
    }
}
