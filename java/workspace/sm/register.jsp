<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Statement" %>
<%@page import="com.hj.ConnectionManager" %>
<%@page import="java.sql.*" %>
<%
String seq = request.getParameter("seq");
String sel_yn = request.getParameter("sel_yn");
Statement stmt = null;
ResultSet rs = null;
ResultSet drs = null;
ConnectionManager connMgr = new ConnectionManager();

boolean areadyUse = false;
try{

	stmt = connMgr.getStatement();
	rs = stmt.executeQuery("select * from carpool.driver where seq = '"+seq+"' and id = '"+session.getAttribute("id")+"'");
	if(rs.next())
	{
			areadyUse = true;
		
	}
	else
	{
			String isql = "insert into carpool.driver (seq,id,sel_yn) values ('"+seq+"','"+session.getAttribute("id")+"','N')";
			stmt.executeUpdate(isql);
		
	}
	
	if(stmt != null)
	{
		stmt.close();
		
	}
	if(areadyUse == false)
	{
		%>
		<script>
		
		alert("지원되었습니다.");
		opener.document.location.href="content.jsp?seq=<%=seq%>";
		self.close();
		</script>
		<%	
	}else
	{
		%>
		<script>
		alert("이미지원되었습니다.")
		opener.document.location.href="content.jsp?seq=<%=seq%>";
		self.close();
		</script>
		<%	
	}
}
catch(Exception e)
{
	if(stmt != null)
	{
		stmt.close();
		
	}
	%>
	<script>
	alert("지원에 실패하였습니다.")
	window.close();
	</script>
	
<%	
}
%>

</body>
</html>