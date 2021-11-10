package com.consumerService.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.consumerService.model.GpxLocationData;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationSerializer implements Serializer<GpxLocationData> {

	 @Override
	  public void close() {

	  }

	  @Override
	  public void configure(Map<String, ?> arg0, boolean arg1) {

	  }

	  @Override
	  public byte[] serialize(String arg0, GpxLocationData arg1) {
	    byte[] retVal = null;
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	      retVal = objectMapper.writeValueAsString(arg1).getBytes();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return retVal;
	  }

}