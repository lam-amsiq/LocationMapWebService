package com.lam.locationmapwebservice.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.lam.locationmapwebservice.db.DummyDatabase;
import com.lam.locationmapwebservice.models.Annotation;
import com.lam.locationmapwebservice.models.AnnotationMeta;

@Path("dummy")
public class DummyResource {
	@GET
	@Path("annotations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnnotations(@QueryParam("latMin") float latMin, @QueryParam("latMax") float latMax, @QueryParam("lngMin") float lngMin, @QueryParam("lngMax") float lngMax, @QueryParam("id") long annotation_id) {
		try {
			ArrayList<Annotation> annotationList = DummyDatabase.getInstance().getAnnotationList(latMin, latMax, lngMin, lngMax, annotation_id);
			
			if (annotationList != null) {
				return Response.ok(annotationList).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
			
		} catch (ConnectException e) {
			System.err.println("Failed to get annotation list");
			return Response.serverError().build();
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnnotation(@PathParam("id") long id) {
		try {
			Annotation annotation = DummyDatabase.getInstance().getAnnotation(id);
			
			if (annotation != null) {
				return Response.ok(annotation).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (ConnectException e) {
			System.err.println("Failed to get annotation");
			return Response.serverError().build();
		}
	}

	@GET
	@Path("meta/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMeta(@PathParam("id") long id) {
		try {
			AnnotationMeta meta = DummyDatabase.getInstance().getAnnotationMeta(id);
			
			if (meta != null) {
				return Response.ok(meta).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (ConnectException e) {
			System.err.println("Failed to get annotation meta");
			return Response.serverError().build();
		}
	}

	@GET
	@Path("portrait/{id}")
	@Produces("image/png")
	public Response getPortrait(@PathParam("id") long id) {
		try {
			return buildImageResponse(DummyDatabase.getInstance().getAnnotationPortrait(id));
		} catch (ConnectException e) {
			System.err.println("Failed to get annotation portrait");
			return Response.serverError().build();
		}
	}

	@GET
	@Path("thumb/{id}")
	@Produces("image/png")
	public Response getThumb(@PathParam("id") long id) {
		try {
			return buildImageResponse(DummyDatabase.getInstance().getAnnotationThumb(id));
		} catch (ConnectException e) {
			System.err.println("Failed to get annotation thumb");
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Path("position")
	@Produces(MediaType.TEXT_PLAIN)
	public Response updatePosition(@QueryParam("lat") float lat, @QueryParam("lng") float lng, @QueryParam("enabled") boolean enabled, @QueryParam("id") long annotation_id) {
		try {
			int updatedRows = DummyDatabase.getInstance().updatePosition(annotation_id, lat, lng, enabled);
			
			if (updatedRows > 0) {
				return Response.ok(updatedRows).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
			
		} catch (ConnectException e) {
			System.err.println("Failed to get annotation list");
			return Response.serverError().build();
		}
	}
	
	private Response buildImageResponse(BufferedImage image) {
		if (image == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
			byte[] imageData = baos.toByteArray();
			return Response.ok(new ByteArrayInputStream(imageData)).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}