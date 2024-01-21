package simulator.factories;

import simulator.model.ForceLaws;
import simulator.misc.Vector2D;
import simulator.model.MovingTowardsFixedPoint;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
	}
	
	@Override
	public ForceLaws createTheInstance(JSONObject jo) {
		int array[] = new int[2];

		Vector2D c = jo.has("c") ? posiC(jo, array, jo.getJSONArray("c")) : new Vector2D(0,0);
		double g = jo.has("g") ? jo.getDouble("g") : 9.81;
		
		return new MovingTowardsFixedPoint(g, c);
	}
	
	private Vector2D posiC(JSONObject jo, int[] array, JSONArray ja) {
		if(ja != null) {
			for(int i = 0; i < ja.length(); i++) {
				array[i] = ja.getInt(i);
			}	
		}
		return new Vector2D(array[0], array[1]);
	}
	
    @Override
    protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		JSONArray c = new JSONArray();
		c.put(0, "x");
		c.put(1, "y");
		
		jo.put("c", "el centro"); // center
		jo.put("g", "constante de gravedad");
		return jo;
    }
}