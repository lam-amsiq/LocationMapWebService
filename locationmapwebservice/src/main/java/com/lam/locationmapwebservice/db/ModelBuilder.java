package com.lam.locationmapwebservice.db;

import com.lam.locationmapwebservice.models.Annotation;
import com.lam.locationmapwebservice.models.AnnotationMeta;
import com.lam.locationmapwebservice.models.Location;

import static com.lam.locationmapwebservice.db.data.DBTags.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

public class ModelBuilder {
    static final protected Annotation createAnnotation(ResultSet rs) throws SQLException {
        long id = rs.getLong(ANNOTATION_ID.tag);
        String name = rs.getString(ANNOTATION_NAME.tag);
        boolean hasPortrait = rs.getBoolean(ANNOTATION_HAS_PORTRAIT.tag);
        
        float lat = rs.getFloat(POSITION_LAT.tag);
        float lng = rs.getFloat(POSITION_LNG.tag);
        boolean enabled = rs.getBoolean(POSITION_ENABLE.tag);
        
        Annotation annotation = new Annotation(id, name, null, null, new Location(lat, lng, enabled));
        
        if (hasPortrait) {
        	annotation.setPortrait("dummy/portrait/"+id);
        	annotation.setThumb("dummy/thumb/"+id);
        	annotation.setHasPortrait(hasPortrait);
        }
        
        return annotation;
    }
    
    static final protected AnnotationMeta createAnnotationMeta(ResultSet rs) throws SQLException {
        long id = rs.getLong(ANNOTATION_ID.tag);
        int age = rs.getInt(META_AGE.tag);
        boolean isMale = rs.getBoolean(META_GENDER.tag);
        
        return new AnnotationMeta(id, age, isMale);
    }
    
    static final protected BufferedImage createAnnotationPortrait(ResultSet rs) throws SQLException {
        return createAnnotationImage(rs, IMAGE_PORTRAIT.tag);
    }
    
    static final protected BufferedImage createAnnotationThumb(ResultSet rs) throws SQLException {
        return createAnnotationImage(rs, IMAGE_THUMB.tag);
    }
    
    static final private BufferedImage createAnnotationImage(ResultSet rs, String tag) throws SQLException {
        Blob image = rs.getBlob(tag);
        InputStream in = image.getBinaryStream();
        
        try {
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
}