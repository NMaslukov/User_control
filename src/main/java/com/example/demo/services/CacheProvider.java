package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheProvider {
	public static final String VAL_PERSON = "person";
	public static final String KEY_ID = "#id";
	public static final String KEY_P_ID = "#p.id";
	
	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager concurrentMapCacheManager = new ConcurrentMapCacheManager(VAL_PERSON);
		List<String> cacheNames = new ArrayList<>();
		cacheNames.add(VAL_PERSON);
		concurrentMapCacheManager.setCacheNames(cacheNames);
		return concurrentMapCacheManager;
	}
}
