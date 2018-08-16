package app.model;

public class DataTableWrapper {

	private Object data;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public DataTableWrapper(Object object) {
		this.data = object;
	}
	
}
