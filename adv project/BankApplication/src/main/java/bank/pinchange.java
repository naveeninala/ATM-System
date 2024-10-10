package bank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pinchange")
public class pinchange extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public pinchange() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String username=request.getParameter("username");
		int cpin=Integer.parseInt(request.getParameter("cpin"));
		int npin=Integer.parseInt(request.getParameter("npin"));
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Welcome@123");
			PreparedStatement ps=con.prepareStatement("update regi set pin="+npin+" where pin= "+cpin+" and username='"+username+"'");
			ps.executeUpdate();	
			response.sendRedirect("pindone.html");
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

}
