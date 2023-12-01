package com.primax.web.svt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/storeImages")
public class IdentifierServlet extends HttpServlet {

	private static final long serialVersionUID = -4042884789452082515L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String URL = req.getParameter("URL");
		File archivo = new File(URL);
		FileInputStream fis = new FileInputStream(archivo);
		OutputStream out = resp.getOutputStream();
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = fis.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		out.close();
		fis.close();
	}

}
