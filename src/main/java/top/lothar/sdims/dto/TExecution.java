package top.lothar.sdims.dto;

import java.util.List;

/**
 * 泛型，以及分页数据临时存储对象,以及状态信息
 * @author Lothar
 * @param <T>
 *
 */
public class TExecution<T> {
	
	private String stateInfo;
	
	private int count;
	
	private List<T> data;
	/**
	 * 无参构造
	 */
	public TExecution() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 失败构造器
	 * @param stateInfo
	 */
	public TExecution(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	
	
}
