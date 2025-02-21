package s1TaskManager;

import java.util.LinkedList;


public class TaskManager {


    public static void main(String[] args) {
        //TaskManagerUI taskManagerUI = new TaskManagerUI();
        //taskManagerUI.display();
        run();
    }

    // Program logic
    public static void run(){
        storage.loadTasks(storage.tasks);
        System.out.println("Welcome to Task Manager!");
        label:
        while(true){
            System.out.println("What do you want to do.(set/add/view/delete/finished/clear/help/exit)?");
            String input = System.console().readLine();
            String firstWord = "";

                // Check for null or empty input
            if (input == null || input.trim().isEmpty()) {
                System.out.println("No input provided.");
                // You can decide how to handle this, like setting firstWord to an empty string
            } else {
                input = input.toLowerCase();
                // Split by whitespace and get the first word
                String[] words = input.split("\\s+");

                // If there's at least one word, get the first one
                 firstWord = words.length > 0 ? words[0] : "";
            }
            switch (firstWord) {
                case "exit":
                    break label;
                case "add":
                    //System.out.println("What do you want to add to your list of Tasks?");
                    if (input.length() < 4){
                        System.out.println("Please enter a task to add");
                        continue;
                    }
                    String task = input.substring(input.indexOf("add") + 4);
                    addTask(task);
                    System.out.println("Added " + task);
                    break;
                case "set":
                    System.out.println("Please keep on listing tasks until you type done");

                    String taskInput = System.console().readLine();
                    while (!taskInput.equals("done")) {
                        addTask(taskInput);
                        taskInput = System.console().readLine();
                    }
                case "view":
                    System.out.println("Tasks:");
                    getTasks(storage.tasks);

                    break;
                case "delete":
                   // System.out.println("Which task do you want to delete?");
                    if (input.length() < 7){
                        System.out.println("Please enter a task index");
                        continue;
                    }
                    int taskIndex = Integer.parseInt(input.substring(input.indexOf("delete")+7)) - 1;
                    if (taskIndex < 0 || taskIndex >= storage.tasks.size()) {
                        System.out.println("Please enter a valid task index");
                        continue;
                    }
                    deleteTask(taskIndex);
                    System.out.println("Deleted task");
                    break;
                case "finished":
                    //System.out.println("Which task do you want to mark as finished?");
                    if (input.length() < 9){
                        System.out.println("Please enter a task index");
                        continue;
                    }
                    int finishedIndex = Integer.parseInt(input.substring(input.indexOf("finished")+9)) - 1;
                    if (finishedIndex < 0 || finishedIndex >= storage.tasks.size()) {
                        System.out.println("Please enter a valid task index");
                        continue;
                    }
                    finishedTask(finishedIndex);
                    break ;

                case "clear":
                   clearTasks();
                   System.out.println("Cleared tasks");
                   break;
                case "help":
                    helpRef();
                    break;
            }
        }
        storage.saveTasks(storage.tasks);
        System.out.println("Goodbye!");

    }

    // Recalling functions due to lax
    public static boolean isInteger(String str) {
        return storage.isInteger(str);
    }
    public static boolean checkDueDate(String dueDate){
        return storage.checkDueDate(dueDate);
    }


    // Logic helpers

    // for add and set commands
    public static void getTasks(LinkedList<storage.Task> tasks) {
        int count = 1;
        for (storage.Task task: tasks) {
           System.out.println(count +". "+task.toString());
           count++;
        }
    }
    public static void addTask(String input){
        String[] splitInput = input.split("\\s+");
        StringBuilder task= new StringBuilder();
        String dueDate = "";
        int priority = 0;
        for (String s: splitInput) {
            if (checkDueDate(s)) {
                dueDate = s;
                continue;
            }  if (isInteger(s)) {
                priority = Integer.parseInt(s);
                if (priority > 3 || priority < 1) {
                    priority = 100;
                }
                continue;
            }  if (task.isEmpty()) {
                task = new StringBuilder(s);
            } else {
                task.append(" ").append(s);
            }

        }
        storage.Task task1 = new storage.Task(task.toString(), dueDate, priority);
        storage.tasks.add(task1);
        storage.mergeSortList(storage.tasks);

    }

    // for deleting a task
    public static void deleteTask(int index){
        storage.tasks.remove(index);
    }

    // for marking a task finished
    public static void finishedTask(int index){
        storage.Task task = storage.tasks.get(index) ;
        System.out.println(task.taskName +" is now marked as finished");
        task.taskName = storage.tasks.get(index).taskName+" (Finished)";
        task.setDueDate("");
        task.setPriority(-1);
        storage.tasks.set(index, task);
    }

    // for clearing the linked list of tasks
    public static void clearTasks(){
        storage.tasks.clear();
    }

    // Listing commands for reference
    public static void helpRef(){
        System.out.println("Write the command you want to know about or type exit to exit: ");
        while(true) {

            String command = System.console().readLine().toLowerCase();
            if (command.equals("exit")) {
                break;
            }
            defineFunctions(command);
        }
    }
    public static void defineFunctions(String function){
        switch (function) {
            case "add" ->
                    System.out.println("Adds a Task and you can mention the due date (dd/mm/yyyy) and priority (1[highest]-3[lowest])\n Example: add do this 1/11/23 2");
            case "view" -> System.out.println("Lists down all the tasks you currently have to do");
            case "finished" -> System.out.println("Tags a task by its index finished");
            case "clear" -> System.out.println("Clears all the tasks");
            case "set" ->
                    System.out.println("Set a bunch of tasks to do next line after set\n Example:\n set \n Please keep on listing tasks until you type done \n do this \n do that 1/12/24 \n done");
        }
        System.out.println("Mention the command you want to know");
    }
}
