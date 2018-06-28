package com.example.demo.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.ApiController;
import com.example.demo.service.DataAccessService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiController {

	@Autowired
	DataAccessService dataAccessService;
	
	@RequestMapping("/api/findOneByFieldEqString")
	public ResponseEntity<Document> findOneByFieldEqString(
			@RequestParam("colName") String colName,
			@RequestParam("fieldName") String fieldName,
			@RequestParam("val") String val
			) {
		
		Document res;
		
		try {
			res = dataAccessService.findOneByFieldEqString(colName, fieldName, val);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (res == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return ResponseEntity.ok(res);
	}
	
	@RequestMapping(value="/api/insertOne", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> insertOne(
			@RequestBody Document doc
			) {
		
		String colName = doc.getString("__colName");
		if (colName == null)
		{
			log.error("Must pass the collection name as __colName");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		doc.remove("__colName");
		
		try {
			dataAccessService.insertOne(colName, doc);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok().build();
	}
}
