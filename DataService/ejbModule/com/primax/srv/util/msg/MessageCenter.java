package com.primax.srv.util.msg;

import java.util.Map;

import com.primax.jpa.param.CorreoEt;

public interface MessageCenter {

	public void setMessage(String message);

	public void setRecipient(String receiver);

	public void setSubject(String subject);

	public void setFrom(String from);

	public boolean sendMessage();

	public void setConfig(CorreoEt config);	

	void setAttachtment(Map<String, byte[]> filebyte);

}
