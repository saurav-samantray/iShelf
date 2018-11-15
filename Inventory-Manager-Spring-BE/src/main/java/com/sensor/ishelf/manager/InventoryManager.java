package com.sensor.ishelf.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sensor.tool.InventoryTool;
import com.sensor.vo.ProductData;
import com.sensor.vo.RecommProduct;
import com.sensor.vo.RequestSensorData;
import com.sensor.vo.SensorData;

@Component
public class InventoryManager {
	
	@Autowired
    private InventoryTool inventoryTool;
	
	/**
	 * 
	 * @return List<ProductData>
	 */

	public List<ProductData> getInventoryDetails() {
		List<ProductData> productDataList = inventoryTool.getInventoryDetails();
		productDataList.stream().forEach(pData -> {
			AtomicInteger qty = new AtomicInteger();
			AtomicBoolean isInvalidItemPresent = new AtomicBoolean();
			pData.getShelfId().stream().forEach(shelfId -> {
				SensorData sensorData = inventoryTool.getSensorDetails(shelfId);
				qty.set((int) sensorData.getQty());
				isInvalidItemPresent.set(sensorData.isInvalidItemPresent());
			});
			int stock = (qty.get() * 20) / pData.getThreshold();
			pData.setAvailablestock(stock);
			pData.setInvalidItemPresent(isInvalidItemPresent.get());
		});
		/**for(ProductData pData : productDataList2) {
			int stock = 0;
			int qty = 0;
			List<String> shelfIds = pData.getShelfId();
			for(String shelfId: shelfIds) {
				SensorData sensorData = inventoryTool.getSensorDetails(shelfId);
				qty +=sensorData.getQty();
			}
			stock = (qty * 20) / pData.getThreshold();
			pData.setAvailablestock(stock);
		} */
		return productDataList;
	}

	public Boolean updateShelfQty(final RequestSensorData sensorData) {
		return inventoryTool.updateQtyForShelf(sensorData);
	}

	public List<RecommProduct> getRecommandedProduct(String productId) {
		List<RecommProduct> productDataList = inventoryTool.getRecommandedProduct(productId);
		return productDataList;
	}
}
