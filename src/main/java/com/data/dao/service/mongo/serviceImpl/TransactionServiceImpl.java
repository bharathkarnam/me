package com.data.dao.service.mongo.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.data.dao.service.mongo.common.CSVHelper;
import com.data.dao.service.mongo.dao.ComplexQuery;
import com.data.dao.service.mongo.model.Transaction;
import com.data.dao.service.mongo.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
	
	@Autowired
	private ComplexQuery complexquery;

	CSVHelper csvHelper = new CSVHelper();

	public boolean insertCSV(MultipartFile file) {
		try {
			List<Transaction> transactions = csvHelper.csvToDB(file.getInputStream());
			complexquery.deleteAll();
			boolean result = complexquery.saveAll(transactions);
			return result;
		} catch (Exception e) {
			logger.error(this.getClass()+":" + Arrays.toString(e.getStackTrace()));
			return false;
		}
	}


	public double calculateTransaction(String uniqId,Date fromdate,Date todate) {
		try {	
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormatter.setTimeZone(TimeZone.getDefault());
			double usrList= complexquery.getData(uniqId,fromdate,todate);
			return usrList;
		} catch (Exception e) {
			logger.error(this.getClass()+":" + Arrays.toString(e.getStackTrace()));
			return 0;
		}
	}


}