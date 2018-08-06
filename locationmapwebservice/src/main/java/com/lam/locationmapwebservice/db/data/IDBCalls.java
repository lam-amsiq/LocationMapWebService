package com.lam.locationmapwebservice.db.data;

import java.awt.image.BufferedImage;
import java.net.ConnectException;
import java.util.ArrayList;

import com.lam.locationmapwebservice.models.Annotation;
import com.lam.locationmapwebservice.models.AnnotationMeta;

public interface IDBCalls {
    public ArrayList<Annotation> getAnnotationList(float latMin, float latMax, float lngMin, float lngMax, long anootation_id) throws ConnectException;

    public Annotation getAnnotation(long annotationId) throws ConnectException;
    public AnnotationMeta getAnnotationMeta(long annotationId) throws ConnectException;
    
    public BufferedImage getAnnotationPortrait(long annotationId) throws ConnectException;
    public BufferedImage getAnnotationThumb(long annotationId) throws ConnectException;
    
    public int updatePosition(long annotationId, float lat, float lng, boolean enabled) throws ConnectException;
}