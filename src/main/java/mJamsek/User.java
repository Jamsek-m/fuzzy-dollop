package mJamsek;

public class User {
	private int id;
	private String name;
	private String email;

	public User(int i, String n, String e) {
		this.id = i;
		this.email = e;
		this.name = n;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}