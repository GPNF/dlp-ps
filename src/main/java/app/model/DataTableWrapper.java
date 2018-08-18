package app.model;

/**
 * A wrapper written as to obtain json as {data:...} format, required for
 * datatable.
 * 
 * @author AdarshSinghal
 *
 */
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
