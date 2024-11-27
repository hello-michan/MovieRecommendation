package com.michan.movierecommendation.model;

import java.util.List;

public class Movie {
	private List<String> mov_category;
	private String mov_description;
	private String mov_rating;
	private String mov_releaseday;

	public Movie() {
	}

	public List<String> getMov_category() {
		return mov_category;
	}

	public void setMov_category(List<String> mov_category) {
		this.mov_category = mov_category;
	}

	public String getMov_description() {
		return mov_description;
	}

	public void setMov_description(String mov_description) {
		this.mov_description = mov_description;
	}

	public String getMov_rating() {
		return mov_rating;
	}

	public void setMov_rating(String mov_rating) {
		this.mov_rating = mov_rating;
	}

	public String getMov_releaseday() {
		return mov_releaseday;
	}

	public void setMov_releaseday(String mov_releaseday) {
		this.mov_releaseday = mov_releaseday;
	}

	@Override
	public String toString() {
		return "Movie [mov_category=" + mov_category + ", mov_description=" + mov_description + ", mov_rating="
				+ mov_rating + ", mov_releaseday=" + mov_releaseday + "]";
	}

}
