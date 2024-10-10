package bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public login() 
    {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		boolean bo=false;
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Welcome@123");
			PreparedStatement ps=con1.prepareStatement("select * from regi");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) 
			{
				String validusername=rs.getString(9);
				String validpassword=rs.getString(7);
				if(username.equals(validusername) && pass.equals(validpassword))
				{
					bo=true;
					break;
				}
			}
			if(bo==true) 
			{
				HttpSession user = request.getSession(true);
				user.setAttribute("username", username);
				user.setAttribute("pass", pass);
				RequestDispatcher rd = request.getRequestDispatcher("welcome1.html");
				rd.include(request, response);
			}
			else
			{
				String m = "Please Enter Valid Login Credentials";
				PrintWriter out = response.getWriter();
				out.println(m);
				response.setContentType("text/html");
				out.println("<html> <body> <a href='login1.html'>Go Back</a> </body> </html>");
			}
			}
			catch(Exception obj)
			{
				obj.printStackTrace();
			}
	}
}