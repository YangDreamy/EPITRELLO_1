package project.epita.trello;
import java.io.IOException;
import java.sql.Connection;

public interface EpitrelloDataServerice {
	public String addUser(String name);
	public boolean checklist(String listname);
	public boolean checkTask(String taskname);
	public boolean checkUser(String username);
	public String addList(String listname);
	public String addTask(String listname, String taskname, int estimatedtime,int priority,String description);
	public String editTask(String taskname,int estimatedtime,int priority,String description);
	public String assignTask(String taskname,String username);
	public String printTask(String print_task);
	public String completeTask(String taskname);
	public String printUsersByPerformance();
	public String printUsersByWorkload();
	public String printUnassignedTasksByPriority(); 
	public String deleteTask(String taskname);
	public String printAllUnfinishedTasksByPriority();
	public String moveTask(String taskname, String listname);
	public String printList(String listname);
	public String printAllLists();
	public String printUserTasks(String username);
	public void printtext() throws IOException;
	public int insert(Connection connection);
}
