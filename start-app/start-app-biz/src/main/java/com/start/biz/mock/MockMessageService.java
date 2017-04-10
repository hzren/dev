package com.start.biz.mock-app.biz.mock;

import java.util.Date;

import commc.receiver.api.enumeration.SendPlanEnum;
import commc.receiver.api.result.SMSResult;
import commc.receiver.api.service.MessageService;

/**
 * 
 * DO nothing , 只是返回一个空结果
 * 
 * */
public class MockMessageService implements MessageService{

	@Override
    public SMSResult sendSMS(String templateCode, String mobile, String content, String requestIp) {
		SMSResult result = new SMSResult();
		result.setRequestId(String.valueOf(System.currentTimeMillis()));
	    return result;
    }

	@Override
    public SMSResult sendSMS(String templateCode, String mobile, String content, SendPlanEnum sendPlan, Date planTime, String requestIp) {
		SMSResult result = new SMSResult();
		result.setRequestId(String.valueOf(System.currentTimeMillis()));
	    return result;
    }

}
