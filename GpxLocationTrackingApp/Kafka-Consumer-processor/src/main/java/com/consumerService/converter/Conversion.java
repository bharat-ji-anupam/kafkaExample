package com.consumerService.converter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.consumerService.model.GpxLocationData;

@Service
public class Conversion {

	private GpxLocationData prevGpxLocationData;
	
	public GpxLocationData getLocationData(String data) {
		GpxLocationData locationData = new GpxLocationData();

		try {
			List<String> trackingDataPoints = Arrays.asList(data.split(","));

			Double latitude = Double.valueOf(trackingDataPoints.get(0).trim());
			Double longitude = Double.valueOf(trackingDataPoints.get(1).trim());
			long time = convertTimeToMillis(trackingDataPoints.get(2).trim());

			locationData.setLatitude(latitude);
			locationData.setLongitude(longitude);
			locationData.setTime(time);

		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
		}
		return locationData;
	}

	
	public long calculateTravelTime(GpxLocationData locationData, GpxLocationData prevLocationData) {
		long timeDifference = locationData.getTime() - prevLocationData.getTime();
		timeDifference = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
		return timeDifference;

	}

	public double calculateDistance(GpxLocationData locationData, GpxLocationData prevLocationData) {
		double lon1 = Math.toRadians(locationData.getLongitude());
		double lon2 = Math.toRadians(prevLocationData.getLongitude());
		double lat1 = Math.toRadians(locationData.getLatitude());
		double lat2 = Math.toRadians(prevLocationData.getLatitude());

		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		double c = 2 * Math.asin(Math.sqrt(a));

		double r = 6371;

		return (c * r);
	}

	public long convertTimeToMillis(String dateTime) {
		long timeInMillis = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = sdf.parse(dateTime);
			timeInMillis = date.getTime();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return timeInMillis;
	}
	
	public GpxLocationData convertTrackingData(String data) {
		GpxLocationData locationData = getLocationData(data);

		if (prevGpxLocationData == null) {
			locationData.setDistanceTracker(0);
			locationData.setTimeTracker(0);
		} else {
			long travelTime = calculateTravelTime(locationData, prevGpxLocationData);
			double distanceCovered = calculateDistance(locationData, prevGpxLocationData);
			locationData.setDistanceTracker(distanceCovered);
			locationData.setTimeTracker(travelTime);
		}

		prevGpxLocationData = locationData;
		return locationData;

	}


	
}