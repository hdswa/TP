package simulator.factories;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;
import org.json.JSONException;
import org.json.JSONObject;

public class MassEqualStatesBuilder extends Builder<StateComparator> {

    public MassEqualStatesBuilder(){
    	super("masseq", "MassEqualStatesBuilder");
    }

    @Override
    public StateComparator createTheInstance(JSONObject data) {
        MassEqualStates body = null;
        try {
            body = new MassEqualStates();
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
		return jo;
	}
}