package com.primax.enm.gen;

import java.io.File;

public enum RutaFileEnum {

	RUTA_PROYECTO_DEPLOYED(getPathProyecto()),
	RUTA_CONTROL_INTERNO(getPath("ControlInterno", "Adjunto")),
	RUTA_NIVEL_EVALUACION(getPath("ControlInterno", "nivelEvaluacion")),
	RUTA_IMAGEN_TEMPORAL("TMP"), 
	RUTA_IMAGEN_TEMP("");

	private RutaFileEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	// File.listRoots()[0].getAbsolutePath()

	private String descripcion;

	public String getDescripcion() {

		return descripcion;
	}

	public static String getPath(String... path) {
		StringBuilder fileDir = new StringBuilder(File.listRoots()[0].getAbsolutePath());
		for (String fl : path) {
			fileDir.append(File.separator + fl);
		}
		return fileDir.toString() + File.separator;
	}

	public static String getPathProyecto(String... path) {
		if (path != null && path.length > 0) {
			String ruta = path[0];
			System.out.println("RUTA POR PATH: " + ruta);
			return ruta;
		} else {
			String ruta = System.getenv("JBOSS_HOME");
			System.out.println("RUTA DEL SERVIDOR: " + ruta);
			return ruta + File.separator + "standalone" + File.separator + "deployments" + File.separator
					+ "PrimaxEvaluacionEAR.ear" + File.separator + "PrimaxEvaluacion.war";
		}
	}
}
