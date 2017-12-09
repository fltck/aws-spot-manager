/**
 * 
 */
package com.fltck.aws.manager.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fltck.aws.manager.entity.HeartbeatMessage;
import com.fltck.aws.manager.service.HeartbeatService;

/**
 * @author brentlemons
 *
 */
public class HeartbeatServiceImpl implements HeartbeatService {

	private static final Logger logger = LoggerFactory.getLogger(HeartbeatServiceImpl.class);

	@RabbitListener(queues = "#{spotQueue.name}")

	@Override
	public Object receive(final Message message) {
		try {
	        ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			HeartbeatMessage heartbeat = mapper.readValue(message.getBody(), HeartbeatMessage.class);
			logger.info("heartbeat received: " + heartbeat.toString());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
