package project.epita.trello;

public class Task {
	private String username;
	private String listname;
	private String taskname;
	private Integer estimatedtime;
	private Integer priority;
	private String description;
	private boolean completity;
	public Task(String listname, String taskname, int estimatedtime, int priority, String description) {
		
		this.listname=listname;
		this.taskname=taskname;
		this.estimatedtime=estimatedtime;
		this.priority=priority;
		this.description=description;
		this.username=null;
		this.setCompletity(false);
		
		
	}
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}
	public String getListname() {
		return listname;
	}
	public void setListname(String listname) {
		this.listname = listname;
	}

	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public Integer getEstimatedtime() {
		return estimatedtime;
	}
	public void setEstimatedtime(Integer estimatedtime) {
		this.estimatedtime = estimatedtime;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompletity() {
		return completity;
	}

	public void setCompletity(boolean completity) {
		this.completity = completity;
	}
	

}
