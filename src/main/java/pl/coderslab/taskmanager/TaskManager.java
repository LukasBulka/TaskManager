package pl.coderslab.taskmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager
{
    private static String[][] taskList;

    public static void main(String[] args)
    {
        TaskManager.run();
    }

    public static void run()
    {
        showWelcomeMessage();
        loadTaskList();

        while (true)
        {
            showMainMenu();
            String userChoise = getUserInput();
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
        saveTaskList();
    }

    private static void saveTaskList() {

    }

    private static void loadTaskList()
    {
        File fileToLoad = new File("tasks.csv");
        String[] entries = new String[0];

        try
        {
            Scanner scanner = new Scanner(fileToLoad);
            while (scanner.hasNextLine())
            {
                entries = Arrays.copyOf(entries, entries.length + 1);
                entries[entries.length - 1] = scanner.nextLine();    //   + "\n"

            }
            taskList = new String[entries.length][0];

            for (int i = 0; i < entries.length; i++)
            {
                taskList[i] = entries[i].split(" ");

            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage() + " Please check the file name.");
        }
    }


    private static String getUserInput()
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
                addTasks();
                break;
            case "remove" :
                // obsluga executeRemoveChoise();
                break;
            case "list" :
               listTasks();
                break;
        }
    }

    private static void addTasks()
    {
        try(FileWriter fileWriter = new FileWriter("tasks.csv", true))
        {
            System.out.println("Please add task description: ");
            fileWriter.append("\n" + getUserInput() + ", ");
            System.out.println("Please add task due date: ");
            fileWriter.append(getUserInput() + ", ");
            System.out.println("Is your task important: true / false ");
            fileWriter.append(getUserInput());
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage() + "Can't write to file: ");
        }
    }

    private static void listTasks()
    {
        loadTaskList();

        String separator = ConsoleColors.RED + "|---------------------------------------------------------------------------------------|" + ConsoleColors.RESET;
        System.out.println(ConsoleColors.YELLOW + "Current tasks: " + ConsoleColors.RESET);

        for (int i = 0; i < taskList.length; i++)
        {
            System.out.print("[" + (i + 1) + "] ");
            for (int j = 0; j < taskList[i].length; j++)
            {
                System.out.print(taskList[i][j] + " ");
            }
            System.out.println();
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
        System.out.println(ConsoleColors.RED + "|\t" + ConsoleColors.RESET + ConsoleColors.BLUE + "\tPlease select an option:" + ConsoleColors.RESET + ConsoleColors.RED + "                                                        |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "+---------------------------------------------------------------------------------------+");
        System.out.println("\t   [a] - add\t [r] - remove\t   [l] - list\t   [e] - exit");
        System.out.println(ConsoleColors.RED + "+---------------------------------------------------------------------------------------+" + ConsoleColors.RESET);
    }

    private static void showExitMessage()
    {
        System.out.println(ConsoleColors.RED + "GOODBYE");
        System.out.print(ConsoleColors.RESET);
    }

    private static void showWelcomeMessage()
    {
        System.out.println(ConsoleColors.RED + "+---------------------------------------------------------------------------------------+");
        System.out.println(ConsoleColors.RED + "|                                 " + ConsoleColors.CYAN + "Welcome to TASK MANAGER" + ConsoleColors.RED + "                               |");
        System.out.println(ConsoleColors.RED + "+---------------------------------------------------------------------------------------+");
        System.out.print(ConsoleColors.RESET);
    }
}
