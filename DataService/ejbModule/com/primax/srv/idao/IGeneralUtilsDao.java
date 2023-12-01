package com.primax.srv.idao;

import java.io.InputStream;
import java.util.Date;

public interface IGeneralUtilsDao {

	public void copyFile(String fileName, InputStream in, String destino);

	public void copyFile0(String fileName, InputStream in, String destino);

	public String creaRuta(Long idObjeto, String pathFolder);

	public String guardaBlobEnFifheroTemporal(InputStream stream, String destino, Long codObj, String subfolder, String nombreArchivo, boolean condicion);

	public String guardaImagenTemporal(byte[] bytes, String pathProyecto, String subFolder, Long codObj, String nombreArchivo, boolean condicion);

	public InputStream getFileFromPath(String path);

	public Date getPrimerDiaDelMes();

	public Date getUltimoDiaDelMes();

}
