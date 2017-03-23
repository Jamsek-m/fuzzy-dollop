package mJamsek;
public class DB_Projekti {
	private String id;
	private String name;
	private String desk;
	
	public DB_Projekti(String i, String n, String d){
		this.id = i;
		this.setName(n);
		this.setDesk(d);
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesk() {
		return desk;
	}

	public void setDesk(String desk) {
		this.desk = desk;
	}
	
}

