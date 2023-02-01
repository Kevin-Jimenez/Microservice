package com.microservice.inventoryservice;

import com.microservice.inventoryservice.entity.Inventory;
import com.microservice.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return  args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("Huawei_Mate_30_Pro");
			inventory.setQuantity(10);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("Huawei_Mate_30_Pro_Blue");
			inventory1.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}*/
}
