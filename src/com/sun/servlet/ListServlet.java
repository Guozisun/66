package com.sun.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.dao.DepartDao;
import com.sun.dao.EmployeeDao;
import com.sun.pojo.Dept;
import com.sun.pojo.Employee;
import com.sun.utils.ChangLiang;
import com.sun.utils.PaginiTion;

import net.sf.json.JSONArray;

@WebServlet("/listServlet")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String path="WEB-INF/employee/";
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html,charset=utf-8");

		String who = request.getParameter("who");
		if (who == null) {
			select(request, response);
		} else if ("add1".equals(who)) {
			add1(request, response);
		} else if ("delete".equals(who)) {
			delete(request, response);

		} else if ("111".equals(who)) {
			select(request, response);
		} else if ("add".equals(who)) {
			add(request, response);
		} else if ("update1".equals(who)) {
			update1(request, response);
		} else if ("update2".equals(who)) {
			update2(request, response);
		} else if ("update".equals(who)) {
			update(request, response);
		} else if ("update2All".equals(who)) {
			update2All(request, response);
		} else if ("ddd".equals(who)) {
			searchByOne(request, response);
		}
	}
	private void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmployeeDao employeeDao = new EmployeeDao();
		Employee condition = new Employee();
		DepartDao departDao = new DepartDao();
		String name = request.getParameter("name");
		String sex =  request.getParameter("sex");
		int age=-1;
		if (request.getParameter("age")!=null&&!"".equals(request.getParameter("age"))) {
			age = Integer.parseInt(request.getParameter("age"));
			
		}
		int deptId =-1;
		if (request.getParameter("deptId")!=null&&!"".equals(request.getParameter("deptId"))) {
			deptId = Integer.parseInt(request.getParameter("deptId"));
			
		}
		Dept dept = new Dept();
		dept.setId(deptId);
		condition.setName(name);
		condition.setSex(sex);
		condition.setAge(age);
		condition.setDept(dept);
		int nowPage = 1;
		if (request.getParameter("page") != null) {
			nowPage = Integer.parseInt(request.getParameter("page"));
		}
		int count = employeeDao.count(condition);
		PaginiTion p = new PaginiTion(nowPage, count, ChangLiang.SIZE_PAGE, ChangLiang.NUM_PAGE);
		List<Employee> list = employeeDao.selectByCondition1(condition,p.getBegin(), ChangLiang.SIZE_PAGE);
		List<Dept>deptList = departDao.selectAll();
		request.setAttribute("p", p);
		request.setAttribute("userList", list);
		request.setAttribute("deptList", deptList);
		request.setAttribute("d_id", deptId);
		request.setAttribute("c", condition);
		request.getRequestDispatcher(path+"list.jsp").forward(request, response);
	}
	protected void showList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmployeeDao employeeDao = new EmployeeDao();
		int nowPage = 1;
		if (request.getParameter("page") != null) {
			nowPage = Integer.parseInt(request.getParameter("page"));
		}
		int count = employeeDao.count();
		PaginiTion p = new PaginiTion(nowPage, count, ChangLiang.SIZE_PAGE, ChangLiang.NUM_PAGE);
		List<Employee> list = employeeDao.selectAll(p.getBegin(), ChangLiang.SIZE_PAGE);
		request.setAttribute("p", p);
		request.setAttribute("userList", list);
		
		request.getRequestDispatcher(path+"list.jsp").forward(request, response);

	}

	protected void add1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DepartDao departDao = new DepartDao();
		List<Dept> deptList=departDao.selectAll();
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher(path+"insert.jsp").forward(request, response);

	}

	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		Employee employee = new Employee();
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		// String age = request.getParameter("age");
		// System.out.println(name);
		int age = -1;
		try {
			age = Integer.parseInt(request.getParameter("age"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer deptId=null;
		if (!"".equals(request.getParameter("deptId"))) {
			deptId = Integer.parseInt(request.getParameter("deptId"));
			
		}
		Dept dept = new Dept();
		dept.setId(deptId);
		
		employee.setName(name);
		employee.setSex(sex);
		employee.setAge(age);
		employee.setDept(dept);
		EmployeeDao employeeDao = new EmployeeDao();
		boolean flag = employeeDao.add(employee);
		if (flag) {

			response.sendRedirect("listServlet");

		} else {

			request.getRequestDispatcher(path+"insert.jsp").forward(request, response);
		}
	}

	protected void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ids = request.getParameter("ids");

		EmployeeDao dao = new EmployeeDao();
		boolean flag = dao.deleteEmployee1(ids);
		if (flag) {
			response.sendRedirect("listServlet");
		} else {
			System.out.println("111");
		}
	}

	protected void update1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids = request.getParameter("ids");
		EmployeeDao dao = new EmployeeDao();
		List<Employee> list = dao.selectByChoose(ids);
		Employee emp = list.get(0);
		
		request.setAttribute("ids", ids);
		request.setAttribute("emp", emp);
		DepartDao departDao = new DepartDao();
		List<Dept> deptList=departDao.selectAll();
		request.setAttribute("deptList", deptList);
		request.getRequestDispatcher(path+"updateAll.jsp").forward(request, response);

	}

	protected void update2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids = request.getParameter("ids");
		EmployeeDao dao = new EmployeeDao();
		List<Employee> list = dao.selectByChoose(ids);
		DepartDao departDao = new DepartDao();
		List<Dept> deptList=departDao.selectAll();
		request.setAttribute("deptList", deptList);
		request.setAttribute("ids", ids);
		request.setAttribute("list", list);
		request.getRequestDispatcher(path+"update.jsp").forward(request, response);

	}

	protected void update2All(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// List<Employee> list=(List<Employee>)request.getAttribute("list");
		// System.out.println(list.size());

		String array = request.getParameter("array");
		JSONArray array2 = JSONArray.fromObject(array);
		List<Employee> list = (List<Employee>) JSONArray.toCollection(array2, Employee.class);
		EmployeeDao dao = new EmployeeDao();
		boolean flag = dao.updateAll(list);
		if (flag) {
			response.sendRedirect("listServlet");
		} else {
			System.out.println("11111");
		}

	}

	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// List<Employee> list=(List<Employee>)request.getAttribute("list");
		// System.out.println(list.size());
		Employee employee = new Employee();
		String ids = request.getParameter("ids");
		String name = request.getParameter("name1");
		String sex = request.getParameter("sex1");
		String age = request.getParameter("age1");
		Integer deptId= null;
		if (!request.getParameter("deptId").equals("")) {
			deptId=Integer.parseInt(request.getParameter("deptId"));
		}
		Dept dept = new Dept();
		dept.setId(deptId);
		employee.setName(name);
		employee.setSex(sex);
		employee.setAge(Integer.parseInt(age));
		employee.setDept(dept);
		EmployeeDao dao = new EmployeeDao();
		boolean flag = dao.updateEmployees1(ids, employee);
		if (flag) {
			response.sendRedirect("listServlet");
		} else {
			System.out.println("11111");
		}

	}

	protected void searchByOne(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sousuo = request.getParameter("sousuo");
		if (sousuo == null) {
			request.getRequestDispatcher("listServlet").forward(request, response);

		}
		EmployeeDao dao = new EmployeeDao();
		Employee employee = dao.selectOne(sousuo);
		System.out.println(employee.getName());
		request.setAttribute("user", employee);
		request.getRequestDispatcher(path+"searchOne.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
