package simulator.control;

import org.json.JSONObject;
import org.json.JSONArray;

public class MassEqualStates implements StateComparator {
	
	 @Override
	 public boolean equal(JSONObject s1, JSONObject s2) {
		 boolean equal = false;  
		 if(s1.getDouble("time") == s2.getDouble("time")){
			JSONArray b1 = s1.getJSONArray("bodies");
			JSONArray b2 = s2.getJSONArray("bodies");	
			if(b1.length() == b2.length()) {
				for (int i = 0; i < b1.length(); i++) {
					JSONObject a = b1.getJSONObject(i);
					JSONObject b = b1.getJSONObject(i);
					
					if(b.getString("id").equals(a.getString("id")) && b.getDouble("m") == a.getDouble("m") ) 
							equal = true;
					else {
						equal = false;
					}		
				}	
			}
			return equal; 
		 }
		return equal;
	 }
}
