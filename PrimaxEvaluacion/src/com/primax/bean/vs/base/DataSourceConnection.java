package com.primax.bean.vs.base;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceConnection {

	public static Connection getJNDIConnection() {
		String DATASOURCE_CONTEXT = "java:/PrimaxDBE";

		Connection result = null;
		try {
			Context initialContext = new InitialContext();

			DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
			if (datasource != null) {
				result = datasource.getConnection();
			} else {
				System.err.println("Error obtenidendo conexión de datasource :" + DATASOURCE_CONTEXT);
			}
		} catch (NamingException ex) {
			System.err.println("No JNDI found " + ex);
		} catch (SQLException ex) {
			System.err.println("Error @ getting connection: " + ex);
		}
		return result;
	}
}
