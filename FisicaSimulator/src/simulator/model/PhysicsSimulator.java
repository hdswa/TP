package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;

public class PhysicsSimulator {

    private ForceLaws law;
    private double time;
    private double dt;
    private List<Body> bs;
    private int steps=0;
    private List<SimulatorObserver> so;

    public PhysicsSimulator(double dt, ForceLaws law) {
        this.law = law;
        time = 0;
        this.dt = dt;
        this.bs = new ArrayList<Body>();
        this.so=new ArrayList<SimulatorObserver>();
    }

    public void advance() {
        for (Body body : bs)
            body.resetForce();
        law.apply(bs);
        for (Body body : bs)
            body.move(dt);
        time += dt;
        for(SimulatorObserver a: so){
			a.onAdvance(bs, time);
		}
    }
    public void setDeltaTime(double dt) throws IllegalArgumentException {
        this.dt = dt;
        for(SimulatorObserver o:this.so)
            o.onDeltaTimeChanged(dt);
    }

    public void setForceLawsLaws(ForceLaws forceLaws) throws IllegalArgumentException {
        this.law = forceLaws;
        for(SimulatorObserver o:this.so)
            o.onForceLawsChanged(law.toString());
    }
    public void addBody(Body b) throws IllegalArgumentException {
		if(!bs.contains(b)) {
		for (Body body : bs) {
			if(body.getId().equals(b.id))//ya existe
				throw new IllegalArgumentException();
			}
			bs.add(b);
			for(SimulatorObserver a: so){
				a.onBodyAdded(bs, b);
			}
		}
    }
    public void reset() {
        bs.clear();
        this.time = 0.0;

        for (int i = 0; i < this.so.size(); i++) {
            this.so.get(i).onReset(bs, time, dt, law.toString());
        }
    }


    public JSONObject getState() {
        JSONObject jo = new JSONObject();
        JSONArray jo1 = new JSONArray();
        jo.put("time", time);
        for (Body body : bs) {
        	jo1.put(body.getState());
        }
        jo.put("bodies", jo1);

        return jo;
    }
    
    public void addObserver(SimulatorObserver o) {
    	boolean eq=true;
    	for(int i=0;i<so.size();i++) {
    		if(so.contains(o)) {
    			eq=false;
    		}
    	}
    	if(eq)
    	so.add(o);
    	
    }
    
    public ForceLaws getForceLaw() {
    	
    	
    	return this.law;
    }
    public List<Body> getBodies(){
    	
    	return this.bs;
    }
    public void setSteps(int n) {
    	
    	this.steps=n;
    }
    public int getSteps() {
    	
    	return this.steps;
    }
    public double getDT() {
    	return this.dt;
    }
  
}
