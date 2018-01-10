package com.serverless.dao;

import java.util.List;
import java.util.Optional;

import com.serverless.domain.UserInfo;

public interface UserInfoDAO {

	List<UserInfo> findAllEvents();

	Optional<UserInfo> findUserByName(String name);

	void saveOrUpdateUser(UserInfo event);

	void deleteUser(String name);

}
