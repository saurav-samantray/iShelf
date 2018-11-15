package com.sensor.vo;

import java.util.List;

import org.springframework.data.annotation.Id;

public class SensorData {
	@Id
	private String id;
	private int weight;
	private List<String> shelfId;
	private long qty;
	private boolean invalidItemPresent;
	
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * @return the qty
	 */
	public long getQty() {
		return qty;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(long qty) {
		this.qty = qty;
	}
	/**
	 * @return the shelfId
	 */
	public List<String> getShelfId() {
		return shelfId;
	}
	/**
	 * @param shelfId the shelfId to set
	 */
	public void setShelfId(List<String> shelfId) {
		this.shelfId = shelfId;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	/**
	 * @return the invalidItemPresent
	 */
	public boolean isInvalidItemPresent() {
		return invalidItemPresent;
	}
	/**
	 * @param invalidItemPresent the invalidItemPresent to set
	 */
	public void setInvalidItemPresent(boolean invalidItemPresent) {
		this.invalidItemPresent = invalidItemPresent;
	}
	
}
