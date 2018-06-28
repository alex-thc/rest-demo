package com.example.demo.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class DataAccessService {

	@Autowired
	private MongoTemplate mongoTemplate;
		
	public Document findOneByFieldEqString(String colName, String fieldName, String val) {
		Query query = new Query();
		query.addCriteria(Criteria.where(fieldName).is(val));
		
		return mongoTemplate.findOne(query, Document.class, colName);
	}
	
	public void insertOne(String colName, Document doc) {
		mongoTemplate.insert(doc, colName);
	}
}
