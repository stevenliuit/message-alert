package com.message.alert.service;

import com.message.alert.entity.TestEntity;
import com.message.alert.utils.JsonResponse;

public interface TestService {
    void queryTest(JsonResponse jsonResponse, TestEntity testEntity);
}
