package com.serverless;

import java.util.Collections;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.domain.UserInfo;
import com.serverless.function.UserServiceImpl;
import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.LambdaLogger;
public class AddUserHandlerUsingPOJO implements RequestHandler<AddUserPOJO, ApiGatewayResponse> {

	//private static final Logger LOG = LogManager.getLogger(AddUserHandler.class);
	private static final UserServiceImpl userServiceImplInstance = UserServiceImpl.instance();

	@Override
	public ApiGatewayResponse handleRequest(AddUserPOJO input, Context c) {
		  LambdaLogger logger = c.getLogger();
		  logger.log("-->>>>>> AddUserHandlerUsingPOJO Add User handler body: " + input.getName());

		UserInfo userInfo = new UserInfo();
		userInfo.setName(input.getName());
		userInfo.setEmail(input.getEmail());
		userServiceImplInstance.saveOrUpdateEvent(userInfo);

		return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(input)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
	}

}
