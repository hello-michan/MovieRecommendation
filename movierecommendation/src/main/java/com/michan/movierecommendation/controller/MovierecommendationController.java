package com.michan.movierecommendation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		Movies movieDetail = new Movies();
		movieDetail.getMovies();
	}
	@GetMapping("/movieDetail")
	public void getMovieDetail(@RequestParam(value = "mov_id") int mov_id) {
		Movies movieDetail = new Movies();
		String tableName="mn_movies";
		String key="mov_id";
		String keyVal="2";
		movieDetail.getDetail(tableName,key,keyVal);
	}
}
