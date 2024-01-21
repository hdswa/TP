package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	List<Builder<T>> buildersList;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.buildersList = builders;
	}
	
	@Override
	public T createInstance(JSONObject data) throws IllegalArgumentException {
		T object = null;
		for (Builder<T> B:buildersList) {
			if (object == null) {
				object = B.createInstance(data);
			}
		}
		if (object == null) {
			throw new IllegalArgumentException("invalid data");
		}
		return object;
	}

	@Override
	public List<JSONObject> getInfo() {
		List<JSONObject> jsonStructs = new ArrayList<JSONObject>(); 
		for (Builder<T> B:buildersList) {
			jsonStructs.add(B.getBuilderInfo());
		}
		return jsonStructs;
	}

}
