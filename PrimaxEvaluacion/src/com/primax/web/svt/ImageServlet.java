package com.primax.web.svt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/recognizeme")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = -4042884789452082515L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String URL = req.getParameter("id");
		
		
	}

}
