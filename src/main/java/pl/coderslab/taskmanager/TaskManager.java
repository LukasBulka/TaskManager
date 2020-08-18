package pl.coderslab.taskmanager;

import java.util.Arrays;
import java.util.Scanner;

public class TaskManager
{
    private static String[][] taskList = new String[0][];

    public static void main(String[] args)
    {
        TaskManager.run();
    }

    public static void run()
    {
        showWelcomeMessage();
        loadTaskListFromFile();

        while (true)
        {
            showMainMenu();
            String userChoise = getUserChoise();
            if (validateUserChoise(userChoise))
            {
                executeValidChoise(userChoise);
                if (isExitChoise(userChoise))
                {
                    break;
                }
            }
            else
            {
                executeInvalidChoice(userChoise);
            }
        }

        showExitMessage();
        saveTaskListFromFile();
    }

    private static void saveTaskListFromFile() {

    }

    private static void loadTaskListFromFile() {

    }


    private static String getUserChoise()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private static boolean validateUserChoise(String userChoise)
    {
        String[] validChoises = {"add", "remove", "list", "exit"};
        Arrays.sort(validChoises);
        int index = Arrays.binarySearch(validChoises, userChoise);
        return index >= 0;
    }

    private static void executeValidChoise(String userChoise)
    {
        switch (userChoise)
        {
            case "add" :
                // obsluga executeAddChoise();
                break;
            case "remove" :
                // obsluga executeRemoveChoise();
                break;
            case "list" :
                // obsluga executeListChoise();
                break;
        }
    }

    private static boolean isExitChoise(String userChoise)
    {
        return "exit".equalsIgnoreCase(userChoise);
    }

    private static void executeInvalidChoice(String userChoise)
    {
        System.out.println(ConsoleColors.RED_BACKGROUND + "INVALID OPTION: '" + userChoise + "'. Please choose a valid option; ");
        System.out.print(ConsoleColors.RESET);
    }

    private static void showMainMenu()
    {
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.print(ConsoleColors.RESET);
        System.out.println("\tadd");
        System.out.println("\tremove");
        System.out.println("\tlist");
        System.out.println("\texit");
    }

    private static void showExitMessage()
    {
        System.out.println(ConsoleColors.RED + "GOODBYE");
        System.out.print(ConsoleColors.RESET);
    }

    private static void showWelcomeMessage()
    {
        System.out.println(ConsoleColors.RED + "WELCOME TO TASK MANAGER");
        System.out.print(ConsoleColors.RESET);
    }
}
