package com.michan.movierecommendation.service;

import java.util.ArrayList;
import java.util.List;

import com.michan.movierecommendation.dao.MoviesDAO;
import com.michan.movierecommendation.model.Movie;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class Movies {
	private static final Region REGION = Region.US_EAST_2;
	private static final int MOVIENUM = 3;

	public Movie getDetail(String mov_id, String mov_name) {
		DynamoDbClient ddb = DynamoDbClient.builder().region(REGION).build();
		MoviesDAO moviesDao = new MoviesDAO();
		Movie movie = new Movie();
		movie = moviesDao.getDynamoDBItem(ddb, mov_id, mov_name);
		ddb.close();
		return movie;
	}

	public List<Movie> getMovies(String mov_category) {
		DynamoDbClient ddb = DynamoDbClient.builder().region(REGION).build();
		MoviesDAO moviesDao = new MoviesDAO();
		List<Movie> res = new ArrayList<>();
		createRandomNum(moviesDao.getDynamoDBItemsByCategory(ddb,mov_category),res);
		return res;
		
	}
	
	private void createRandomNum(List<Movie> arrMov, List<Movie> res) {
		int[] randomID = new int[MOVIENUM];
		int i=0;
		while(i<randomID.length) {
			int temp = (int)(Math.random()*arrMov.size());
			if(!checkNum(temp,randomID)) {
				randomID[i] = temp;
				res.add(arrMov.get(temp));
				i++;
			}
		}
	}
	private boolean checkNum(int temp, int[] randomID) {
		boolean flag = false;
		for(int num: randomID) {
			if(num==temp) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public void watchedFlag(String mov_id, String mov_name, boolean updateVal) {
		DynamoDbClient ddb = DynamoDbClient.builder().region(REGION).build();
		MoviesDAO moviesDao = new MoviesDAO();
		moviesDao.putDynamoDBItemByWatched(ddb, mov_id, mov_name, updateVal);
	}
	
}
