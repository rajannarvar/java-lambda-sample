package com.serverless;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;


import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.domain.UserInfo;
import com.serverless.function.UserServiceImpl;

public class AddUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	
	private static final UserServiceImpl userServiceImplInstance = UserServiceImpl.instance();

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context c) {
		LambdaLogger logger= c.getLogger();
		logger.log("Add User handler body: " + input.get("body"));

		ObjectMapper objectMapper = new ObjectMapper();
		UserInfo userInfo = null;
		try {
			userInfo = objectMapper.readValue(input.get("body").toString(), UserInfo.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		userServiceImplInstance.saveOrUpdateEvent(userInfo);

		return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(input)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
	}

}
