package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class UnassedOrderIDList implements Serializable {
	private List<Integer> list = new ArrayList<>();

	public UnassedOrderIDList(List<Integer> list) {
		this.list = list;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

}
