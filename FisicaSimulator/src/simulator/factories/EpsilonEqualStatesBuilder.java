package simulator.factories;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;
import org.json.JSONException;
import org.json.JSONObject;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator>{

    public  EpsilonEqualStatesBuilder(){
    	super("epseq", "EpsilonEqualStatesBuilder");
    }

    @Override
    public StateComparator createTheInstance(JSONObject data) {
        EpsilonEqualStates body = null;

        try {
            body = new EpsilonEqualStates(data);
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
		jo.put("eps", "epsilon");
		return jo;
    }
}

