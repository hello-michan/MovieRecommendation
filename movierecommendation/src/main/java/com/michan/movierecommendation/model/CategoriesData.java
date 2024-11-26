package com.michan.movierecommendation.model;

import java.util.List;


public class CategoriesData {
	List<String> categories;
	
	public CategoriesData(String category) {
		this.categories.add(category);
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
}
