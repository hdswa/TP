package simulator.factories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

    public BasicBodyBuilder() {
    	super("basic", "BasicBodyBuilder");
    }

    @Override
    public Body createTheInstance(JSONObject data) {
        Body body = null;

        try {
            body = new Body(data);
        }catch (JSONException e) {
            if (e.getMessage().equals("Null key.")) {
            	body = null;
            }
            else {
            	throw new IllegalArgumentException();
            }
        }
        return body;
    }

    @Override
    protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		JSONArray v = new JSONArray();
		JSONArray p = new JSONArray();
		v.put(0, "x");
		v.put(1, "y");
		p.put(0, "x");
		p.put(1, "y");
		
		jo.put("id", "id del cuerpo");
		jo.put("v", v);
		jo.put("p", p);
		jo.put("m", "masa");
		
		return jo;
    }
}
