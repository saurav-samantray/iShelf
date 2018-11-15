package com.sensor.ishelf.controller;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sensor.ishelf.manager.InventoryManager;
import com.sensor.response.Output;
import com.sensor.response.ServiceResponse;
import com.sensor.vo.ProductData;
import com.sensor.vo.RecommProduct;
import com.sensor.vo.RequestSensorData;

@EnableWebMvc
@RestController
@ResponseBody
public class SensorController {
	
	@Autowired
    private InventoryManager inventoryManager;
	
    
	@RequestMapping(value = "/inventory/", method = RequestMethod.GET)
	public ServiceResponse getInventoryStatus() {
		ServiceResponse response  = new ServiceResponse();
		Output output = new Output();
		List<ProductData> productDataList = inventoryManager.getInventoryDetails();
		output.setResponse(productDataList);
		response.setOutput(output);
		return response;
		
	}
	
	@RequestMapping(value = "/inventory/", method = RequestMethod.POST)
	public ServiceResponse updateSensorShelfData(@RequestBody RequestSensorData sensorData) {
		System.out.println(sensorData);
		ServiceResponse response  = new ServiceResponse();
		Output output = new Output();
		Boolean isSuccess = inventoryManager.updateShelfQty(sensorData);
		//output.setObject(productDataList);
		if(isSuccess) {
			output.setResponse(isSuccess);
			response.setOutput(output);
		} else {
			output.setResponse(Boolean.FALSE);
			response.setOutput(output);
		}
		response.setOutput(output);
		return response;
		
	}
	
	@RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
	public ServiceResponse getRecommandedProduct(@PathVariable("productId") String productId) {
		ServiceResponse response  = new ServiceResponse();
		Output output = new Output();
		List<RecommProduct> productDataList = inventoryManager.getRecommandedProduct(productId);
		output.setResponse(productDataList);
		response.setOutput(output);
		return response;
		
	}
	
	
	
}
