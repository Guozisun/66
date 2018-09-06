package com.sun.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.dao.UserDao;

@WebServlet("/user")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 通过隐藏域判断是进入登录页面还是回显数据
		String type = request.getParameter("hedden");
		// 通过url进入判断type
		if (type == null) {
			showLogin(request, response);
		} else if ("login".equals(type)) {
			dologin(request, response);

		}else if ("loginout".equals(type)) {
			loginout(request, response);

		}

	}

	protected void showLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/main/login.jsp").forward(request, response);
	}

	protected void dologin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String name = request.getParameter("username");
		String pwd = request.getParameter("password");

		UserDao userDao = new UserDao();
		boolean flag = userDao.selcet1(name, pwd);
		if(flag) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("username", name);
			httpSession.setMaxInactiveInterval(1*60);
			out.print(flag);
			
		}
		
		

	}
	protected void loginout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			request.getSession().invalidate();
			request.getSession().removeAttribute("username");
			request.getRequestDispatcher("user").forward(request, response);
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
