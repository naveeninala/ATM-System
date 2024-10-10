package bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

@WebServlet("/balance")
public class balance extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public balance() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String uname=request.getParameter("username");
		String pin=request.getParameter("pin");
		PrintWriter pw=response.getWriter();
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Welcome@123");
			PreparedStatement ps1=con.prepareStatement("select balance from regi where username='"+uname+"'"+" and pin="+pin);
			ResultSet rs=ps1.executeQuery();
			long balance=0;
			while(rs.next()) 
			{
				balance=rs.getLong(1);
			}
			response.setContentType("text/html");
			pw.print("<!DOCTYPE html>\r\n"
			+ "<html lang=\"en\">\r\n"
			+ "<head>\r\n"
			+ "<meta charset=\"UTF-8\">\r\n"
			+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "<title>Available Balance</title>\r\n"
			+ "<style>\r\n"
			+ " body {\r\n"
			+ " font-family: Arial, sans-serif;\r\n"
			+ " background-color: #f0f0f0;\r\n"
			+ " text-align: center;\r\n"
			+ " margin: 0;\r\n"
			+ " padding: 0;\r\n"
			+ " }\r\n"
			+ "\r\n"
			+ " .container {\r\n"
			+ " max-width: 400px;\r\n"
			+ " margin: 20px auto;\r\n"
			+ " background-color: #fff;\r\n"
			+ " padding: 20px;\r\n"
			+ " border-radius: 5px;\r\n"
			+ " box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\r\n"
			+ " }\r\n"
			+ "\r\n"
			+ " p {\r\n"
			+ " font-size: 18px;\r\n"
			+ " font-weight: bold;\r\n"
			+ " margin-bottom: 20px;\r\n"
			+ " }\r\n"
			+ "\r\n"
			+ " a {\r\n"
			+ " display: block;\r\n"
			+ " text-decoration: none;\r\n"
			+ " color: #007bff;\r\n"
			+ " margin: 10px 0;\r\n"
			+ " transition: color 0.3s;\r\n"
			+ " }\r\n"
			+ "\r\n"
			+ " a:hover {\r\n"
			+ " color: #0056b3;\r\n"
			+ " }\r\n"
			+ "</style>\r\n"
			+ "</head>\r\n"
			+ "<body>\r\n"
			+ " <div class=\"container\">\r\n"
			+ " <p>The Available Balance is "+ balance +"</p>\r\n"
			+ " <a href=\"welcome1.html\">Home</a>\r\n"
			+ " <a href=\"login1.html\">Log Out</a>\r\n"
			+ " </div>\r\n"
			+ "</body>\r\n"
			+ "</html>\r\n"
			+ "");
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

}
