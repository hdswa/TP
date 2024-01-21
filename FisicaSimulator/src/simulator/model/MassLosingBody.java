package simulator.model;

import simulator.misc.Vector2D;
import org.json.JSONObject;

public class MassLosingBody extends Body {

    protected double lossFactor;
    protected double lossFrequency;
    protected double contador;

    public MassLosingBody(JSONObject data) {
        super(data);
        this.contador = 0;
        this.lossFrequency = data.getDouble("freq");
        this.lossFactor = data.getDouble("factor");
    }
    
    public MassLosingBody(String id, Vector2D v, Vector2D p, double m, double lossFactor, double lossFrequency) {
        super(id, v, p, m);
        this.contador = 0;
        this.lossFrequency = lossFrequency;
        this.lossFactor = lossFactor;
    }

    @Override
    public void move(double t) {
        super.move(t);
        contador += t;
        if (contador >= lossFrequency) {
            m = this.getMass() * (1 - this.lossFactor);
            contador = 0;
        }
    }
}