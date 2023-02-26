package DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registration")
public class DataBaseStorage extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		RequestDispatcher rd=null;
		Connection con=null;
		
		String urname=req.getParameter("name");
		String uremail=req.getParameter("email");
		String urpassword=req.getParameter("pass");
		String rpassword=req.getParameter("re_pass");
		String urmobile=req.getParameter("contact");
	     
		if(urname==null || urname.equals("")){
			req.setAttribute("status", "InavlidUrname");
			rd=req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
		}
		if(uremail==null || uremail.equals("")){
			req.setAttribute("status", "InvaliUremail");
			rd=req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
		}
		
		if(urpassword==null || urpassword.equals("")){
			req.setAttribute("status","InvalidUrpassword");
			rd=req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
		}
		else if(!rpassword.equals(urpassword)) {
			req.setAttribute("status", "InvalidConformPassword");
			rd=req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
		}
		if(urmobile==null || urmobile.equals("")) {
			req.setAttribute("status", "InvalidUrmobile");
			rd=req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
			
		}
		else if(urmobile.length()!=10)
		{
			req.setAttribute("status", "InvalidUrmobileLength");
			rd=req.getRequestDispatcher("registration.jsp");
			rd.forward(req, resp);
			
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3307/employee","root","root");
			
			System.out.println("connection is established");
			
			
			String query=("insert into stuff(Name,Email,Password,ContactNo) values(?,?,?,?)");
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, urname);
			ps.setString(2, uremail);
			ps.setString(3, urpassword);
			ps.setString(4, urmobile);
			
			int rowCount=ps.executeUpdate();
			rd=req.getRequestDispatcher("registration.jsp");
			if(rowCount>0){
				req.setAttribute("status", "success");
			}
			else {
				req.setAttribute("status", "failed");
			}
			
			rd.forward(req, resp);
			
			
			
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

}
