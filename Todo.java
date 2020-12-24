import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Todo {


	private static final String menu = "Usage :-\n$ ./todo add \"todo item\"  # Add a new todo\n$ ./todo ls               # Show remaining todos\n$ ./todo del NUMBER       # Delete a todo\n$ ./todo done NUMBER      # Complete a todo\n$ ./todo help             # Show usage\n$ ./todo report           # Statistics";

	public static void main(String args[]) {

		try {
			fileCheck();
		} catch (Exception e) {
			System.err.println(e);
		}
		

		if (args.length == 0 || args[0].equals("help")) {
			System.out.println(menu);

		} else if (args[0].equals("add")) {
			if(args.length <= 1){
				System.out.print("Error: Missing todo string. Nothing added!");
			}else{
				try {
					add(args[1]);
				} catch (Exception e) {
					System.err.println(e);
				}
			}
			
		} else if (args[0].equals("ls")) {
			try {
				ls();
			} catch (Exception e) {
				System.err.println(e);
			}
		} else if (args[0].equals("del")) {
			if (args.length == 1){
				System.out.print("Error: Missing NUMBER for deleting todo.");
			}else{
				try {
					del(Integer.parseInt(args[1]));
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		} else if (args[0].equals("done")) {
			if(args.length == 1){
				System.out.print("Error: Missing NUMBER for marking todo as done.");
			}else{
				try {
					done(Integer.parseInt(args[1]));
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		} else if (args[0].equals("report")) {
			try {
				report();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public static void add(String todo) throws Exception {
		List<String> todosList = new ArrayList<String>();
		todosList = Files.readAllLines(Paths.get("todo.txt"));
		int count = Integer.parseInt(todosList.get(0));
		count++;
		todosList.add(todo);
		todosList.set(0, "" + count);
		FileWriter fWriter = new FileWriter("todo.txt");
		for (String string : todosList) {
			fWriter.write(string + "\n");
		}
		fWriter.close();
		System.out.print("Added todo: \"" + todo + "\"");
	}

	public static void ls() throws Exception {
		List<String> todosList = new ArrayList<String>();
		todosList = Files.readAllLines(Paths.get("todo.txt"));
		if(todosList.size()==1){
			System.out.println("There are no pending todos!");
		}else{
			for (int i = todosList.size() - 1; i > 0; i--) {
				String todo = todosList.get(i);
				System.out.println("[" + i + "] " + todo);
			}
		}
		
	}

	public static void del(int index) throws Exception {
		List<String> todosList = new ArrayList<String>();
		todosList = Files.readAllLines(Paths.get("todo.txt"));
		if (index >= todosList.size() || index <= 0) {
			System.out.print("todo : #" + index + " does not exist. Nothing deleted");
		} else {
			int count = Integer.parseInt(todosList.get(0));
			count--;
			todosList.remove(index);
			todosList.set(0, "" + count);
			FileWriter fWriter = new FileWriter("todo.txt");
			for (String string : todosList) {
				fWriter.write(string + "\n");
			}
			fWriter.close();
			System.out.print("Deleted todo #" + index);
		}

	}

	public static void done(int index) throws Exception {
		List<String> todosList = new ArrayList<String>();
		todosList = Files.readAllLines(Paths.get("todo.txt"));
		if (index >= todosList.size() || index <= 0) {
			System.out.print("Error: todo #" + index + " does not exist");
		} else {
			int count = Integer.parseInt(todosList.get(0));
			count--;
			String todoDone = todosList.get(index);
			addToDoneList(todoDone);
			todosList.remove(index);
			todosList.set(0, "" + count);
			FileWriter fWriter = new FileWriter("todo.txt");
			for (String string : todosList) {
				fWriter.write(string + "\n");
			}
			fWriter.close();
			System.out.print("Marked todo #" + index + " as done");
		}

	}

	public static void addToDoneList(String todoDone) throws Exception {
		List<String> todosList = new ArrayList<String>();
		todosList = Files.readAllLines(Paths.get("done.txt"));
		int count = Integer.parseInt(todosList.get(0));
		count++;
		String todoInfo = "x " + java.time.LocalDate.now() + " " + todoDone;
		todosList.add(todoInfo);
		todosList.set(0, "" + count);
		FileWriter fWriter = new FileWriter("done.txt");
		for (String string : todosList) {
			fWriter.write(string + "\n");
		}
		fWriter.close();

	}

	public static void report() throws Exception {
		List<String> todosList = new ArrayList<String>();
		todosList = Files.readAllLines(Paths.get("todo.txt"));
		String pending = todosList.get(0);
		todosList = Files.readAllLines(Paths.get("done.txt"));
		String completed = todosList.get(0);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now) + " Pending : " + pending + " Completed : " + completed);

	}

	public static void fileCheck() throws Exception {
		File todofile = new File("todo.txt");
		if(todofile.createNewFile()){
			FileWriter fWriter = new FileWriter(todofile);
			fWriter.write("0" + "\n");
			fWriter.close();
		}
		File donefile = new File("done.txt");
		if(donefile.createNewFile()){
			FileWriter fWriter = new FileWriter(donefile);
			fWriter.write("0" + "\n");
			fWriter.close();
		}
	}

}
