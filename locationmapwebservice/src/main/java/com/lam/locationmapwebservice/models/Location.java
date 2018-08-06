package com.lam.locationmapwebservice.models;

public class Location {
	private float lat;
	private float lng;
	private boolean enabled;
	
	public Location(float lat, float lng, boolean enabled) {
		this.lat = lat;
		this.lng = lng;
		this.enabled = enabled;
	}

	public float getLat() {
		return lat;
	}

	public float getLng() {
		return lng;
	}

	public boolean isEnabled() {
		return enabled;
	}	

	@Override
	public String toString() {
		String enable;
		if (enabled) {
			enable = "Enabled";
		} else {
			enable = "Disabled";
		}
		return "("+ lat + "," + lng + "), " + enable;
	}
}
