package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;
import org.json.JSONException;

public class Body {

    protected String id;
    protected Vector2D v;
    protected Vector2D f;
    protected Vector2D p;
    protected double m;

    public Body(String id, Vector2D v, Vector2D p, double m) {
        this.id = id;
        this.v = v;
        this.p = p;
        this.m = m;
    }

    public Body(JSONObject data) throws JSONException {
        this.id = data.getString("id");
        this.m = data.getDouble("m");
        this.v = new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1));
        this.p = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
        this.f = new Vector2D();
    }

    public String getId() {
        return id;
    }

    public Vector2D getVel() {
        return v;
    }

    public Vector2D getForce() {
        return f;
    }

    public Vector2D getPos() {
        return p;
    }

    public double getMass() {
        return m;
    }

    public void addForce(Vector2D f) {
        this.f = this.f.plus(f);
    }
    
    public void resetForce() {
        f = new Vector2D();
    }
    
    public void move(double t) {
        Vector2D a;

        if (m == 0) {
            a = new Vector2D();
        }
        else {
        	a = new Vector2D(this.f.scale(1.0/this.m));
        }
        p = p.plus(v.scale(t).plus(a.scale(t * t * 0.5)));
        v = v.plus(a.scale(t));
    }

    public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		jo.put("m", getMass());
		jo.put("v", getVel().asJSONArray());
		jo.put("f", getForce().asJSONArray());
		jo.put("p", getPos().asJSONArray());
		
		return jo;
    }

    public String toString() {
        return getState().toString();
    }

}