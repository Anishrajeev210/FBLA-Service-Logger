package model;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/model")
public class ModelRestWS {

	public Response modelGet() {
		String test = "nithya";
		Object responseObject = test;;
		return Response.ok(responseObject).build();
	}
}
