package com.data.dao.service.mongo.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.data.dao.service.mongo.model.Transaction;



public interface TransactionService {

	boolean insertCSV(MultipartFile pa);
	
	double calculateTransaction(String id,Date startdate,Date enddate);
}
