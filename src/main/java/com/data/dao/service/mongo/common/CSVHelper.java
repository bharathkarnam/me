package com.data.dao.service.mongo.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.data.dao.service.mongo.serviceImpl.TransactionServiceImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.data.dao.service.mongo.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVHelper {
	public static final String FAIL_TO_PARSE_CSV_FILE = "fail to parse CSV file";
	private final Logger logger = LoggerFactory.getLogger(CSVHelper.class);

	public List<Transaction> csvToDB(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<Transaction> csvobject =new ArrayList<>();
			
			DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			String relatedString = "";
			String transactionId ="";
			Map<String, Transaction> releatedtransaction = new HashMap<>();
			for (CSVRecord csvRecord : csvRecords) {
				
				try {
					 try {
						 transactionId = csvRecord.get("transactionId");
						 relatedString = csvRecord.get("relatedTransaction");
						 relatedString = relatedString.substring(0, relatedString.indexOf(" ")).trim();
					 } catch (Exception e) {
						 relatedString ="";
					}
					 if((null != relatedString || relatedString != "") && releatedtransaction.containsKey(relatedString)) {
						 releatedtransaction.remove(relatedString);
					 } else{
					 	Transaction tutorial = new Transaction(transactionId, csvRecord.get("fromAccountId"),
							 csvRecord.get("toAccountId"), dateFormatter.parse(csvRecord.get("createdAt")),
							 Float.parseFloat(csvRecord.get("amount")), csvRecord.get("transactionType"),
							 relatedString);
						 releatedtransaction.put(transactionId, tutorial);
					 }
				} catch(Exception e) {
					logger.debug(e.getClass()+" "+e.getStackTrace().toString());
				}
			}
			for (Transaction tab : releatedtransaction.values()) {
				csvobject.add(tab);
			}
			return csvobject;
		} catch (IOException e) {
			logger.debug(e.getClass()+" "+ Arrays.toString(e.getStackTrace()));
			throw new RuntimeException(FAIL_TO_PARSE_CSV_FILE + ": " + e.getMessage());
		}
	}
}
