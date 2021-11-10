package com.consumerService.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GpxTrackingData {

	@JsonProperty("timeTracker")
	private long totalTime;
	
	@JsonProperty("distanceTracker")
	private double totalDistance;
		
    public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public GpxTrackingData addTotalTime(long time) {
        this.totalTime = time;
        return this;
    }
    
    public GpxTrackingData addTotalDistance(double distance) {
        this.totalDistance = distance;
        return this;
    }
    
	 @Override 
	 public String toString() {
		   return "Total time : " + totalTime + " sec, Total Distance : " + totalDistance+" km" ;
	  }
}
