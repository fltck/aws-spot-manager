package com.fltck.aws.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.fltck.aws.manager.service.HeartbeatService;
import com.fltck.aws.manager.service.impl.HeartbeatServiceImpl;


@Configuration
public class RabbitMQConfig {
	@Value("${spring.rabbitmq.host:localhost}") String host;

	@Value("${spring.rabbitmq.exchange:exchange}") String exchange;
	@Value("${spring.rabbitmq.queue:spots}") String queue;
	@Value("${spring.rabbitmq.routingKey.heartbeat:heartbeat}") String routingKeyHeartbeat;

	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(host);
	}

//	@Bean
//	public Queue requests() {
//		return new AnonymousQueue();
//	}
//
//	@Bean
//	public Queue replies() {
//		return new AnonymousQueue();
//	}
	@Bean
	public HeartbeatService receiver() {
 	 	return new HeartbeatServiceImpl();
	}

	@Bean
	public RabbitAdmin admin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

//	@Bean
//	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//	    return new Jackson2JsonMessageConverter();
//	}
//	
//	@Bean
//	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
//	   return new MappingJackson2MessageConverter();
//	}
//	 
//	@Bean
//	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
//	   DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
//	   factory.setMessageConverter(consumerJackson2MessageConverter());
//	   return factory;
//	}
//	 
//	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
//	   registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
//	}
	
//	@Bean
//	public Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper jsonObjectMapper) {
//		Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(jsonObjectMapper);
//		return converter;
//	}
//	
//	@Bean
//	public ObjectMapper jsonObjectMapper() {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.registerModule(new JavaTimeModule());
//		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//		return mapper;
//	}
	
	@Bean
	public RabbitTemplate template(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//	    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter(jsonObjectMapper()));
		return rabbitTemplate;
	}

	@Bean
	public TopicExchange topic() {
		return new TopicExchange(exchange);
	}
	

	@Bean
	public Queue spotQueue() {
		return new Queue(queue);
	}

	@Bean
	public Binding binding(TopicExchange topic, Queue spotQueue) {
		return BindingBuilder.bind(spotQueue).to(topic).with(routingKeyHeartbeat);
	}

//	@Bean
//	@Primary
//	public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//		container.setQueueNames(replies().getName());
//		return container;
//	}
//
//	@Bean
//	public AsyncRabbitTemplate asyncTemplate(RabbitTemplate template,
//			SimpleMessageListenerContainer container) {
//		return new AsyncRabbitTemplate(template, container);
//	}
//
//	@Bean
//	public SimpleMessageListenerContainer remoteContainer(ConnectionFactory connectionFactory) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//		container.setQueueNames(requests().getName());
//		container.setMessageListener(new MessageListenerAdapter(new Object() {
//
//			@SuppressWarnings("unused")
//			public String handleMessage(String message) {
//				return message.toUpperCase();
//			}
//
//		}));
//		return container;
//	}

} 