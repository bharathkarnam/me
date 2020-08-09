package com.data.dao.service;

import com.data.dao.service.mongo.daoimpl.ComplexQueryImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.data.dao.service.mongo.controller.MainControllerTest;
import com.data.dao.service.mongo.serviceImpl.TransactionServiceImplTest;

@SpringBootTest
@RunWith(Suite.class)
@ActiveProfiles("test")
@SuiteClasses({MainControllerTest.class, TransactionServiceImplTest.class, ComplexQueryImplTest.class})
public class TestSuite {

}
