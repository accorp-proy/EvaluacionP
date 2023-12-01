package com.primax.bean.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;

import com.sun.xml.bind.marshaller.DataWriter;

@XmlTransient
public class XmlBind {

	/**
	 * Marshall this object to xml-utf8 composition
	 * 
	 * @return String xml
	 */
	protected <T> String parsetoXMl() {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(this.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new JaxbCharacterEscapeHandler());
			jaxbMarshaller.marshal(this, dataWriter);
			return new String(stringWriter.toString().getBytes(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * For general purpose it is necessary to store this object in another field
	 * or the same where this method was called
	 * 
	 * @param xml
	 * @return object with xml data
	 */
	@SuppressWarnings("unchecked")
	protected <T> T parsetoObject(String xml) {
		InputStream xmlInput = null;
		try {
			xmlInput = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
			Unmarshaller jaxbUnmarshaller;
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Object result = jaxbUnmarshaller.unmarshal(xmlInput);
			return (T) result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				xmlInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
