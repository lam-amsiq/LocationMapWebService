package com.lam.locationmapwebservice.db.data;

public enum DBTags {
    // Annotations
    ANNOTATION_ID("an_id"),
    ANNOTATION_NAME("an_name"),
    ANNOTATION_HAS_PORTRAIT("an_has_portrait"),
    
    // Position
    POSITION_LAT("po_lat"),
    POSITION_LNG("po_lng"),
    POSITION_ENABLE("po_enable"),
	
	// Image
    IMAGE_PORTRAIT("im_portrait"),
    IMAGE_THUMB("im_thumb"),
	
	// Meta
    META_GENDER("an_gender"),
    META_AGE("me_age");
	
    public final String tag;
    DBTags(String tag) { 
        this.tag = tag; 
    }
}
