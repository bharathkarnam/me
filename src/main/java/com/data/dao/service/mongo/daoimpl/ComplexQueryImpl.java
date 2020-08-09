package com.data.dao.service.mongo.daoimpl;


import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.print.Doc;

import com.data.dao.service.mongo.common.Constants;
import com.data.dao.service.mongo.serviceImpl.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.data.dao.service.mongo.dao.ComplexQuery;
import com.data.dao.service.mongo.model.Transaction;

@Service
public class ComplexQueryImpl implements ComplexQuery {

	public static final String PAYMENT = "PAYMENT";
	public static final String TRANSACTION_TYPE = "transactionType";
	public static final String CREATEDAT = "createdat";
	public static final String TO_ACCOUNT_ID = "toAccountId";
	public static final String FROM_ACCOUNT_ID = "fromAccountId";
	public static final String PATTERN = "#.##";
	@Autowired
	private MongoTemplate mongoTemplate;

	private final Logger logger = LoggerFactory.getLogger(ComplexQueryImpl.class);

	@Override
	public double getData(String fromAccountId, Date createDateAfter, Date createDateBefore) {
		Query querypayment = new Query();
		double amount =0.00;
		DecimalFormat df = new DecimalFormat(PATTERN);
		Criteria criteriaV1 = Criteria.where(TO_ACCOUNT_ID).is(fromAccountId).and(CREATEDAT).gte(createDateAfter).lte(createDateBefore).and(TRANSACTION_TYPE).is(PAYMENT);
		Criteria criteriaV2 = Criteria.where(FROM_ACCOUNT_ID).is(fromAccountId).and(CREATEDAT).gte(createDateAfter).lte(createDateBefore).and(TRANSACTION_TYPE).is(PAYMENT);
		try{
		querypayment.addCriteria(new Criteria().orOperator(criteriaV1, criteriaV2));
		  List<Transaction> trsList=  mongoTemplate.find(querypayment, Transaction.class);

		  for(Transaction transaction : trsList){
		  	 if(transaction.getToAccountId().equalsIgnoreCase(fromAccountId)) {
				 amount = amount + transaction.getAmount();
			 } else {
				 amount = amount - transaction.getAmount();
			 }
		  }} catch(Exception e){
			logger.error(this.getClass()+":"+Constants.SOMETHING_IS_NOT_RIGHT+":"+ Arrays.toString(e.getStackTrace()));
		}
          return Double.parseDouble(df.format(amount));
	}

	@Override
	public boolean deleteAll() {
		mongoTemplate.dropCollection(Constants.COLLECTION);
		return true;
	}

	@Override
	public boolean saveAll(List<Transaction> transactions) {
		// TODO Auto-generated method stub
		try {
			Collection<Transaction> trans = mongoTemplate.insertAll(transactions);
			if (null == trans) {
				return false;
			}
			return true;
		} catch(Exception e){
			logger.error(this.getClass()+":"+ Constants.SOMETHING_IS_NOT_RIGHT+":"+ Arrays.toString(e.getStackTrace()));
			return false;
		}
	}
}
