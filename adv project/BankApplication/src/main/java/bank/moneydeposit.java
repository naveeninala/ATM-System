package bank;

import java.io.IOException;
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

@WebServlet("/moneydeposit")
public class moneydeposit extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public moneydeposit() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		long money=Long.parseLong(request.getParameter("money"));
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Welcome@123");
			HttpSession session=request.getSession(false);
			String uname=(String)session.getAttribute("username");
			String pin=(String)session.getAttribute("pin");
			PreparedStatement ps1=con.prepareStatement("select balance from regi where username='"+uname+"'");
			ResultSet rs=ps1.executeQuery();
			long mon=0;
			while(rs.next()) 
			{
				mon=rs.getLong(1);
			}
			money=money+mon;
			PreparedStatement ps=con.prepareStatement("update regi set balance =? where username=? and pin=?");
			ps.setLong(1, money);
			ps.setString(2, uname);
			ps.setString(3, pin);
			ps.executeUpdate();
			response.sendRedirect("depositdone.html");
			session.invalidate();
		}
		catch(Exception e) {
			e.printStackTrace();
			}
		}
	}
