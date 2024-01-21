package simulator.factories;

import simulator.model.Body;
import simulator.model.MassLosingBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MassLosingBodyBuilder extends Builder<Body>{


    public MassLosingBodyBuilder() {
    	super("mlb", "MassLosingBodyBuilder");
    }

    @Override
    public Body createTheInstance(JSONObject data) {
        MassLosingBody body = null;

        try {
            body = new MassLosingBody(data);
        }catch (JSONException e) {
            if (e.getMessage().equals("Null")) {
            	body = null;
            }
            else {
                throw new IllegalArgumentException("Error");
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
		jo.put("factor", "factor de perdida de masa");
		jo.put("freq", "frequencia de perdida de masa");
		return jo;
	}

}
