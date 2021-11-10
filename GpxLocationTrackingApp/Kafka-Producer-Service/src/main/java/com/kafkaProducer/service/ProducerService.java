package com.kafkaProducer.service;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

	public static final String FILEPATH = "/testdata/cycle_gpx (2).csv";
	public static final String TOPIC = "topicCal";		
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@PostConstruct
	public void sendToTopic() {
		try {
			String filePath = System.getProperty("user.dir")+FILEPATH;
					
			BufferedReader br = new BufferedReader(new FileReader(filePath));
				String row;
				while((row = br.readLine()) != null) {
					this.kafkaTemplate.send(TOPIC,row);
					System.out.println("Current_ROW: "+row);
					Thread.sleep(100);
				}
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
