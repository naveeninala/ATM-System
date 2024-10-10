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
import javax.servlet.http.HttpSession;

@WebServlet("/userinfo")
public class userinfo extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public userinfo() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession user = request.getSession(false);
		String s1 = (String)user.getAttribute("username");
		user.getAttribute("pass");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Welcome@123");
			PreparedStatement pst = con.prepareStatement("select*from regi where username = ?");
			pst.setString(1, s1);
	        ResultSet rs=pst.executeQuery();
	        
	        out.println("<html><body>");
	        out.println("<h1><center>User Info</center></h1>");
	        out.println("<center><table border='1'><tr><th>Name</th><th>Mobile</th><th>E-mail</th><th>DOB</th><th>UserName</th><th>AccountNumber</th><th>Available Balance</th></tr>");
            while (rs.next()) 
            {
                String name = rs.getString(1)+" "+rs.getString(2);
                String mob = rs.getString(3);
                String email = rs.getString(4);
                String dob = rs.getString(8);
                String username = rs.getString(9);
                String acc = rs.getString(10);
                long balance = rs.getLong(12);
                out.println("<tr><td>" + name + "</td><td>" + mob + "</td><td>" + email + "</td><td>" + dob + "</td><td>" + username + "</td><td>" + acc + "</td><td>"+ balance + "</td></tr>");
            }
            out.println("</table></center>");
            out.println("</body></html>");

	            rs.close();
	            pst.close();
	            con.close();
		}
		catch(Exception obj)
		{
			obj.printStackTrace();
		}
	}

}
