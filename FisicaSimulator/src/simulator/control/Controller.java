package simulator.control;

import java.io.*;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;
import simulator.view.BodiesTableModel;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;

public class Controller {
    private final PhysicsSimulator s;
    private final Factory<Body> f;
    private final Factory<ForceLaws> lf;
    private final BodiesTableModel tableModel;
    

    public Controller(PhysicsSimulator simulator, Factory<Body> bodyFactory, Factory<ForceLaws> lawsFactory) {
        this.s = simulator;
        this.f = bodyFactory;
        this.lf = lawsFactory;
        tableModel=new BodiesTableModel(this);
    }
    public Controller() {
    	tableModel=new BodiesTableModel(this);
    	this.s=null;
    	this.lf=null;
    	this.f=null;
    	
    }

    public void loadBodies(InputStream in) {
        JSONObject jsonInput = new JSONObject(new JSONTokener(in));
        JSONArray boodies = jsonInput.getJSONArray("bodies");
        for (int i = 0; i < boodies.length(); i++) {
        	Body b = f.createInstance(boodies.getJSONObject(i));
            s.addBody(b);
            
        }
    }

    public void run(int n, OutputStream out, InputStream expOut, StateComparator comparator) throws ComparisonException {
        boolean equal = true;
        JSONObject state;
        JSONArray expStates = null;

        PrintStream p = new PrintStream(out);
        p.println("{");
        p.println("\"states\": [");
        if (expOut != null) {
            expStates = new JSONObject(new JSONTokener(expOut)).getJSONArray("states");
            if (n + 1 != expStates.length()) {
                equal = false;
                throw new ComparisonException();
            }
        }
        for(int i = 0; i <=n; i++) {
        	if(equal) {
                state = s.getState();
                if (expOut != null && !comparator.equal(state, expStates.getJSONObject(i))) {
                	equal = false;
                    throw new ComparisonException(i + 3);
                }
                s.advance();
                if(i != 0) {
                	p.print(",");
                }
                p.println(state.toString());
        	}
        }
        p.println("]");
        p.println("}");
    }
    
    public void run(int n) {

        new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            };
        };

        for (int i = 0;i < n;i++) {
           s.advance();
          
           }
    }
    
	public void reset() {
		s.reset();
	}
	
	public void setDeltaTime(double dt) {
		s.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		s.addObserver(o);
	}
	
	public List<JSONObject>getForceLawsInfo(){
		return lf.getInfo();
	}
	
	public void setForceLaws(JSONObject info) {
		ForceLaws f = lf.createInstance(info);
		s.setForceLawsLaws(f);
	}
	public ForceLaws getForceLaws() {
		return this.s.getForceLaw();
		
	}
	
	public void exit() {
		System.exit(0);
	}
	public BodiesTableModel getModel() {
		
		return this.tableModel;
	}
	public PhysicsSimulator getSim() {
		
		return this.s;
	}
}