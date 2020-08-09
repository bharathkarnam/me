package com.data.dao.service.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.data.dao.service.mongo.model.Transaction;

public class Common {
	public static Transaction buildUserDetails() {
		try {
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Transaction transaction = new Transaction("TX10001", "ACC334455", "ACC778899", dateFormatter.parse("20/10/2018 12:47:55"), 25.00, "PAYMENT", "");			
		return transaction;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	public static MockMultipartFile getfile() {
		 return new MockMultipartFile(
		            "file",
		            "myfile.csv",
		            "application/x-octet-stream",
		            "transactionId, fromAccountId, toAccountId, createdAt, amount, transactionType, relatedTransaction TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT".getBytes());

	}
}