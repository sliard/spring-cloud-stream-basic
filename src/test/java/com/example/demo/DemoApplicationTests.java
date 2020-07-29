package com.example.demo;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
class DemoApplicationTests {

	@Autowired
	@Qualifier("getPrice-in-0")
	MessageChannel input;

	@Autowired
	@Qualifier("getPrice-out-0")
	MessageChannel output;

	@Autowired
	MessageCollector collector;

	@Autowired
	StreamService service;

	@Test
	void testMessages() {
		this.input.send(new GenericMessage<>(new Item("Tomato", 20)));
		BlockingQueue<Message<?>> messages = this.collector.forChannel(this.output);
		assertThat(messages,receivesPayloadThat(CoreMatchers.is("20")));
	}

	@Test
	void testFunction() {
		assertEquals(20,service.getPrice().apply(new Item("Tomato", 20)).intValue());
	}

}
