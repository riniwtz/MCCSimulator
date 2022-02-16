package main;

import command.CommandHandler;
import data.blocks.Blocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static String MINECRAFT_VERSION = "22w06a";

    public Main() {

        while (true) {
            Scanner in = new Scanner(System.in);
            CommandHandler commandHandler = new CommandHandler("riniwtz");
            commandHandler.execute(in.nextLine());
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}