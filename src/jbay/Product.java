package jbay;

public class Product {
	private String title = "";
	private String description = "";

	public Product(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
}
