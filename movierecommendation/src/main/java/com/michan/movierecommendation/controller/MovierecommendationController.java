package com.michan.movierecommendation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.michan.movierecommendation.model.Movie;
import com.michan.movierecommendation.service.Movies;

@RestController
public class MovierecommendationController {
	@PostMapping("/checkmark")
	public void checkMark(@RequestParam(value = "mov_id") String mov_id) {

	}

	@GetMapping("/allCategories")
	public void categories() {

	}

	@GetMapping("/movies")
	public void getMovies() {
	}

	@GetMapping("/movieDetail")
	public Movie getMovieDetail(@RequestParam(value = "mov_id") int mov_id,
			@RequestParam(value = "mov_name") String mov_name) {
		Movies movieDetail = new Movies();
		return movieDetail.getDetail(""+mov_id, mov_name);
	}
}
