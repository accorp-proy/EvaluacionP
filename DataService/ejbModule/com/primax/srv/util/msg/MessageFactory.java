package com.primax.srv.util.msg;

import com.primax.enm.gen.MessageType;

public class MessageFactory {

	MessageCenter messager = null;

	public MessageFactory(MessageType messageType) {

		switch (messageType) {
		case MAIL:
			MailSender mail = new MailSender();
			messager = mail;
			break;
		case SMS:
			break;
		}

	}

	public MessageCenter getMessenger() {
		return messager;
	}

}
