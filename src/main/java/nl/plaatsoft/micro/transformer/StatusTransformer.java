package nl.plaatsoft.micro.transformer;

import org.json.JSONObject;

import nl.plaatsoft.micro.dao.Status;

class StatusTransformer {

	public String toJson(Status status) {
		
		JSONObject obj = new JSONObject();
		
	   	obj.put("uid", status.getId());
	   	obj.put("status", status.getState());
	   	obj.put("dt",status.getTimestamp());
	   	
	   	return obj.toString();
	}
}
