package command;

import data.players.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Arrays;

public class CommandHandler {
    protected Player player;
    protected static String command;

    public CommandHandler(String playerName) {
        player = new Player(playerName);
    }

    private String input;
    public void execute(String command) {
        if (command.length() > 0) {
            if (command.charAt(0) == '/') {
                CommandHandler.command = command;
                encode(command);
            }
        }
    }

    /**
     * How this work:
     * 1. Array Conversion
     * 2. Validate Arguments
     * @param command
     */
    private void encode(String command) {
        /**
         * a command should start with a prefix of '/'
         * having multiple spaces between command input arguments is fine
         * it should be split with //s+
         */

        // Convert to array
        String[] commands = command.split("\s+");

        // Validate arguments
        validate(commands);
    }

    private void validate(String[] commands) {
        // Search for a valid command and execute
        switch (commands[0]) {
            case "/give" -> new GiveCommand().run(commands, player);
            default -> {
                System.err.println("Unknown or incomplete command, see below for error");
                System.err.println(CommandHandler.command + "<--[HERE]");
            }
        }
    }
}
