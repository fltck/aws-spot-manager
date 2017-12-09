/**
 * 
 */
package com.fltck.aws.manager.service;

import org.springframework.amqp.core.Message;

import com.fltck.aws.manager.entity.HeartbeatMessage;

/**
 * @author brentlemons
 *
 */
public interface HeartbeatService {
	
	public Object receive(Message message);

}
