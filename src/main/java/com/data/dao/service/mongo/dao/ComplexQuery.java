package com.data.dao.service.mongo.dao;

import java.util.Date;
import java.util.List;

import com.data.dao.service.mongo.model.Transaction;


public interface ComplexQuery {
    double getData(String fromAccountId,Date createDateAfter,Date createDateBefore);

	boolean deleteAll();

	boolean saveAll(List<Transaction> transactions);

}
