package com.bridgeit.springToDoApp.RedisToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.bridgeit.springToDoApp.model.Token;
import com.bridgeit.springToDoApp.model.User;

public class RedisTokenImpl implements RedisToken {
	
	private static final String KEY = "User";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, String, Token> hashOperations;
	
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public void saveUserToken(User user, Token token) {
		hashOperations = redisTemplate.opsForHash();
		hashOperations.put(KEY,  String.valueOf(user.getId()), token);
	}

	@Override
	public Token getToken(User user) {
		hashOperations = redisTemplate.opsForHash();
		return hashOperations.get(KEY, String.valueOf(user.getId()));
	}
	
	
}
