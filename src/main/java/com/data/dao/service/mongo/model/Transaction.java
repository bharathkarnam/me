package com.data.dao.service.mongo.model;

import java.text.DecimalFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.data.dao.service.mongo.common.Constants;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Constants.COLLECTION)
public class Transaction {
	private static DecimalFormat df = new DecimalFormat("#.##");
	@Length(min=2)
	private String fromAccountId;
	public String getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}

	public String getRelatedTransaction() {
		return relatedTransaction;
	}

	public void setRelatedTransaction(String relatedTransaction) {
		this.relatedTransaction = relatedTransaction;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	
	
	public Transaction(@NotNull String transactionId,@Length(min = 2) String fromAccountId, @NotNull String toAccountId, @NotNull Date createdat,
			@NotNull double amount, @Length(min = 3) String transactionType, @Length(min = 3) String relatedTransaction) {
		super();
		this.fromAccountId = fromAccountId;
		this.transactionId = transactionId;
		this.createdat = createdat;
		this.amount = Double.parseDouble(df.format(amount));
		this.toAccountId = toAccountId;
		this.relatedTransaction = relatedTransaction;
		this.transactionType = transactionType;
	}

	@NotNull
	private String transactionId;
	@NotNull
	private Date createdat;
	@NotNull
	private double amount;
	@NotNull
	private String toAccountId;
	@Null
	private String relatedTransaction;
	@Length(min=3)
	private String transactionType;
	
	

	
}
