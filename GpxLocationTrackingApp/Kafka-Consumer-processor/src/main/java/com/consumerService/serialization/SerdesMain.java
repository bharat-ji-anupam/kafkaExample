package com.consumerService.serialization;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes.WrapperSerde;

import com.consumerService.model.GpxLocationData;
import com.consumerService.model.GpxTrackingData;

public class SerdesMain {

	static final class LocationDataSerde extends WrapperSerde<GpxLocationData> {
    	LocationDataSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>());
        }
    }

    public static Serde<GpxLocationData> GpxLocationData() {
    	LocationDataSerde serde = new LocationDataSerde();

        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, GpxLocationData.class);
        serde.configure(serdeConfigs, false);

        return serde;
    }

    static final class TrackingDataSerde extends WrapperSerde<GpxTrackingData> {
    	TrackingDataSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>());
        }
    }

    public static Serde<GpxTrackingData> GpxTrackingData() {
    	TrackingDataSerde serde = new TrackingDataSerde();

        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, GpxTrackingData.class);
        serde.configure(serdeConfigs, false);

        return serde;
    }
}
