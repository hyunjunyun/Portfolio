<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Statement" %>
<%@page import="com.hj.ConnectionManager" %>
<%@page import="java.sql.*" %>
<%
String sel = request.getParameter("sel");
String selId = request.getParameter("selId");
String selSeq = request.getParameter("seq");
Statement stmt = null;
ResultSet rs = null;
ResultSet drs = null;
ConnectionManager connMgr = new ConnectionManager();
stmt = connMgr.getStatement();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>상세내용</title>
</head>
<script>
<%
try
{
	stmt.executeUpdate("update driver set sel_yn = 'Y' where id = '"+selId+"' and seq = '"+selSeq+"'");	
	stmt.executeUpdate("update driver set sel_yn = 'N' where id != '"+selId+"' and seq = '"+selSeq+"'");
	if(stmt != null)
	{
		stmt.close();
		
	}
%>
alert("저장되었습니다.");
opener.document.location.href="content.jsp?seq="+<%=selSeq%>;
self.close();
<%
}catch(Exception e)
{
	if(stmt != null)
	{
		stmt.close();
		
	}
%>
alert("저장에 실패하였습니다.");
opener.document.location.href="content.jsp";
self.close();
<%
}
%>
</script>
<body>
</body>
</html>