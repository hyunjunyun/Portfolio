<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Statement" %>
<%@page import="com.hj.ConnectionManager" %>
<%@page import="java.sql.*" %>
<%
String sseq = request.getParameter("seq");
Connection conn = null;
Statement stmt = null;
ResultSet rs = null;
ConnectionManager connMgr = new ConnectionManager();
stmt = connMgr.getStatement();
String sql = "delete from carpool.board where seq = '"+sseq+"'";
stmt.executeUpdate(sql);

connMgr.freeStatement();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script>
alert("삭제 되었습니다.");
opener.document.location.href="lobby.jsp";
self.close();
</script>
<body>
</body>
</html>
