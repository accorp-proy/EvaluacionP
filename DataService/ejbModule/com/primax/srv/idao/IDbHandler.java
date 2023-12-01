package com.primax.srv.idao;

import java.sql.Connection;
import java.sql.SQLException;

import com.primax.enm.gen.DBTypeLib;
import com.primax.enm.gen.DbTypeEnum;

public interface IDbHandler {

	public Connection getConexion(DbTypeEnum tipo, DBTypeLib lib) throws SQLException;

	public void remove();

}
