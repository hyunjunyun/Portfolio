<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Statement" %>
<%@page import="com.hj.ConnectionManager" %>
<%@page import="java.sql.*" %>
<%
request.setCharacterEncoding("UTF-8");

String sid = request.getParameter("id");
String spass = request.getParameter("pw");
String sname = request.getParameter("name");
String sbirth = request.getParameter("birthday");
String smarry = request.getParameter("marry");
String sgender = request.getParameter("gender");
String sMsg = "";
Statement stmt_sel = null;
Statement stmt = null;
ResultSet rs = null;

ConnectionManager connMgrSel = new ConnectionManager();
stmt_sel = connMgrSel.getStatement();
String sql = "select * from carpool.member where id = '"+ sid + "'";
rs = stmt_sel.executeQuery(sql);
if(rs.next())
{
	%>
	<script>
		alert("이미 등록되어있는 아이디 입니다.");
		history.back();
	</script>
<%	
}else
{
	ConnectionManager connMgr = new ConnectionManager();
	stmt = connMgr.getStatement();
	stmt.executeUpdate("insert into carpool.member values ('"+sid+"','"+spass+"','"+sbirth+"','"+sname+"','"+smarry+"','"+sgender+"')");
	connMgr.freeStatement();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<script>
function wclose()
{
	opener.document.location.href="lobby.jsp";
	self.close();	
}
</script>
<body>
<% 
rs = stmt_sel.executeQuery("select * from member where id = '"+ sid + "'");
out.println("<table border=1>");

%>
    <link rel="stylesheet" type="text/css" href="board_type1.css" />
<div class="board_type1_wrap">
		<table class="board_list_type1">
			
			<thead>
				<tr>
					<th style="width:10%">아이디</th>			
					<th style="width:15%">비밀번호</th>
					<th class="active_type" style="width:10%">생일</th>
					<th class="active_type" style="width:10%">이름</th>
					<th class="active_type" style="width:15%">결혼여부</th>
					<th class="active_type" style="width:10%">성별</th>
				</tr>
			</thead>
			<tbody>
<%
if(rs.next())
{
	if("single".equals(rs.getString(5)))
	{
		smarry = "미혼";	
	}
	else
	{
		smarry = "기혼";	
	}
	if("woman".equals(rs.getString(6)))
	{
		sgender = "여자";	
	}
	else
	{
		sgender = "남자";	
	}
	
	out.println("<tr>");
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
	out.println(smarry);
	out.println("</td>");
	out.println("<td>");
	out.println(sgender);
	out.println("</td>");
	
	out.println("</tr>");
	out.println("<tr>");
	out.println("<td colspan=6>정상적으로 가입되었습니다.</td>");	
	out.println("</tr>");
	
	session.setAttribute("name", rs.getString(4));
	session.setAttribute("id", sid);

}
out.println("</table>");
connMgrSel.freeStatement();
%>
			</table>		
		</tbody>
	</div>
<input type = "button" value = "확인" onclick = "wclose()">
</body>
</html>
