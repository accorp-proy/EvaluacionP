package EstadoServidorEnum;

public enum GestionRealizadaClienteEnum {
	EMAIL("EMAIL"),
	LLAMADA("LLAMADA"), 
	VISITA("VISITA");

	private String descripcion;

	GestionRealizadaClienteEnum(String descripcion) {
		this.descripcion = descripcion;

	}

	public String getDescripcion() {
		return this.descripcion;
	}
}
