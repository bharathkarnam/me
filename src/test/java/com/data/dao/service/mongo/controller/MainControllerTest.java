package com.data.dao.service.mongo.controller;

import com.data.dao.service.common.Common;
import com.data.dao.service.mongo.model.Transaction;
import com.data.dao.service.mongo.service.TransactionService;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.ServerAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.FileInputStream;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class MainControllerTest {

	@InjectMocks
	MainController maincontroller;

	@Mock
	TransactionService userservice;
	 
	MockHttpServletRequest request = new MockHttpServletRequest();

	
	Transaction transaction = Common.buildUserDetails();

	MockMultipartFile mf = Common.getfile();


	String statusok = "All good";

	@Test
	public void testAddCSV() {
		FileInputStream inputFile = null;
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(userservice.insertCSV(mf)).thenReturn(true);
		ResponseEntity<String> responseEntity = maincontroller.insertCSVDetails(mf);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
		assertThat(responseEntity.getBody().equalsIgnoreCase("All good"));
		
	}
	
	@Test
	public void testGetCalculations() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(userservice.calculateTransaction(transaction.getToAccountId(),new Date(), new Date())).thenReturn(transaction.getAmount());
		ResponseEntity<String> responseEntity = maincontroller.getCalculations(transaction.getFromAccountId(),"20/10/2018 12:47:55","20/10/2018 12:48:55");
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		assertThat(responseEntity.getBody().equalsIgnoreCase(transaction.getAmount()+"".trim()));
	}
	
	@Test
	public void testAddCSVnull() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(userservice.insertCSV(mf)).thenReturn(false);
		ResponseEntity<String> responseEntity = maincontroller.insertCSVDetails(null);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
	}
	
	@Test
	public void testAddCSVException() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(userservice.insertCSV(mf)).thenThrow(new MongoSocketOpenException("Connection closed",new ServerAddress()));
		ResponseEntity<String> responseEntity = maincontroller.insertCSVDetails(mf);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
	}
	
	@Test
	public void testGetCalculationnull() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(userservice.calculateTransaction(null,null, null)).thenReturn(0.00);
		ResponseEntity<String> responseEntity = maincontroller.getCalculations(null,null,null);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
	}
	
	@Test
	public void testGetCalculationexception() throws Exception {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(userservice.calculateTransaction(transaction.getToAccountId(),new Date(), new Date())).thenThrow(new MongoSocketOpenException("Connection closed",new ServerAddress()));
		ResponseEntity<String> responseEntity = maincontroller.getCalculations(transaction.getFromAccountId(),"20/10/2018 12:47:55","20/10/2018 12:48:55");
		assertThat(responseEntity.getBody().equalsIgnoreCase("0"));
	}

	
}