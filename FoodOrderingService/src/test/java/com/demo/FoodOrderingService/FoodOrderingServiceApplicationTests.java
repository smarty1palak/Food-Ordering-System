package com.demo.FoodOrderingService;

import com.demo.FoodOrderingService.dto.OrderDTO;
import com.demo.FoodOrderingService.model.Order;
import com.demo.FoodOrderingService.model.User;
import com.demo.FoodOrderingService.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
class FoodOrderingServiceApplicationTests {

	@Autowired
	private OrderService orderService; // Your order service

	@Test
	public void testConcurrentOrderCreation() throws InterruptedException {
		int numberOfThreads = 10; // Number of concurrent users
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

		List<Callable<String>> tasks = new ArrayList<>();

		// Simulate multiple order requests
		for (int i = 0; i < numberOfThreads; i++) {
			tasks.add(() -> {
				try {
					OrderDTO orderDTO = new OrderDTO(); // Fill with necessary data
					User user = new User(); // Set user details
					Order order = orderService.createOrder(orderDTO, user);
					return "Order created: " + order.getId();
				} catch (Exception e) {
					return "Failed to create order: " + e.getMessage();
				}
			});
		}

		// Run all tasks and gather results
		List<Future<String>> futures = executorService.invokeAll(tasks);

		// Print results for each thread
		futures.forEach(future -> {
			try {
				System.out.println(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});

		executorService.shutdown();
	}

}
