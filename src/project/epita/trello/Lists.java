package project.epita.trello;


public class Lists {
	private String listname;
	
	public String getListname() {
		return listname;
	}
	public void setListname(String listname) {
		this.listname = listname;
	}

	public Lists(String listname) {
		super();
		this.listname=listname;
	}
}

