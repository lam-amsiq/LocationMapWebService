package com.lam.locationmapwebservice.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Annotation {
	private long annotation_id;
	private String name;
	private String portrait;
	private String thumb;
	private Location position;
	private boolean hasPortrait = false;

	public Annotation(long annotation_id, String name, String portrait, String thumb, Location position) {
		this.annotation_id = annotation_id;
		this.name = name;
		this.portrait = portrait;
		this.thumb = thumb;
		this.position = position;
	}

	public long getAnnotation_id() {
		return annotation_id;
	}

	public String getName() {
		return name;
	}

	public Location getPosition() {
		return position;
	}

	public boolean hasPortrait() {
		return hasPortrait;
	}

	public void setHasPortrait(boolean hasPortrait) {
		this.hasPortrait = hasPortrait;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	@Override
	public String toString() {
		return "Annotation [annotation_id=" + annotation_id + ", name=" + name + ", portrait=" + portrait + ", thumb=" + thumb + ", position=" + position + "]";
	}
}