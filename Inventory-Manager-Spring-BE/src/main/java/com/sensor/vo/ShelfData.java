package com.sensor.vo;

import org.springframework.data.annotation.Id;

public class ShelfData {
	@Id
	private String id;
	private int weight;
	private String shelfId;
	private int qty;
	
	
	public String getId() {
		return id;
	}
	
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the shelfId
	 */
	public String getShelfId() {
		return shelfId;
	}

	/**
	 * @param shelfId the shelfId to set
	 */
	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}

	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}

	public void setId(String id) {
		this.id = id;
	}
}
