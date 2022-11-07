package com.message.alert.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.message.alert.entity.TestEntity;
import com.message.alert.service.TestService;
import com.message.alert.utils.JsonResponse;

@Service
public class TestServiceImpl implements TestService {

    private final Logger logger =  LoggerFactory.getLogger(TestServiceImpl.class);

	@Override
	public void queryTest(JsonResponse jsonResponse, TestEntity testEntity) {
		// TODO Auto-generated method stub
		
	}

}
