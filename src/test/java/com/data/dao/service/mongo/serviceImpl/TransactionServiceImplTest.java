package com.data.dao.service.mongo.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.data.dao.service.mongo.daoimpl.ComplexQueryImpl;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.ServerAddress;
import org.assertj.core.internal.InputStreamsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.data.dao.service.common.Common;
import com.data.dao.service.mongo.common.CSVHelper;
import com.data.dao.service.mongo.model.Transaction;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

	@InjectMocks
	TransactionServiceImpl transactionService;

	@Mock
	ComplexQueryImpl complexQuery;
	
	CSVHelper csvHelper = Mockito.mock(CSVHelper.class);
	
	MockHttpServletRequest request = new MockHttpServletRequest();

	Transaction tr = Common.buildUserDetails();
	List<Transaction> trs = new ArrayList<Transaction>();
	MockMultipartFile mf = Common.getfile();
	DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


	@Test
	public void testInsertUser() throws Exception {
		trs.add(tr);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(csvHelper.csvToDB(any())).thenReturn(trs);
		when(complexQuery.deleteAll()).thenReturn(true);
		when(complexQuery.saveAll(trs)).thenReturn(true);
		boolean responseEntity = transactionService.insertCSV(mf);
		System.out.println(responseEntity);
		assertThat(responseEntity).isTrue();
	}

	@Test
		public void testCheckForerror() throws Exception {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
			when(csvHelper.csvToDB(any())).thenReturn(trs);
			when(complexQuery.deleteAll()).thenReturn(true);
			when(complexQuery.saveAll(trs)).thenThrow(new MongoSocketOpenException("Connection closed",new ServerAddress()));
		boolean responseEntity = transactionService.insertCSV(mf);
		assertThat(responseEntity).isEqualTo(false);
	}

	@Test
	public void testCheckForerrorcsvdb() throws Exception {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(csvHelper.csvToDB(any())).thenThrow(new InputStreamsException(""));
		boolean responseEntity = transactionService.insertCSV(mf);
		assertThat(responseEntity).isEqualTo(false);
	}


	@Test
	public void testgetcal() throws Exception {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(complexQuery.getData(any(String.class), any(Date.class), any(Date.class))).thenReturn((-tr.getAmount()));
		double responseEntity = transactionService.calculateTransaction(tr.getToAccountId(), dateFormatter.parse("20/10/2018 12:47:55"), dateFormatter.parse("20/10/2018 13:47:55"));
		assertThat(responseEntity).isEqualTo((-tr.getAmount()));
	}
	
	@Test
	public void testgetcalexception() throws Exception {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(complexQuery.getData(any(String.class), any(Date.class), any(Date.class))).thenThrow(new MongoSocketOpenException("Connection closed",new ServerAddress()));
		double responseEntity = transactionService.calculateTransaction(tr.getToAccountId(), dateFormatter.parse("20/10/2018 12:47:55"), dateFormatter.parse("20/10/2018 13:47:55"));
		assertThat(responseEntity).isEqualTo(0);
	}
	

}