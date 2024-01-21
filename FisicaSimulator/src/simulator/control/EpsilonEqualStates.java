package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator {
    private final double eps;

    public EpsilonEqualStates(JSONObject data) {
        if (data.has("eps")) {
            eps = data.getDouble("eps");
        } 
        else {
        	eps = 0;
        }
    }
    
    public EpsilonEqualStates(double eps) {
        this.eps = eps;
    }

    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {

        boolean equal=true;
        if(s1.getDouble("time")==s2.getDouble("time")) {
        	JSONArray a = s1.getJSONArray("bodies");
            JSONArray b = s2.getJSONArray("bodies"); //lists
            
        	if(a.length()==b.length()) {
        		for(int i=0;i<a.length();i++) {
        			JSONObject b1 = a.getJSONObject(i);//bodies
                    JSONObject b2 = b.getJSONObject(i);//bodies 2
                    boolean vId=(b1.getString("id").equals(b2.getString("id")));//id
                    //velocity
                    Vector2D v1 = new Vector2D(b1.getJSONArray("v").getDouble(0),b1.getJSONArray("v").getDouble(1));
                    Vector2D v2 = new Vector2D(b2.getJSONArray("v").getDouble(0),b2.getJSONArray("v").getDouble(1));
                    boolean vVec=(v1.distanceTo(v2)<=eps);
                    //position
                    v1 = new Vector2D(b1.getJSONArray("p").getDouble(0),b1.getJSONArray("p").getDouble(1));
                    v2 = new Vector2D(b2.getJSONArray("p").getDouble(0),b2.getJSONArray("p").getDouble(1));
                    boolean vPos=(v1.distanceTo(v2)<=eps);
                    //force
                    v1 = new Vector2D(b1.getJSONArray("f").getDouble(0),b1.getJSONArray("f").getDouble(1));
                    v2 = new Vector2D(b2.getJSONArray("f").getDouble(0),b2.getJSONArray("f").getDouble(1));
                    boolean vF = (v1.distanceTo(v2)<=eps);
                    //mass
                    boolean vM = (Math.abs(b1.getDouble("m"))-Math.abs(b2.getDouble("m"))<=eps);
                  
                    if(vId&&vVec&&vPos&&vF&&vM) {
                    	//nothing
                    }
                    else
                    	equal = false;
        		}
        	}
        	else
        		equal=false;
        }
        else {
        	equal=false;
        }
        return equal;
          
     }
}
