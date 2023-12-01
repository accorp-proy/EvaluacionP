package com.primax.enm.msg;

public enum Mensajes {

	_GRABAR("Información guardada exitosamente"),
	 _ERRORGRABAR("Error al grabar"),
	 _DATATABLE("No existe coincidencias"),
	 _ERRORCEDULA("Error: no se encuentra a Identificación y/o no hay conexion a internet"),
	 _ERROR_REFERENCIA("Error: no se puede ingresar el objeto Referencia en nulo"),
	 _ERROR_REFERENCIA_EXISTE("Error: referencia ya existe"),
	 _ERROR_CEDULA("Error: Cédula debe contener 10 digitos"),
	 _ERROR_RUC("Error: Ruc debe contener 13 digitos"),
	 _ERROR_UPLOAD_DOCUMENTO("Error: no se puede subir documento"),
	 _ERROR_FUNCIONARIO_EXISTE("Error: funcionario ya existe");
	
	private Mensajes(String descripcion) {
		this.descripcion = descripcion;
	}

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

}
