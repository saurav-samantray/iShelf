package com.sensor.tool;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.UpdateResult;
import com.sensor.ishelf.constant.IshelfConstant;
import com.sensor.vo.ProductData;
import com.sensor.vo.RecommProduct;
import com.sensor.vo.RequestSensorData;
import com.sensor.vo.SensorData;
import com.sensor.vo.ShelfData;

@Component
public class InventoryTool {
	
	Logger logger = LoggerFactory.getLogger(InventoryTool.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * fatch all inventory details.
	 * @return List<ProductData>
	 */
	public List<ProductData> getInventoryDetails() {
		List<ProductData> productDataList= mongoTemplate.findAll(ProductData.class, IshelfConstant.PRODUCT_COLLECTIONS);
		return productDataList;
	}
	/**
	 * fatch sensor data based on id.
	 * @param id
	 * @return
	 */
	public SensorData getSensorDetails(final String id) {
		ObjectId objeid = new ObjectId(id);
		SensorData sensorData= mongoTemplate.findById(objeid, SensorData.class);
		return sensorData;
	}
	
	/**
	 * Update shelf weight based on shelf id. 
	 * @param sensorData
	 * @return
	 */
	public Boolean updateQtyForShelf(final RequestSensorData sensorData) {
		Boolean isSuccess = false;
		Update update = new Update();
		Query shelfQuery = new Query(Criteria.where(IshelfConstant.SHELFID).is(sensorData.getShelfId()));
		List<ShelfData> shelfData = mongoTemplate.find(shelfQuery, ShelfData.class, IshelfConstant.SENSOR_COLLECTIONS);
		if(shelfData.size() > 0) {
			String id = shelfData.get(0).getId();
			logger.info("shelf data id {}",id);
			ObjectId objeid = new ObjectId(id);
			Query query = new Query(Criteria.where(IshelfConstant.ID).is(objeid));
			update.set(IshelfConstant.SHELFID, sensorData.getShelfId());
			update.set(IshelfConstant.WEIGHT, sensorData.getWeight());
			logger.info("update query {} with ShelfId {} and Weight {}",update,sensorData.getShelfId(), sensorData.getWeight());
			UpdateResult result =mongoTemplate.updateFirst(query, update, ShelfData.class, IshelfConstant.SENSOR_COLLECTIONS);
			isSuccess = result.wasAcknowledged();
		}
		return isSuccess;
	}
	public List<RecommProduct> getRecommandedProduct(String productId) {
		List<RecommProduct> recomProductList = new ArrayList<>();
		Query productQuery = new Query(Criteria.where(IshelfConstant.PRODUCTID).is(productId));
		List<ProductData> productRecommList = mongoTemplate.find(productQuery, ProductData.class, IshelfConstant.PRODUCT_COLLECTIONS);
		if(productRecommList.size() > 0) {
			recomProductList =productRecommList.get(0).getRecomandedProduct().stream().map(x -> {
				RecommProduct recom = new RecommProduct();
				Query query = new Query(Criteria.where(IshelfConstant.PRODUCTID).is(x));
				List<ProductData> productList = mongoTemplate.find(query, ProductData.class, IshelfConstant.PRODUCT_COLLECTIONS);
				if(productList.size() > 0) {
					ProductData productData= productList.get(0);
					recom.setProdName(productData.getProdName());
					recom.setProductId(productData.getProductId());
				}
 				return recom;
			}).collect(Collectors.toList());
		}
		return recomProductList;
	}
}
