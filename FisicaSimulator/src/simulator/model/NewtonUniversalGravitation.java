package simulator.model;

import org.json.JSONObject;
import java.util.List;
import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

    private double G;

    public NewtonUniversalGravitation(JSONObject data) {
        if (data.has("G")) {
            G = data.getDouble("G");
        }
        else {
            G = 6.67E-11;
        }
    }

    public NewtonUniversalGravitation(double G) {
        this.G = G;
    }

    @Override
    public void apply(List<Body> bs) {
    	for(Body b1 :bs) {
    		for(Body b2:bs) {
    			if(b1!=b2) {
    				b1.addForce(force(b1,b2));
    			}
    		}
    	}
    }

    private Vector2D force(Body b1, Body b2) {
        Vector2D delta = b2.getPos().minus(b1.getPos());
        double dist = delta.magnitude();
        double magnitude = dist>0 ? (G * b1.getMass() * b2.getMass()) / (dist * dist) : 0.0;
        return delta.direction().scale(magnitude);
    }
    
	@Override
	public String toString() {
		return "Newton´s Universal Gravitation with G= " + G;
	}
  }