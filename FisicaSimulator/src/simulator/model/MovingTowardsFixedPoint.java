package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws {

	private Vector2D center;
    private double g;

    public MovingTowardsFixedPoint(double g, Vector2D center) {
        this.center = new Vector2D(center);
        this.g = g;
    }
    
	@Override
	public String toString() {
		return "Moving towards " + center + " with constant acceleration " + g;
	}
	
    public MovingTowardsFixedPoint(JSONObject data) {
        if (data.has("g")) {
        	this.g = data.getDouble("g");
        }
        else {
        	g = 9.81;
        }
        if (data.has("c")) {
        	this.center = new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1));
        }
        else {
        	center = new Vector2D();
        }
    }

    @Override
    public void apply(List<Body> bs) {
		for (Body body: bs) {
			body.addForce(center.minus(body.getPos()).direction().scale(g * body.m));
		}
    }

}