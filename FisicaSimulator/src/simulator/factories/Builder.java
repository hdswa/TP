package simulator.factories;

import org.json.JSONObject;

public abstract class Builder <T>  {

    protected String typeTag, desc;

	public Builder(String type, String desc) {
		this.typeTag = type;
		this.desc = desc;
	}

    protected T createInstance(JSONObject info) throws IllegalArgumentException {

        if (!info.getString("type").equals(typeTag)) {
        	return null;
        }
        else {
        	return createTheInstance(info.getJSONObject("data"));
        }
    }

    public JSONObject getBuilderInfo () {
		JSONObject info = new JSONObject();
		
		info.put("type", typeTag);
		info.put("data", createData());
		info.put("desc", desc);
		
		return info;
    }
    
    public abstract T createTheInstance(JSONObject info);

    protected JSONObject createData(){
        return new JSONObject();
    }
}
