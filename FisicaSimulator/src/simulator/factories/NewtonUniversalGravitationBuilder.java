package simulator.factories;

import simulator.model.NewtonUniversalGravitation;
import org.json.JSONObject;
import simulator.model.ForceLaws;
import org.json.JSONException;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{


    public  NewtonUniversalGravitationBuilder( ){
        super("nlug", "NewtonUniversalGravitationBuilder");
    }
    
    @Override
    public ForceLaws createTheInstance(JSONObject data) {
        NewtonUniversalGravitation body = null;
        try {
            body = new NewtonUniversalGravitation(data);
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
		jo.put("G", "constante de gravitacion");
		return jo;
    }
}
