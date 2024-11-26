package com.michan.movierecommendation.service;

import com.michan.movierecommendation.dao.MoviesDAO;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class Movies {
	private static final Region REGION = Region.US_EAST_2;

	public void getDetail(String mov_id, String mov_name) {
		DynamoDbClient ddb = DynamoDbClient.builder().region(REGION).build();
		MoviesDAO moviesDao = new MoviesDAO();
		moviesDao.getDynamoDBItem(ddb, mov_id, mov_name);
		ddb.close();
	}

	public void getMovies() {
	}

	public void watchedFlag() {
	}
}
