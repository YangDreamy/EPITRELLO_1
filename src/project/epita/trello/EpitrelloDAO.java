package project.epita.trello;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class EpitrelloDAO implements EpitrelloDataServerice{
	List<User> user = new ArrayList<>();
	List<Lists> lists = new ArrayList<>();
	List<Task> task = new ArrayList<>();
	public boolean checkUser(String username)
	{
		for(int i = 0;i < user.size(); i ++)
		{
			if(user.get(i).getUser_name().equals(username)) {
				return true;
			}
		}
		return false;
	}
	public String addUser(String name)
	{
		String results = "";
		if(null == user || user.size() ==0 )
		{
			user.add(new User(name));
			results="Add User Success";
		}
		else
		{
			if(checkUser(name))
			{
				results="User already exists\n";
			}
		    else 
		    {
		    	user.add(new User(name));
				results="Add User Success";
		    }
				
		}	
		return results;	
	}
	public boolean checklist(String listname)
	{
		for( int i = 0;i < lists.size(); i ++)
		{
			if(lists.get(i).getListname().equals(listname)) 
				return true;
		} 
		return false;
	}
	public String addList(String listname)
	{
		String results = "";
		if(null == lists || lists.size()==0)
		{
			lists.add(new Lists(listname));	
			results="add list success";
		}
		else
		{
			if(!checklist(listname))
			{
				lists.add(new Lists(listname));
				results="add list success";
			}
			else results="list already exists\n";
		}		
		return results;
		
	}
	@Override
	public boolean checkTask(String taskname) {
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getTaskname()!= null && task.get(i).getTaskname().length()!= 0)
			{
				if(task.get(i).getTaskname().equals(taskname)) {
					return true;}
			}
		}
		return false;
	}
	@Override
	public String addTask(String listname, String taskname, int estimatedtime, int priority,String description) 
   {
		String results = "";
		if(!checklist(listname))
		{
			results="List does not exist";
		}
		else
		{
			if(null == task || task.size()==0)
			{
				task.add(new Task(listname,taskname,estimatedtime,priority,description));	
				results="add task success";
			}
			else
			{
				if(!checkTask(taskname))
				{
					task.add(new Task(listname,taskname,estimatedtime,priority,description));
					results="add task success";
				}
				else results="Task already exist\n";
			}		
		}		
		return results;
	}
	@Override
	public String editTask(String taskname, int estimatedtime, int priority, String description) {
		int flag=0;String results="";
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getTaskname().equals(taskname)) {
				flag=1;
				task.get(i).setEstimatedtime(estimatedtime);
				task.get(i).setDescription(description);
				task.get(i).setPriority(priority);
				results="edit task success\n";
				break;}
		}
		if(flag==0)
		{
			results="task does not exist";
		}
		// TODO Auto-generated method stub
		return results;
	}
	@Override
	public String assignTask(String taskname, String username) {
		String results="";
		if(!checkTask(taskname))
		{
			results="task does not exit";
		}
		else if(!checkUser(username))
		{
			results="User does not exits";
		}
		else
		{
			for(int i=0;i<task.size();i++)
			{
				if(task.get(i).getTaskname().equals(taskname))
				{
					task.get(i).setUsername(username);
					results="assigned success";
				}
			}
			
		}
		return results;
	}
	@Override
	public String printTask(String print_task) {
		String results="";
		List<String> list = new ArrayList<>();
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getTaskname()!= null && task.get(i).getTaskname().length()!= 0)
			{
				if(task.get(i).getTaskname().equals(print_task))
				{
					String a = task.get(i).getTaskname();
					String b = task.get(i).getDescription();
					String c = "Priority: "+task.get(i).getPriority().toString();
					String d = "Estimated Time: "+task.get(i).getEstimatedtime().toString();
					String f = "";
					if(task.get(i).getUsername()==null)
						f = "Unassigned" + "\n";
					else
						f = "Assigned to " +task.get(i).getUsername()+ "\n";
					list.add(a+"\n"+b+"\n"+c+"\n"+d+"\n"+f);				
				}
			}
			
			for(int j=0;j<list.size();j++) {
				results = list.get(j);
			}
		}
		return results;
	}
	@Override
	public String completeTask(String taskname) {
		String results="";
		int flag=0;
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getTaskname().equals(taskname))
			{
				task.get(i).setCompletity(true);
				results="Complete success";
				flag=1;
				break;
			}
		}
		if(flag==0)  results="task does not eixts";
		return results;
	}
	public String printUsersByPerformance()
	{
		String results="";
		int actualtime =0;
		List<String> list = new ArrayList<>();
		List<Task> tasklist = new ArrayList<>();
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getUsername()!= null && task.get(i).getUsername().length()!= 0)
			{
				if(task.get(i).isCompletity())
				{
				    actualtime = task.get(i).getEstimatedtime();
					tasklist.add(task.get(i));	
				}
				else 
					{
					actualtime=0;
					tasklist.add(task.get(i));
					}
				
			}
		}
		Collections.sort(tasklist, new Comparator<Task>(){
            public int compare(Task t1, Task t2) {
                if(t1.getEstimatedtime()< t2.getEstimatedtime()){
                    return 1;
                }
                if(t1.getEstimatedtime()== t2.getEstimatedtime()){
                    return 0;
                }
                return -1;
            }
        });
		for(int j=0;j<tasklist.size();j++) {
			list.add( tasklist.get(j).getUsername());
		}
		for(int j=0;j<list.size();j++) {
			results += list.get(j);
					results+="\n";
		}
        
		return results;
		
	}
	public String printUsersByWorkload()
	{
		String results="";
		List<String> list = new ArrayList<>();
		List<Task> tasklist = new ArrayList<>();
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getUsername()!= null && task.get(i).getUsername().length()!= 0)
			{
				tasklist.add(task.get(i));
			}
		}
		Collections.sort(tasklist, new Comparator<Task>(){
            public int compare(Task t1, Task t2) {
                if(t1.getEstimatedtime()> t2.getEstimatedtime()){
                    return 1;
                }
                if(t1.getEstimatedtime()== t2.getEstimatedtime()){
                    return 0;
                }
                return -1;
            }
        });
		for(int j=0;j<tasklist.size();j++) {
			list.add( tasklist.get(j).getUsername());
		}
		for(int j=0;j<list.size();j++) {
			results += list.get(j);
					results+="\n";
		}
        
		return results;
	}
	public String printUnassignedTasksByPriority()
	{
		List<String> list=new ArrayList<String>();
		String results="";
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getUsername()==null)
			{
				String a = task.get(i).getPriority().toString() ;
				String b = " | " + task.get(i).getTaskname();
				String c = " | Unassigned";;
				String d = " | " + task.get(i).getEstimatedtime().toString()+ " h";
				list.add(a+b+c+d+"\n");
			}
			
		}
		for(int i=0;i<list.size();i++) {
			results = list.get(i);
		}
		return results;
		
	}
	public String deleteTask(String taskname)
	{
		String results="";
		if(!checkTask(taskname))
		{
			results="taskname does not exit\n";
		}
		else
		{
			for(int i=0;i<task.size();i++)
			{
				if(task.get(i).getTaskname().equals(taskname)) {
					task.get(i).setTaskname(null);
					results="taskname delete success\n";
				}
			}
		}
		return results;
		
	}
	public String printAllUnfinishedTasksByPriority()
	{
		List<String> list=new ArrayList<String>();
		String results="";
		Collections.sort(task, new Comparator<Task>(){
            public int compare(Task t1, Task t2) {
                if(t1.getPriority() < t2.getEstimatedtime()){
                    return 1;
                }
                if(t1.getEstimatedtime()== t2.getEstimatedtime()){
                    return 0;
                }
                return -1;
            }
        });
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getTaskname()!= null && task.get(i).getTaskname().length()!= 0)
			{
				if(!task.get(i).isCompletity())
				{
					String a = task.get(i).getPriority().toString() ;
					String b = " | " + task.get(i).getTaskname();
					String c="";
					if(task.get(i).getUsername()==null)
					{
						 c = " | Unassigned";
					}
					else {c = " | "+task.get(i).getUsername();}
					String d = " | " + task.get(i).getEstimatedtime().toString()+ " h";
					list.add(a+b+c+d+"\n");
				}		}
			
		}
		for(int i=0;i<list.size();i++) {
			results += list.get(i);
		}
		return results;
		
	}
	public String moveTask(String taskname, String listname)
	{
		String results="";
		if(!checkTask(taskname))
		{
			results="task does not exit";
		}
		else if (!checklist(listname))
		{
			results="list does not exit";
		}
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getTaskname()!= null && task.get(i).getTaskname().length()!= 0)
			{
				if(task.get(i).getTaskname().equals(taskname))
				{
					task.get(i).setListname(listname);
					results="move task success\n";
				}
			}		
		}
		return results;
	}
	public String printList(String listname)
	{
		String results="";
		List<String> list1=new ArrayList<String>();
		if(!checklist(listname))
		{
			results="listname does not exits";
		}
		String a = "List " +listname+"\n";
		String d = "";
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getListname()!= null && task.get(i).getListname().length()!= 0)
			{
				if(task.get(i).getTaskname()!= null && task.get(i).getTaskname().length()!= 0)
				{
					if(task.get(i).getListname().equals(listname))
					{
						String b =task.get(i).getPriority().toString();
						String c = " | " + task.get(i).getTaskname();
						if (task.get(i).getUsername()==null)
							d = " | Unassigned";
						else
							d = " | " + task.get(i).getUsername();
						String e = " | " + task.get(i).getEstimatedtime().toString()+ " h";
						list1.add(b+c+d+e+"\n");
				}
				
				}
			}
		}
		for(int j=0;j<list1.size();j++) {
			results += list1.get(j);
		}
		results = a + results;		
		return results;
	}
	public String printAllLists()
	{
		String results="";
		for(int i=0;i<lists.size();i++)
		{
			 results +=printList(lists.get(i).getListname());
		}
		return results;
	}
	public String printUserTasks(String username)
	{
		String results= "";
		List<String> usertasklist=new ArrayList<String>();
		if(!checkUser(username)) {
			results="username does not exits";
		}
		for(int i=0;i<task.size();i++)
		{
			if(task.get(i).getUsername()!= null && task.get(i).getUsername().length()!= 0)
			{
				if(task.get(i).getUsername().equals(username))
				{
					String a =  task.get(i).getPriority().toString() ;
					String b = " | " + task.get(i).getTaskname();
					String c = " | " + task.get(i).getUsername();
					String d = " | " + task.get(i).getEstimatedtime().toString() + " h";
					usertasklist.add(a+b+c+d+"\n");
				}
			}	
			for(int j=0;j<usertasklist.size();j++) {
				results = usertasklist.get(j);
			}
		}
		return results;
		
	}
	public void printtext() throws IOException
	{
		File f=new File("out.txt");
	    f.createNewFile();
	    FileOutputStream fileOutputStream = new FileOutputStream(f);
	    PrintStream printStream = new PrintStream(fileOutputStream);
	    System.setOut(printStream);
	}
	private static final String INSERT_QUERY = "INSERT INTO Users(USERNAME) VALUES (?)";

	
	public int insert(Connection connection) {
		try {
			for(User use: user) {
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
				preparedStatement.setString(1,use.getUser_name());
				preparedStatement.execute();
				preparedStatement.close();
			}
		} catch (Exception e) {
			//throw a custom exception
			e.printStackTrace();
		}
		return 0;

	}


}
