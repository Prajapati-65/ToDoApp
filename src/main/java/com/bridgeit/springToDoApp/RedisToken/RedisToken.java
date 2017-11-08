package com.bridgeit.springToDoApp.RedisToken;

import com.bridgeit.springToDoApp.model.Token;
import com.bridgeit.springToDoApp.model.User;

public interface RedisToken {

	void saveUserToken(User user, Token Token);

	Token getTokenFromUser(User user);
}
