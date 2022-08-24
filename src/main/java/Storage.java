import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.time.format.DateTimeFormatter;

public class Storage {

    private String filePath;

    public Storage(String filepath) {
        this.filePath = filepath;
    }

    public void saveFile(ArrayList<String> arr) {
        String fileName = "tasks.txt";
        FileWriter fileWriter = null;

        try {
            File file = new File(filePath);
            fileWriter = new FileWriter(file);

            for (int i = 0; i < arr.size(); i++) {
                fileWriter.write(arr.get(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    public TaskList loadFile() throws DukeException {
        BufferedReader reader = null;
        TaskList arr = new TaskList(new ArrayList<>());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss");

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                String[] parse = line.split(" ~ ");
                Task tsk = null;
                if (parse[0].equals("T")) {
                    tsk = new Todo(parse[2]);
                } else if (parse[0].equals("E")) {
                    tsk = new Event(parse[2], parse[3]);
                } else if (parse[0].equals("D")) {
                    tsk = new Deadline(parse[2], parse[3], formatter);
                } else {
                    throw new DukeException("Invalid File");
                }
                if (parse[1].equals("X")) {
                    tsk.markAsDone();
                }
                arr.addTask(tsk);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } finally {
                    return arr;
                }
            } else {
                return arr;
            }
        }
    }
}

