package command;

import data.blocks.Blocks;
import data.players.Player;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GiveCommand {

    private String targets;
    private String item;
    private BigInteger count = new BigInteger("1");

    private boolean targetsError = false;
    private boolean itemError = false;

    /**
     * 1. Unknown or incomplete command
     * 2. Invalid name or UUID - This is when input length is greater than 16 and if the first character is a symbol except (@, -, ., ', ") and normal characters
     * 3. Expected whitespace - This is when there's a symbol in a string 2nd index more except (-, ., @) and normal characters
     * 4. Unclosed quoted string - This is when the first character is a quote (' or ") and no closing quote
     * 5. Missing selector type - This is when we only had '@' in first character
     * 6. Unknown selector type - This is when the selector type is incorrect
     *
     * https://www.digminecraft.com/getting_started/target_selectors.php
     * @param commands
     */


//    @p	Targets the nearest player
//    @r	Targets a random player
//    @a	Targets all players
//    @e	Targets all entities (See list of entities in Minecraft)
//    @s	Targets the entity that is executing the command (referred to as "yourself")

    protected void run(String[] commands, Player player) {
        if (!error(commands, player)) {
            System.out.println("Gave " + count + " [" + item + "] to " + player.getName());
        }
    }

    protected boolean error(String[] commands, Player player) {
        // consists of any words, digits, and only period, dash, underscore symbols
        String DEF_REGEX_CHECK = "\\w+|\\d+|\\.|-|_|\\+";
        Pattern DEF_MATCHER = Pattern.compile(DEF_REGEX_CHECK);

        // check command argument length
        if (commands.length >= 2) {
            targets = commands[1];
            // check first if it's not empty
            if (targets.length() > 0) {
                // check first if there's target selectors
                if (targets.charAt(0) == '@') {
                    if (targets.length() == 1) {
                        System.err.println("Missing selector type");
                        System.err.println(CommandHandler.command + "<--[HERE]");
                        return true;
                    } else {
                        String[] targetSelectorTypes = {"@a", "@e", "@p", "@r", "@s"};
                        // check if targets is equal to @e target selector type
                        if (targets.startsWith(targetSelectorTypes[1])) {
                            System.err.println("Only players may be affected by this command, but the provided selector includes entities");
                            System.err.println(CommandHandler.command + "<--[HERE]");
                            return true;
                        }
                        // check if it matches selector type with targets length greater than 2
                        else if (selectorTypeExist(targetSelectorTypes, targets) && targets.length() > 2) {
                            System.err.println("Expected whitespace to end one argument, but found trailing data");
                            System.err.println(CommandHandler.command + "<--[HERE]");
                            return true;
                        }
                        // check if it doesn't match selector types
                        else if (!selectorTypeExist(targetSelectorTypes, targets)) {
                            System.err.println("Unknown selector type '" + targets.substring(0, 2) + "'");
                            System.err.println(CommandHandler.command + "<--[HERE]");
                            return true;
                        }
                        // default error
                        else {
                            System.err.println("Unknown or incomplete command, see below for error");
                            System.err.println(CommandHandler.command + "<--[HERE]");
                            return true;
                        }
                    }
                } else {
                    // check first character if symbols
                    if (!DEF_MATCHER.matcher(String.valueOf(targets.charAt(0))).find()) {
                        System.err.println("Invalid name or UUID");
                        System.err.println(CommandHandler.command + "<--[HERE]");
                        return true;
                    }
                    // check 2nd character up
                    else if (!DEF_MATCHER.matcher(targets.substring(1)).find() && targets.length() > 1) {
                        System.err.println("Expected whitespace to end one argument, but found trailing data");
                        System.err.println(CommandHandler.command + "<--[HERE]");
                        return true;
                    } else {
                        /**
                         * FIX THE AMOUNT
                         */
                        if (commands.length >= 3) {
                            Blocks blocks = new Blocks();
                            item = blocks.getBlockMap().get(commands[2]);
                            if (commands.length == 4)
                                count = new BigInteger(commands[3]);
                            if (!player.getName().equals(targets)) {
                                if (blocks.exists(item)) {
                                    System.err.println("No player was found");
                                    return true;
                                }
                            }
                        } else {
                            System.err.println("Unknown or incomplete command, see below for error");
                            System.err.println(CommandHandler.command + "<--[HERE]");
                            return true;
                        }
                    }
                }
            }
        } else {
            System.err.println("Unknown or incomplete command, see below for error");
            System.err.println(CommandHandler.command + "<--[HERE]");
            return true;
        }
        return false;
    }

    private boolean selectorTypeExist(String[] targetSelectorTypes, String selectorType) {
        for (String selector : targetSelectorTypes)
            if (selectorType.startsWith(selector)) return true;
        return false;
    }
}
