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

@WebServlet("/registration")
public class registration extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public registration() 
    {
        super();
    }
    public static String accountno()
    {
    	String Accountnumber = 999999999+Math.round(Math.random()*999999999)+"";
    	return Accountnumber;
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String fn = request.getParameter("fn");
		String ln = request.getParameter("ln");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String username = request.getParameter("un");
		String Aadhar = request.getParameter("Aadhar");
		String pan = request.getParameter("pan");
		String pass = request.getParameter("pass");
		String newpass = request.getParameter("newpass");
		String dob = request.getParameter("dob");
		String pin = request.getParameter("pin");
		if(!fn.isEmpty()||!ln.isEmpty()||!email.isEmpty()||!pass.isEmpty()||!newpass.isEmpty()||!Aadhar.isEmpty()||!pan.isEmpty()||!dob.isEmpty()||!username.isEmpty())
		{
			if(emailverification(email)==true)
			{
				if(AadharVerification(Aadhar)==true)
				{
					if(panVerification(pan)==true)
					{
						if(passwordVerification(pass)==true)
						{
							if(newpass.equals(pass))
							{
								try
								{
									Class.forName("com.mysql.cj.jdbc.Driver");
									Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Welcome@123");
									PreparedStatement pst = con.prepareStatement("insert into regi(firstname,lastname,mobilenumber,emailid,Aadhar,pan,pass,dob,username,pin) values(?,?,?,?,?,?,?,?,?,?)");
									pst.setString(1, fn);
									pst.setString(2, ln);
									pst.setString(3, mobile);
									pst.setString(4, email);
									pst.setString(5, Aadhar);
									pst.setString(6,pan);
									pst.setString(7, pass);
									pst.setString(8, dob);
									pst.setString(9, username);
									pst.setString(10, pin);
									pst.executeUpdate();
									String acc = accountno();
									boolean accnum = false;
									PreparedStatement pst1 = con.prepareStatement("select AccountNumber from regi");
									ResultSet rs1 = pst1.executeQuery();
									while(rs1.next())
									{
										String acc1 = rs1.getString(1)+"";
										if(acc1.equals(acc))
										{
											accnum = false;
											break;
										}
										else
										{
											accnum = true;
										}
									}
									if(accnum == true)
									{
										PreparedStatement pst2 = con.prepareStatement("update regi set AccountNumber = ? where username = ?");
										pst2.setString(1, acc);
										pst2.setString(2,username);
										pst2.executeUpdate();
									}
									PrintWriter out = response.getWriter();
									out.println("Registered SucessFully Please Login");
									RequestDispatcher rd = request.getRequestDispatcher("registrationcompleted.html");
									rd.forward(request, response);
								}
								catch(Exception obj)
								{
									obj.printStackTrace();
								}
							}
							else
							{
								String m = "Password Doesn't Match";
								PrintWriter out = response.getWriter();
								out.println(m);
								response.setContentType("text/html");
								out.println("<html> <body> <a href='register.html'>Go Back</a> </body> </html>");
							}
						}
						else
						{
							PrintWriter out = response.getWriter();
							out.println("Password Should Contain  9 characters and  must at least one capital,  one small, one number and one special character");
							response.setContentType("text/html");
							out.println("<html> <body> <a href='register.html'>Go Back</a> </body> </html>");
						}
					}
					else
					{
						PrintWriter out = response.getWriter();
						out.println("Please Enter a Valid PAN Number");
						response.setContentType("text/html");
						out.println("<html> <body> <a href='register.html'>Go Back</a> </body> </html>");
					}
				}
				else
				{
					PrintWriter out = response.getWriter();
					out.println("Please Enter a Valid Aadhar Number");
					response.setContentType("text/html");
					out.println("<html> <body> <a href='register.html'>Go Back</a> </body> </html>");
				}
			}
			else
			{
				String m = "Please Enter Valid E-mail Adress";
				PrintWriter out = response.getWriter();
				out.println(m);
				response.setContentType("text/html");
				out.println("<html> <body> <a href='register.html'>Go Back</a> </body> </html>");
			}
		}
		else
		{
			PrintWriter out = response.getWriter();
			out.println("Fields Must Not Be Empty");
			RequestDispatcher rd = request.getRequestDispatcher("register.html");
			rd.forward(request, response);
		}
	}
	private boolean emailverification(String s) 
	{
		int y = s.indexOf("@");
		int u= s.indexOf("@",y+1);
		int p= s.lastIndexOf("@",y-1);
		String errorM = "";
		if(p==-1&&u==-1&&y!=-1&&s.length()>=15&&s.length()<=50)
		{
		    if(!Character.isUpperCase(s.charAt(0)))
    		  {
    		      return false;
    		  }
    	    if(!Character.isLetter(s.charAt(y-1))&&!Character.isDigit(s.charAt(y-1)))
    		  {
    		      return false; 
    		  }
    		if(errorM.isEmpty()) 
    		{
        		for(int i=1;i<y-1;i++)
        		{
        		    if(!Character.isLetter(s.charAt(i))&&!Character.isDigit(s.charAt(i)))
        		    {
        		        if(s.charAt(i)!='.'&&s.charAt(i)!='_')
        		        {
        		            return false;
        		        }
        		    }
        		}
        		for(int i=y+1;i<s.length();i++)
        		{
        		    if(Character.isDigit(s.charAt(i)))
        		    {
        		        return false;
        		    }
        		    else if(!Character.isLetter(s.charAt(i))&&(s.charAt(i)!='.'))
        		    {
        		        return false;
        		    }
        		}
    		}
    		if(errorM.isEmpty())
    		{
    		    return true;
    		}
    		else
    			return false;
		}
		else
			return false;
	}
	private boolean passwordVerification(String s)
	{
		int n=s.length();
	    int cc=0,sc=0,dc=0,spc=0,sp=0;
	    if(n>=9)
	    {
	        for(int i=0;i<n;i++)
	        {
	            char ch=s.charAt(i);
	            if(Character.isUpperCase(ch))
	            {
	                cc++;
	            }
	            else if(Character.isLowerCase(ch))
	            {
	                sc++;
	            }
	            else if(Character.isDigit(ch))
	            {
	                dc++;
	            }
	            else if(ch==' ')
	            {
	                sp++;
	            
	            }
	            else
	            {
	                spc++;
	            }
	        }
	        if(cc>=1&&sc>=1&&dc>=1&&sp==0&&spc>=1)
	        {
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }
	    else
	    	return false;
	}
	private boolean AadharVerification(String n)
	{
		int k = n.length();
        if(k==14||k==12)
        {
            if(k==12)
            {
                int c = 0;
                for(int i = 0;i<k;i++)
                {
                    char ch = n.charAt(i);
                    if(ch>='0'&&ch<='9')
                    {
                        c++;
                    }
                    else
                        break;
                }
                if(c==12)
                    return true;
                else
                    return false;
            }
            else
            {
                int c = 0;
                for(int i = 0;i<k;i++)
                {
                    char ch = n.charAt(i);
                    if(ch>='0'&&ch<='9'&&i!=4&&i!=9)
                    {
                        c++;
                    }
                    else if((i==4||i==9)&&ch==' ')
                    {
                        c++;
                    }
                    else
                        break;
                }
                if(c==14)
                    return true;
                else
                    return false;
            }
        }
        return false;
	}
	private boolean panVerification(String s)
	{
		int k = s.length();
        if(k==10)
        {
            int c = 0;
            for(int i = 0;i<k;i++)
            {
                char ch = s.charAt(i);
                if((i==0||i==1||i==2||i==4||i==9)&&(ch>='A'&&ch<='Z'))
                {
                    c++;
                }
                else if((i==3)&&(ch=='P'||ch=='C'||ch=='F'||ch=='H'||ch=='A'||ch=='T'))
                {
                    c++;
                }
                else if((i==5||i==6||i==7||i==8)&&(ch>='0'&&ch<='9'))
                {
                    c++;
                }
                else
                {
                    break;
                }
            }
            if(c==10)
            {
                return true;
            }
            else
                return false;
        }
		return false;
	}
}