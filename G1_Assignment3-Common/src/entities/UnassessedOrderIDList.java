package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class UnassessedOrderIDList implements Serializable {
	private List<Integer> list = new ArrayList<>();

	public UnassessedOrderIDList(List<Integer> list) {
		this.list = list;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

}
