
package com.serverless.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import java.io.Serializable;

@DynamoDBTable(tableName = "user_info")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -8243145429438016232L;
	public static final String CITY_INDEX = "City-Index";
	public static final String AWAY_TEAM_INDEX = "AwayTeam-Index";

	@DynamoDBHashKey
	private String name;

	@DynamoDBAttribute
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
