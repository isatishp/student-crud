package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class StudentCrudApplicationTests {

	@Autowired
	StudentRepository students;

	@Autowired
	TransactionTemplate txt;

	@Test
	void createAndUpdateStudent() throws InterruptedException {

		Student saved = txt.execute(tx -> {
			Student student = new Student();
			return students.save(student);
		});

		Instant createdAt1 = saved.getCreatedAt();
		Instant modifiedAt1 = saved.getModifiedAt();

		Thread.sleep(10);

		Student changed = txt.execute(tx -> {
			saved.setName("new Name");
			return students.save(saved);
		});


		Instant createdAt2 = changed.getCreatedAt();
		Instant modifiedAt2 = changed.getModifiedAt();


		assertThat(modifiedAt1).isBefore(modifiedAt2);
		assertThat(createdAt1).isEqualTo(createdAt2);
	}

}
