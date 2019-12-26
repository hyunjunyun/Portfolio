<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Statement" %>
<%@page import="com.hj.ConnectionManager" %>
<%@page import="java.sql.*" %>
<%
String sid = request.getParameter("id");
String spass = request.getParameter("pw");
String sname = request.getParameter("name");
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
<% 
String sql = "select * from member";
rs = stmt.executeQuery(sql);
out.println("<table border=1>");

%>
    <link rel="stylesheet" type="text/css" href="board_type1.css" />
<div class="board_type1_wrap">
		<table class="board_list_type1">
			
			<thead>
				<tr>
					<th style="width:10%">아이디</th>			
					<th style="width:10%">비밀번호</th>
					<th class="active_type" style="width:20%">생일</th>
					<th class="active_type" style="width:10%">이름</th>
					<th class="active_type" style="width:15%">결혼여부</th>
					<th class="active_type" style="width:10%">성별</th>
				</tr>
			</thead>
			<tbody>
<%
while(rs.next())
{
	out.println("<tr border=1>");
	out.println("<td>");
	out.println(rs.getString(1));
	out.println("</td>");
	out.println("<td>");
	out.println(rs.getString(2));
	out.println("</td>");
	out.println("<td>");
	out.println(rs.getString(3));
	out.println("</td>");
	out.println("<td>");
	out.println(rs.getString(4));
	out.println("</td>");
	out.println("<td>");
	out.println(rs.getString(5));
	out.println("</td>");
	out.println("<td>");
	out.println(rs.getString(6));
	out.println("</td>");
	
	out.println("</tr>");

}
out.println("</table>");
connMgr.freeStatement();
%>
			</table>		
		</tbody>
	</div>
<input type = "button" value = "확인" onclick = "javascript:window.close();">
</body>
</html>
