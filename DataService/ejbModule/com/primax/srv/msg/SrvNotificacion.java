package com.primax.srv.msg;

import javax.ejb.Stateless;
import javax.mail.MessagingException;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.enm.gen.MessageType;
import com.primax.srv.imsg.ISrvNotificacion;
import com.primax.srv.util.msg.MessageCenter;
import com.primax.srv.util.msg.MessageFactory;

@Stateless
public class SrvNotificacion extends BaseNaming implements ISrvNotificacion {

	@Override
	public void enviarMensaje(MessageType Messenger, String destinatario, String asunto, String Mensaje) throws MessagingException {

		MessageCenter messenger = new MessageFactory(Messenger).getMessenger();
		messenger.setMessage(Mensaje);
		messenger.setRecipient(destinatario);
		messenger.setSubject(asunto);
		if (Messenger.equals(MessageType.MAIL)) {
			messenger.setFrom("giftcard@primax.com.ec");
			/*
			 * IParametrolGeneralDao iparametroGeneral =
			 * EJB(EnumNaming.IParametroDao); List<ParametrosGeneralesEt>
			 * paramConfig =
			 * iparametroGeneral.getObjPadre(ParametrosGeneralesEnum.
			 * CONFIGURACION_CORREOS.getDescripcion()).getParametros();
			 * paramConfig.size(); messenger.setConfig(paramConfig);
			 * iparametroGeneral.remove();
			 */
		}
		messenger.sendMessage();
	}

}
