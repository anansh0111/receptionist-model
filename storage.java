package s1TaskManager;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class storage {
     // Variables initialized
     private static final String FILE_NAME = "tasks.txt";
     static LinkedList<storage.Task> tasks = new LinkedList<>();

    public static class Task{
        String taskName;
        String dueDate;
        int priority;

        //Constructors
        public Task(){
           setTaskName("");
           setDueDate(" ");
           setPriority(0);

        }
        public Task(String taskName, String dueDate, int priority){
            setTaskName(taskName);
            setDueDate(dueDate);
            setPriority(priority);
        }
        public Task(String taskName){
            setTaskName(taskName);
        }
        public Task(String taskName, String dueDate){
            setTaskName(taskName);
            setDueDate(dueDate);
        }

        //Getters
        public String getDueDate() {
            return dueDate;
        }
        public int getPriority() {
            return priority;
        }
        public String getTaskName() {
            return taskName;
        }

        //Setters
        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }
        public void setPriority(int priority) {
            this.priority = priority;
        }
        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String toString(){
            String value= getTaskName();
            if (dueDate!= null &&!dueDate.isEmpty()) {
                value+=" | Due Date: "+dueDate;
            }
            if (priority != 0) {
                value+=" | Priority: "+parsePriority(priority);
            }
            return value;
        }
    }




    // Parsing data helpers
    private static String parsePriority(int priority) {
        return switch (priority) {
            case 1 -> "high";
            case 2 -> "medium";
            case 3 -> "low";
            default -> ""; // Default to low if invalid
        };
    }
    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false; // Null or empty strings are not integers
        }
        try {
            Integer.parseInt(str);
            return true; // If parsing succeeds, it is an integer
        } catch (NumberFormatException e) {
            return false; // If parsing fails, it is not an integer
        }
    }
    public static boolean checkDueDate(String dueDate){
        dueDate = dueDate.trim();
        if (dueDate.length()!=8){
            // System.out.println("Please enter a valid due date");
            return false;
        }
        if (!isInteger(dueDate.substring(0,2))) {
            //  System.out.println("Please enter a valid due date");
            return false;
        } else if(Integer.parseInt(dueDate.substring(0,2))>31 || Integer.parseInt(dueDate.substring(0,2))<1){
            return false;
        }
        if (!isInteger(dueDate.substring(4,5))){          // System.out.println("Please enter a valid due date");
            return false;
        }else if(Integer.parseInt(dueDate.substring(4,5))>12 || Integer.parseInt(dueDate.substring(0,2))<1){
            return false;
        }
        if (!isInteger(dueDate.substring(7,8))){          // System.out.println("Please enter a valid due date");
            return false;
        }else if(Integer.parseInt(dueDate.substring(7,8))>99 || Integer.parseInt(dueDate.substring(0,2))<1){
            return false;
        }
        if (dueDate.charAt(2) != '/') {
            //System.out.println("Please enter a valid due date");
            return false;
        }
        //System.out.println("Please enter a valid due date");
        // To check the last / in the date.
        return dueDate.charAt(5) == '/';

    }
    private static Task constructTask(String[] line) {
        // String[] splitLine = line.split("\\| ");
        Task task = new Task();
        //StringBuilder build = new StringBuilder();
        // Validate and parse the fields
        for(String s : line){
            if (checkDueDate(s)){
                task.setDueDate(s);
                // build.setLength(0);
            }
            else if (isInteger(s)){
                task.setPriority(Integer.parseInt(s));
                //build.setLength(0);
            }else if (!s.isEmpty()) task.setTaskName(s);

        }

        return task;
    }


    // File handling at the beginning and end of the program
    public static void loadTasks(LinkedList<Task> tasks){
    File file = new File(FILE_NAME);
    if(file.exists()){
        try {
            System.out.println("Loading Tasks: ");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line=reader.readLine();

            int n=1;
            while (line!= null){
                String [] tokens = line.split(":");

                Task task = constructTask(tokens);
                tasks.add(task);
                line=reader.readLine();
            }
            mergeSortList(tasks);
            for(Task task : tasks){
                System.out.println(n++ + ". " + task);
            }

            reader.close();
    } catch (IOException e) {
            System.out.println("Error loading tasks");
            throw new RuntimeException(e);}

}else{
        System.out.println("No tasks found, creating new file");
    }
  }
    public static void saveTasks(LinkedList<Task> tasks){
        File file = new File(FILE_NAME);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Task task: tasks) {
                writer.write(task.getTaskName()+":"+task.getDueDate()+":"+task.getPriority()+"\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks");
        }
    }


    public static void mergeSortList(LinkedList<Task> list){
        Task[] temp = new Task[list.size()];
        mergeSort(list.toArray(temp));
        for(int i=0; i<list.size(); i++){
            list.set(i, temp[i]);
        }
    }
    public static void mergeSort(Task[] arr) {
        if (arr.length < 2) return; // Base case: array of size 1 is already sorted

        int mid = arr.length / 2;
        Task[] left = Arrays.copyOfRange(arr, 0, mid);  // Left half
        Task[] right = Arrays.copyOfRange(arr, mid, arr.length); // Right half

        mergeSort(left);  // Sort the left half
        mergeSort(right); // Sort the right half

        merge(arr, left, right);  // Merge both halves back together
    }
    // Merge two sorted arrays into a single sorted array
    private static void merge(Task[] arr, Task[] left, Task[] right) {
        int i = 0, j = 0, k = 0;

        // Merge elements while there are elements in both left and right
        while (i < left.length && j < right.length) {
            if (left[i].getPriority() <= right[j].getPriority()) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        // If there are any remaining elements in the left array
        while (i < left.length) {
            arr[k++] = left[i++];
        }

        // If there are any remaining elements in the right array
        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }
}



