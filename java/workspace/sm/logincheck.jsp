<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.sql.Statement" %>
<%@page import="com.hj.ConnectionManager" %>
<%@page import="java.sql.*" %>
<%
String sid = request.getParameter("id");
String spass = request.getParameter("pw");

Statement stmt = null;
ResultSet rs = null;
ConnectionManager connMgr = new ConnectionManager();
stmt = connMgr.getStatement();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
var success = "";

</script>
<%
String sql = "select * from member where id = '"+sid+"' and pass = '"+spass+"'";
rs = stmt.executeQuery(sql);

if(rs.next())
{
	session.setAttribute("name", rs.getString(4));
	session.setAttribute("id", sid);
%>
	<script>
		alert("<%=sid%>님 반갑습니다.");
		success = "Y";
	</script>
<%	
}
else 
{
%>
	<script>
		alert("아이디를 확인해주세요.");
		history.back();
	</script>
<%	
}
connMgr.freeStatement();
%>
<script>
	if (success == "Y")
		{
		opener.document.location.href="lobby.jsp";
		self.close();
	}
</script>
</body>
</html>
