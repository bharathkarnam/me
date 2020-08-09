package com.data.dao.service.mongo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.data.dao.service.mongo.common.CSVHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.data.dao.service.mongo.common.Constants;
import com.data.dao.service.mongo.service.TransactionService;
import com.google.gson.Gson;

import io.swagger.annotations.Api;

@RestController
@Api(tags = Constants.SWAGGER_TAGS, value = Constants.SWAGGER_VALUE)
@RequestMapping("/api")
public class MainController {

	@Autowired
	TransactionService service;
	private final Logger logger = LoggerFactory.getLogger(MainController.class);

	Gson responseJson = new Gson();

	@PostMapping(path = Constants.POST_CSV, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> insertCSVDetails(@RequestParam("file") MultipartFile file) {
		try {
			if(service.insertCSV(file)) {
				logger.info(this.getClass()+":"+Constants.ALL_GOOD);
				return new ResponseEntity<String>(responseJson.toJson(Constants.ALL_GOOD), HttpStatus.ACCEPTED);
			}
			logger.error(this.getClass()+":"+Constants.SOMETHING_IS_NOT_RIGHT);
			return new ResponseEntity<String>(responseJson.toJson(Constants.SOMETHING_IS_NOT_RIGHT), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(this.getClass()+":"+Constants.SOMETHING_IS_NOT_RIGHT+":"+e.getStackTrace());
			return new ResponseEntity<String>(responseJson.toJson(Constants.SOMETHING_IS_NOT_RIGHT),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(path = Constants.GET_TRANSACTIONS, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> getCalculations(@RequestBody String accoundId, @RequestParam("From Date") String fromDate,@RequestParam("To Date") String toDate) {
		System.out.println(accoundId+""+fromDate+""+toDate);
		DateFormat dateFormatter = new SimpleDateFormat(Constants.DD_MM_YYYY_HH_MM_SS);
		Date startDate,endDate;
		try {
		startDate =  dateFormatter.parse(fromDate);
		endDate = dateFormatter.parse(toDate);		
			if (null == accoundId || accoundId.isEmpty()) {
				logger.error(this.getClass()+":"+Constants.SOMETHING_IS_NOT_RIGHT);
				return new ResponseEntity<String>(responseJson.toJson(Constants.SOMETHING_IS_NOT_RIGHT),
						HttpStatus.BAD_REQUEST);
			}
			logger.info(this.getClass()+":"+Constants.ALL_GOOD);
			return new ResponseEntity<String>(responseJson.toJson("Balance:"+service.calculateTransaction(accoundId,startDate,endDate)), HttpStatus.OK);
		} catch (Exception e) {
			logger.error(this.getClass()+":"+Constants.SOMETHING_IS_NOT_RIGHT+":"+e.getStackTrace());
			return new ResponseEntity<String>(responseJson.toJson(Constants.SOMETHING_IS_NOT_RIGHT),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping(path = Constants.GETLOCALTIME, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> getTimezone() {

			return new ResponseEntity<String>(responseJson.toJson(Locale.getDefault()), HttpStatus.OK);
		
	}

}
