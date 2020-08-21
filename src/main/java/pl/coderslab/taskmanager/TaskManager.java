package pl.coderslab.taskmanager;

import java.io.*;
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
    private static void showWelcomeMessage()
    {
        System.out.println(ConsoleColors.YELLOW + "----------------------------------------------------------");
        System.out.println(ConsoleColors.CYAN + "\t\t\t\tWELCOME TO TASK MANAGER" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "----------------------------------------------------------" + "\n");
    }
    private static String[][] loadTaskList()
    {
        File fileToLoad = new File("tasks.csv");
        String[] entries = new String[0];

        try(Scanner scanner = new Scanner(fileToLoad))
        {
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
        return taskList;
    }
    private static void showMainMenu()
    {
        System.out.println(ConsoleColors.BLUE + "\t\t\t\tPlease select an option..." + ConsoleColors.RESET );
        System.out.println(ConsoleColors.YELLOW + "\t[a]" + ConsoleColors.RESET + " - add" + ConsoleColors.YELLOW + "\t[r]" + ConsoleColors.RESET + " - remove" + ConsoleColors.YELLOW + "\t[l]" + ConsoleColors.RESET + " - list" + ConsoleColors.YELLOW + "\t[e]" + ConsoleColors.RESET + " - exit");
    }
    private static String getUserInput()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
    private static boolean validateUserChoise(String userChoise)
    {
        String[] validChoises = {"a", "r", "l", "e"};
        Arrays.sort(validChoises);
        int index = Arrays.binarySearch(validChoises, userChoise);
        return index >= 0;
    }
    private static void executeValidChoise(String userChoise)
    {
        switch (userChoise)
        {
            case "a" :
                addTasks();
                break;
            case "r" :
                removeTasks();
                break;
            case "l" :
                listTasks();
                break;
            case "e":
                saveTaskList();
                break;
        }
    }
    private static boolean isExitChoise(String userChoise)
    {
        return "e".equalsIgnoreCase(userChoise);
    }
    private static void executeInvalidChoice(String userChoise)
    {
        System.out.println(ConsoleColors.RED_BACKGROUND + "INVALID OPTION: '" + userChoise + "'. Please choose a valid option; ");
        System.out.print(ConsoleColors.RESET);
    }
    private static void showExitMessage()
    {
        System.out.println(ConsoleColors.RED + "GOODBYE" + ConsoleColors.RESET);
    }
    private static void saveTaskList()
    {
        try(PrintWriter printWriter = new PrintWriter("tasks.csv"))
        {
            for (int i = 0; i < taskList.length; i++)
            {
                String line = "";
                for (int j = 0; j < taskList[i].length; j++)
                {
                    line += taskList[i][j] + " ";
                }
                printWriter.println(line);
            }

        }
        catch (IOException ex)
        {
            ex.getMessage();
        }
    }
    private static void addTasks()
    {
        System.out.println("Please add task description: ");
        String desc = getUserInput();
        System.out.println("Please add task due date: ");
        String date = getUserInput();
        System.out.println("Is your task important: true / false ");
        String important = getUserInput();

        taskList = Arrays.copyOf(taskList, taskList.length + 1);
        taskList[taskList.length-1] = new String[3];
        taskList[taskList.length-1][0] = desc;
        taskList[taskList.length-1][1] = date;
        taskList[taskList.length-1][2] = important;
    }
    private static void removeTasks()
    {
        int entryToRemove;
        Scanner scanner = new Scanner(System.in);

        do
        {
            System.out.println("Please select a correct entry number to remove: ");
            while (!scanner.hasNextInt())
            {
                scanner.next();
                System.out.println("Incorrect argument passed. Please give number greater than 0: ");
            }
            entryToRemove = scanner.nextInt();
        }
        while (entryToRemove > taskList.length || entryToRemove <= 0);

        String[][] tempTaskList = new String[taskList.length - 1][];
        int index = 0;

        for (int i = 0; i < taskList.length; i++)
        {
            if (taskList[entryToRemove -1] != (taskList[i]))
            {
                tempTaskList[index] = taskList[i];
                index++;
            }
        }
        taskList = new String[tempTaskList.length][];

        for (int i = 0; i < tempTaskList.length; i++)
        {
            taskList[i] = tempTaskList[i];
        }
    }
    private static void listTasks()
    {
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
}