package com.consumerService.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GpxLocationData {

	@JsonProperty("timeTracker")
	private long timeTracker;
	
	@JsonProperty("distanceTracker")
	private double distanceTracker;
	
	@JsonProperty("latitude")
	private double latitude;
	
	@JsonProperty("longitude")
	private double longitude;
	
	@JsonProperty("time")
	private long time;
	
	
	public long getTimeTracker() {
		return timeTracker;
	}

	public void setTimeTracker(long timeTracker) {
		this.timeTracker = timeTracker;
	}

	public double getDistanceTracker() {
		return distanceTracker;
	}

	public void setDistanceTracker(double distanceTracker) {
		this.distanceTracker = distanceTracker;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	 @Override public String toString() {
		   return "Location(" + timeTracker + ", " + distanceTracker + ")";
		 }

	public void update(GpxLocationData latestLocationData) {
		this.distanceTracker = latestLocationData.getDistanceTracker();
		this.timeTracker = latestLocationData.getTimeTracker();
	}
	
	public GpxLocationData withTotalTime(long time) {
        this.timeTracker = time;
        return this;
    }
    
    public GpxLocationData withTotalDistance(double distance) {
        this.distanceTracker = distance;
        return this;
    }
}
