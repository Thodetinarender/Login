package DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class login extends HttpServlet{
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  
		Connection con=null;
		HttpSession s=req.getSession();
		
		RequestDispatcher d=null;
		String uname=req.getParameter("username");
		String upswd=req.getParameter("password");
		
		if(uname==null || uname.equals(""))
		{
			req.setAttribute("status", "InvalidName");
			d=req.getRequestDispatcher("login.jsp");
	
		}
		if(upswd==null || upswd.equals(""))
		{
			req.setAttribute("status", "InvalidPassword");
			d=req.getRequestDispatcher("login.jsp");
		
		}
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3307/employee","root","root");
			System.out.println("connection is established");
			
			String query="select * from stuff where Name = ? and Password = ?";
			
			PreparedStatement ps=con.prepareStatement(query);
		    ps.setString(1, uname);
		    ps.setString(2, upswd);
		    
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				s.setAttribute("name", rs.getString("Name"));
				d=req.getRequestDispatcher("index.jsp");
			}
			else {
				req.setAttribute("status","failed");
				d=req.getRequestDispatcher("login.jsp");
			}
			
			d.forward(req, resp);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
	}

}
