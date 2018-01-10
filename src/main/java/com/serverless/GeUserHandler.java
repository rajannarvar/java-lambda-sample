package com.serverless;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;



import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.dao.DynamoDBUserInfoDaoImpl;
import com.serverless.domain.UserInfo;
import com.serverless.function.UserServiceImpl;
import com.serverless.manager.DynamoDBManager;

public class GeUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	//private static final Logger LOG = Logger.ge
	private static final UserServiceImpl userServiceImplInstance = UserServiceImpl.instance();

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		//BasicConfigurator.configure();

		System.out.println("received: " + input);
		UserInfo user = null;

		try {
			user = userServiceImplInstance.getUserByName(String.valueOf(input.get("name")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(user)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
	}
}
