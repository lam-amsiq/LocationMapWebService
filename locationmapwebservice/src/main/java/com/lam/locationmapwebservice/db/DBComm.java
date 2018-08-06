package com.lam.locationmapwebservice.db;

import java.awt.image.BufferedImage;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lam.locationmapwebservice.models.Annotation;
import com.lam.locationmapwebservice.models.AnnotationMeta;

public class DBComm extends DBCommSuper {
    public DBComm(MySQLFactory dbComm) {
        super(dbComm);
    }

	@Override
	public ArrayList<Annotation> getAnnotationList(float latMin, float latMax, float lngMin, float lngMax, long annotation_id) throws ConnectException {
        ArrayList<Annotation> result = new ArrayList<>();
        ResultSet rs = null;
        
        try {
            rs = sendCall(calls.getString("get_annotation_list"), latMin, latMax, lngMin, lngMax, annotation_id);
            
            while (rs.next())
                result.add(ModelBuilder.createAnnotation(rs));
        } catch (SQLException | ConnectException e) {
            e.printStackTrace();
            throw new ConnectException(e.getMessage());
        } finally {
            closeConnection(rs);
        }
        
        return result;
	}

	@Override
	public Annotation getAnnotation(long annotationId) throws ConnectException {
		ResultSet rs = null;

		try {
			rs = sendCall(calls.getString("get_annotation"), annotationId);

			while (rs.next())
				return ModelBuilder.createAnnotation(rs);
		} catch (SQLException | ConnectException e) {
			e.printStackTrace();
			throw new ConnectException(e.getMessage());
		} finally {
			closeConnection(rs);
		}

		return null;
	}

	@Override
	public AnnotationMeta getAnnotationMeta(long annotationId) throws ConnectException {
		ResultSet rs = null;

		try {
			rs = sendCall(calls.getString("get_annotation_meta"), annotationId);

			while (rs.next())
				return ModelBuilder.createAnnotationMeta(rs);
		} catch (SQLException | ConnectException e) {
			e.printStackTrace();
			throw new ConnectException(e.getMessage());
		} finally {
			closeConnection(rs);
		}

		return null;
	}

	@Override
	public BufferedImage getAnnotationPortrait(long annotationId) throws ConnectException {
        ResultSet rs = null;
        
        try {
            rs = sendCall(calls.getString("get_annotation_portrait"), annotationId);
            
            while (rs.next())
                return ModelBuilder.createAnnotationPortrait(rs);
        } catch (SQLException | ConnectException e) {
            e.printStackTrace();
            throw new ConnectException(e.getMessage());
        } finally {
            closeConnection(rs);
        }
        
        return null;
	}

	@Override
	public BufferedImage getAnnotationThumb(long annotationId) throws ConnectException {
        ResultSet rs = null;
        
        try {
            rs = sendCall(calls.getString("get_annotation_thumb"), annotationId);
            
            while (rs.next())
                return ModelBuilder.createAnnotationThumb(rs);
        } catch (SQLException | ConnectException e) {
            e.printStackTrace();
            throw new ConnectException(e.getMessage());
        } finally {
            closeConnection(rs);
        }
        
        return null;
	}

	@Override
	public int updatePosition(long annotationId, float lat, float lng, boolean enabled) throws ConnectException {
		try {
			return sendUpdate(calls.getString("update_position"), annotationId, lat, lng, enabled);
		} catch (SQLException e) {
			e.printStackTrace();
            throw new ConnectException(e.getMessage());
		}
	}
}
