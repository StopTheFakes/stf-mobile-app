package user.com.stopthefakes.api;


public class Country {

	private String id;
	private String name;


	public Country(String id, String name) {
		this.id = id;
		this.name = name;
	}


	public Country(String id) {
		this.id = id;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return name;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Country) {
			Country c = (Country) obj;
			return c.getId().equals(id);
		}
		return false;
	}

}