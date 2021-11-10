package com.consumerService.serialization;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.consumerService.model.GpxLocationData;

public final class SerdeFactory {

	static public final class LocationSerde extends Serdes.WrapperSerde<GpxLocationData> {
		public LocationSerde() {
			super(new LocationSerializer(), new LocationDeserializer());
		}
	}

	public static Serde<GpxLocationData> TrackLocation() {
		return new SerdeFactory.LocationSerde();
	}
}
