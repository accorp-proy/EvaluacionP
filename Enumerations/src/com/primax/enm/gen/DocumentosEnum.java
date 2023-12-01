package com.primax.enm.gen;

public enum DocumentosEnum {

    XLSXPROYECTOSEE(0, null, 13);

    private Integer _desde, _filas, _columnas;

    DocumentosEnum(Integer desde, Integer filas, Integer columnas) {
	_desde = desde;
	_filas = filas;
	_columnas = columnas;
    }

    public Integer getDesde() {
	return this._desde;
    }

    public Integer getFilas() {
	return this._filas;
    }

    public Integer getColumnas() {
	return this._columnas;
    }
}
