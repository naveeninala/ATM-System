package bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/deposit")
public class deposit extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public deposit() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String username = request.getParameter("username");
		String pin = request.getParameter("pin");
		PrintWriter out = response.getWriter();
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Welcome@123");
			PreparedStatement ps=con1.prepareStatement("select username,pin from regi");
			ResultSet rs=ps.executeQuery();
			boolean bo = false;
			while(rs.next()) 
			{
				String validusername=rs.getString(1);
				String validpassword=rs.getString(2);
				if(username.equals(validusername) && pin.equals(validpassword))
				{
					bo=true;
					break;
				}
			}
			response.setContentType("text/html");
			if(bo==true) 
			{
				HttpSession user = request.getSession(true);
				user.setAttribute("username", username);
				user.setAttribute("pin", pin);
				response.sendRedirect("moneydeposit.html");
			}
			else
			{
				String m = "Please Enter Valid Login Credentials";
				PrintWriter out1 = response.getWriter();
				out1.println(m);
				response.setContentType("text/html");
				out.println("<html> <body> <a href='deposit.html'>Go Home</a> </body> </html>");
			}
		}
		catch(Exception obj)
		{
			obj.printStackTrace();
		}
	}
}
