package com.ato.bananaleaf;

import com.ato.bananaleaf.entity.Department;
import com.ato.bananaleaf.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BananaleafApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BananaleafApplication.class, args);

		MessageProducer producer = context.getBean(MessageProducer.class);
		MessageListener listener = context.getBean(MessageListener.class);

		producer.sendMessage("Ba ba ba baba nana");

		try {
			listener.latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// context.close();
	}

	@Bean
	public MessageProducer messageProducer() {
		return new MessageProducer();
	}

	@Bean
	public MessageListener messageListener() {
		return new MessageListener();
	}

	public static class MessageProducer {
		@Autowired
		private KafkaTemplate<String, String> kafkaTemplate;

		@Autowired
		private KafkaTemplate<String, Employee> employeeKafkaTemplate;

		@Autowired
		private KafkaTemplate<String, Department> departmentKafkaTemplate;

		@Value(value = "${message.topic.name}")
		private String topicName;

		@Value(value = "${employee.topic.name}")
		private String employeeTopic;

		@Value(value = "${department.topic.name}")
		private String departmentTopic;

		public void sendMessage(String message) {
			ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

			future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
				@Override
				public void onFailure(Throwable ex) {
					System.out.println("Unable to send=[" + message + "] due to : " + ex.getMessage());
				}

				@Override
				public void onSuccess(SendResult<String, String> result) {
					System.out.println("Send message=[" + message + "] with offset : " + result.getRecordMetadata().offset());
				}
			});
		}

		public void sendEmployeeMessage(Employee employee) {
			employeeKafkaTemplate.send(employeeTopic, employee);
		}

		public void sendDepartmentMessage(Department department) {
			departmentKafkaTemplate.send(departmentTopic, department);
		}
	}

	public static class MessageListener {
		private CountDownLatch latch = new CountDownLatch(3);

		private CountDownLatch employeeLatch = new CountDownLatch(1);

		private CountDownLatch departmentLatch = new CountDownLatch(1);

		@KafkaListener(topics = "${message.topic.name}", groupId = "bananaleaf", containerFactory = "bananaleafListenerContainerFactory")
		public void listenGroupBananaleaf(String message) {
			System.out.println("Received message in group 'bananaleaf': " + message);
			latch.countDown();
		}

		@KafkaListener(topics = "${employee.topic.name}", groupId = "employee", containerFactory = "employeeConcurrentKafkaListenerContainerFactory")
		public void listenGroupEmployee(Employee employee) {
			System.out.println("Received message in group 'employee': " + employee);
			employeeLatch.countDown();
		}

		@KafkaListener(topics = "${department.topic.name}", groupId = "department", containerFactory = "departmentConcurrentKafkaListenerContainerFactory")
		public void listenGroupDepartment(Department department) {
			System.out.println("Received message in group 'department': " + department);
			departmentLatch.countDown();
		}
	}
}
