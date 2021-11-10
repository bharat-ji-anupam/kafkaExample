package com.consumerService.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.consumerService.model.GpxLocationData;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationDeserializer implements Deserializer<GpxLocationData> {

    @Override 
    public void configure(Map<String, ?> arg0, boolean arg1) {
    	
    }
    @Override
    public GpxLocationData deserialize(String topic, byte[] arg1) {
    	ObjectMapper mapper = new ObjectMapper();
    	GpxLocationData locationData = null;
        try {
        	locationData = mapper.readValue(arg1, GpxLocationData.class);
        } catch (Exception e) {

          e.printStackTrace();
        }
        return locationData;
    }

    @Override
    public void close() {
        // nothing to do
    }
}
