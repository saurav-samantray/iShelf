package com.sensor.vo;
import org.springframework.data.annotation.Id;

import java.util.List;

public class ProductData {
	@Id
	private String id;
	private String productId;
	private int weight;
	private int threshold;
	private List<String>recomandedProduct;
	private String prodName;
	private List<String> shelfId;
	private int availablestock;
	private boolean invalidItemPresent;
	
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
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @return the threshold
	 */
	public int getThreshold() {
		return threshold;
	}
	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	/**
	 * @return the recomandedProduct
	 */
	public List<String> getRecomandedProduct() {
		return recomandedProduct;
	}
	/**
	 * @param recomandedProduct the recomandedProduct to set
	 */
	public void setRecomandedProduct(List<String> recomandedProduct) {
		this.recomandedProduct = recomandedProduct;
	}
	/**
	 * @return the prodName
	 */
	public String getProdName() {
		return prodName;
	}
	/**
	 * @param prodName the prodName to set
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
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
	 * @return the availablestock
	 */
	public int getAvailablestock() {
		return availablestock;
	}
	/**
	 * @param availablestock the availablestock to set
	 */
	public void setAvailablestock(int availablestock) {
		this.availablestock = availablestock;
	}
}
