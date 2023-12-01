package com.primax.srv.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;

import com.primax.enm.gen.RutaFileEnum;
import com.primax.srv.idao.IGeneralUtilsDao;

@Stateless
public class GeneralUtilsDao implements IGeneralUtilsDao {

	public String creaRuta(Long idObjeto, String pathFolder) {

		File folder = new File(pathFolder + File.separator + idObjeto + File.separator);
		System.out.println("crea carpeta folder ==> " + folder);
		folder.mkdirs();
		folder.setWritable(true);
		System.out.println("retorna carpeta folder ==> " + folder.getPath().toString());
		return folder.getPath().toString() + File.separator;
	}

	public void copyFile(String fileName, InputStream in, String destino) {
		if (in != null) {
			try {
				OutputStream out = new FileOutputStream(new File(destino + fileName), false);
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				in.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void copyFile0(String fileName, InputStream in, String destino) {
		if (in != null) {
			try {
				OutputStream out = new FileOutputStream(new File(destino + fileName), false);
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				in.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Permite crear un archivo temporal para almacenar la Imagen
	 * 
	 * @param bytes
	 * @param nombreArchivo
	 * @return
	 */
	public String guardaBlobEnFifheroTemporal(InputStream stream, String destino, Long codObj, String subfolder, String nombreArchivo, boolean condicion) {

		String ubicacionImagen = null;
		String path = null;
		try {
			// stream.reset();
			if (stream.markSupported()) {
				stream.reset();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (condicion) {
			path = destino + subfolder + nombreArchivo;
		} else {
			path = destino + File.separatorChar + subfolder + File.separatorChar + nombreArchivo;
		}
		File f = new File(path);

		try (FileOutputStream out = new FileOutputStream(f.getAbsolutePath())) {
			int c = 0;
			while ((c = stream.read()) >= 0) {
				out.write(c);
			}
			out.flush();
			if (condicion) {
				ubicacionImagen = destino + nombreArchivo;
			} else {
				ubicacionImagen = destino + File.separator + subfolder + File.separator + nombreArchivo;
			}

		} catch (Exception e) {
			System.err.println("No se pudo Cargar la Imagen");
		}

		return ubicacionImagen;

	}

	public String guardaImagenTemporal(byte[] bytes, String pathProyecto, String subFolder, Long codObj, String nombreArchivo, boolean condicion) {

		if (bytes == null)
			return "";

		String ubicacionImagen = null;
		String path = null;

		if (condicion) {
			path = pathProyecto + File.separatorChar + RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion() + File.separatorChar + subFolder
					+ File.separatorChar + codObj + File.separatorChar + nombreArchivo;
		} else {
			path = pathProyecto + File.separatorChar + RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion() + File.separatorChar + nombreArchivo;
		}

		File f = null;
		InputStream in = null;

		try {

			f = new File(path);
			if (!f.exists()) {
				in = new ByteArrayInputStream(bytes);
				FileOutputStream out = new FileOutputStream(f.getAbsoluteFile());
				int c = 0;
				while ((c = in.read()) >= 0) {
					out.write(c);
				}
				out.flush();
				out.close();
			}

			// ubicacionImagen = "/storeImages?URL=" + path;
			ubicacionImagen = path;

		} catch (Exception e) {
			System.err.println("No se pudo Cargar la Imagen");
			e.printStackTrace();
		}

		return ubicacionImagen;

	}

	public InputStream getFileFromPath(String path) {
		File file = new File(path);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date getPrimerDiaDelMes() {

		Calendar cal = Calendar.getInstance();

		cal.set(cal.get(Calendar.YEAR),

				cal.get(Calendar.MONTH),

				cal.getActualMinimum(Calendar.DAY_OF_MONTH),

				cal.getMinimum(Calendar.HOUR_OF_DAY),

				cal.getMinimum(Calendar.MINUTE),

				cal.getMinimum(Calendar.SECOND));

		return cal.getTime();

	}

	public Date getUltimoDiaDelMes() {

		Calendar cal = Calendar.getInstance();

		cal.set(cal.get(Calendar.YEAR),

				cal.get(Calendar.MONTH),

				cal.getActualMaximum(Calendar.DAY_OF_MONTH),

				cal.getMaximum(Calendar.HOUR_OF_DAY),

				cal.getMaximum(Calendar.MINUTE),

				cal.getMaximum(Calendar.SECOND));

		return cal.getTime();

	}

}
