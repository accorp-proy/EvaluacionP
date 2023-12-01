package com.primax.web.sec;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;



public class Encoder {

	private static byte[] SALT_BYTES = { (byte) 0x700, (byte) 0x770, (byte) 0x650, (byte) 0x650, (byte) 0x320,
			(byte) 0x300, (byte) 0x310, (byte) 0x34 };
	private static int ITERATION_COUNT = 17;
	public static String strLlaveCifrado = "ecuador::_Estrategico";

	public static String encriptar(final String passPhrase, final String str) {

		Cipher ecipher = null;
		try {
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), SALT_BYTES, ITERATION_COUNT);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT_BYTES, ITERATION_COUNT);
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			byte[] utf8 = str.getBytes("iso-8859-1");
			byte[] enc = ecipher.doFinal(utf8);
			// Encode to base 64
			return new BASE64Encoder().encode(enc);
		} catch (Exception e) {
			return "";
		}
	}

	public static String desencriptar(final String passPhrase, final String str) {

		Cipher dcipher = null;
		try {
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), SALT_BYTES, ITERATION_COUNT);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			dcipher = Cipher.getInstance(key.getAlgorithm());
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT_BYTES, ITERATION_COUNT);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			byte[] dec = new BASE64Decoder().decodeBuffer(str);
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, "iso-8859-1");
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String... dat) {
		String clave = encriptar(Encoder.strLlaveCifrado, "123456");
		System.out.println(clave);
		System.out.println(desencriptar(Encoder.strLlaveCifrado, "Ozlm+AHVa0iwqhPe4CStNg=="));

		Date fechaDesde = null;
		Date fechaHasta = null;
		String fechaDesdeS = null;
		String fechaHastaS = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf_1 = new SimpleDateFormat("DDD");
		String fechaDesdeString = "01-08-2019";
		String fechaHastaString = "30-09-2019";
		try {
			fechaDesde = formatter.parse(fechaDesdeString);
			fechaHasta = formatter.parse(fechaHastaString);
			Calendar date = Calendar.getInstance();
			date.setTime(fechaDesde);
			int year = date.get(Calendar.YEAR) - 1900;
			fechaDesdeS = year + sdf_1.format(fechaDesde);
			Calendar date0 = Calendar.getInstance();
			date.setTime(fechaDesde);
			int year0 = date0.get(Calendar.YEAR) - 1900;
			fechaHastaS = year0 + sdf_1.format(fechaHasta);
			System.out.println("Fecha Desde " + " " + fechaDesdeS);
			System.out.println("Fecha Hasta " + " " + fechaHastaS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
