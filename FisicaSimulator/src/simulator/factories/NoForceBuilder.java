package simulator.factories;

import org.json.JSONObject;
import simulator.model.*;
import org.json.JSONException;

public class NoForceBuilder extends Builder<ForceLaws> {

	public NoForceBuilder() {
		super("ng", "NoForceBuilder");
	}

    @Override
    public ForceLaws createTheInstance(JSONObject info) {
        NoForce body = null;

        try {
            body = new NoForce();
        }catch (JSONException e) {
            if (e.getMessage() == "Null") {
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
		return new JSONObject();
	}
}