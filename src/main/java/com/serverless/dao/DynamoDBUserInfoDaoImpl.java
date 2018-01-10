// Copyright 2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License"). You may
// not use this file except in compliance with the License. A copy of the
// License is located at
//
//	  http://aws.amazon.com/apache2.0/
//
// or in the "license" file accompanying this file. This file is distributed
// on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language governing
// permissions and limitations under the License.


package com.serverless.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.serverless.domain.UserInfo;
import com.serverless.manager.DynamoDBManager;
import java.util.*;




public class DynamoDBUserInfoDaoImpl implements UserInfoDAO {
    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

    private static volatile DynamoDBUserInfoDaoImpl instance;


    private DynamoDBUserInfoDaoImpl() { }

    public static DynamoDBUserInfoDaoImpl instance() {

        if (instance == null) {
            synchronized(DynamoDBUserInfoDaoImpl.class) {
                if (instance == null)
                    instance = new DynamoDBUserInfoDaoImpl();
            }
        }
        return instance;
    }

    @Override
    public List<UserInfo> findAllEvents() {
        return mapper.scan(UserInfo.class, new DynamoDBScanExpression());
    }

   
	@Override
	public Optional<UserInfo> findUserByName(String name) {

		DynamoDBQueryExpression<UserInfo> userQuery = new DynamoDBQueryExpression<>();
		UserInfo user = new UserInfo();
		user.setName(name);
		userQuery.setHashKeyValues(user);
		
		UserInfo usersFound = mapper.load(user);
		 
		return Optional.ofNullable(usersFound);
	}

   

    @Override
    public void saveOrUpdateUser(UserInfo user) {
        mapper.save(user);
    }

    @Override
    public void deleteUser(String name) {

        Optional<UserInfo> oEvent = findUserByName(name);
        if (oEvent.isPresent()) {
            mapper.delete(oEvent.get());
        }
        else {
          //  log.error("Could not delete event, no such team and date combination");
            throw new IllegalArgumentException("Delete failed for nonexistent event");
        }
    }
}
