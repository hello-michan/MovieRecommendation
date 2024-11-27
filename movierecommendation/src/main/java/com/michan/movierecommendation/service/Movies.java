package com.michan.movierecommendation.service;

import java.util.Map;

import com.michan.movierecommendation.dao.MoviesDAO;
import com.michan.movierecommendation.model.Movie;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class Movies {
	private static final Region REGION = Region.US_EAST_2;

	public Movie getDetail(String mov_id, String mov_name) {
		DynamoDbClient ddb = DynamoDbClient.builder().region(REGION).build();
		MoviesDAO moviesDao = new MoviesDAO();
		Movie movie = new Movie();
		movie = moviesDao.getDynamoDBItem(ddb, mov_id, mov_name);
		ddb.close();
		return movie;
	}

	public void getMovies() {
	}

	public void watchedFlag() {
	}
}
