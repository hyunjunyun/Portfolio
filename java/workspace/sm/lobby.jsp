<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="com.hj.ConnectionManager" %>

<%
String tid = request.getParameter("tid");
String stitle = request.getParameter("stitle");
String scont = request.getParameter("scontent");
Statement stmt = null;
ResultSet rs = null;
ConnectionManager connMgrSel = new ConnectionManager();
stmt = connMgrSel.getStatement();


%>
<link rel="stylesheet" type="text/css" href="board_type1.css" />

<script>
var sessname = '<%=session.getAttribute("name")%>';
function winopen()
{
	
	window.open("carlogin.html","blank1",'width=500,height=600,menubar=no,status=no,toolbar=no');	
}

function login()
{
	window.open("login.html","blank2",'width=400,height=400,menubar=no,status=no,toolbar=no');
}

function logout()
{
	window.open("logout.jsp","blank2",'width=500,height=500,menubar=no,status=no,toolbar=no');
}

function memopen()
{
	window.open("member.jsp","blank2",'width=700,height=700,menubar=no,status=no,toolbar=no');	
}

function postwrite()
{
	if (sessname == "" || sessname == "null")
	{
		alert("로그인후 등록이 가능합니다.");
		return;
	}
	window.open("carpool.jsp","blank2",'width=500,height=600,menubar=no,status=no,toolbar=no');	
}

function modi(sendseq)
{
	window.open("modify.jsp?seq="+sendseq,"delete","width=0;,height=0;");
}
function reg_id(sendid)
{
	window.open("register.jsp?id="+sendid,"delete","width=0;,height=0;");
}
function del(sendseq)
{
	window.open("postpagedelete.jsp?seq="+sendseq,"delete","width=0;,height=0;");
}
function cont(sendseq)
{

	if (sessname == "" || sessname == "null")
	{
		alert("로그인후 등록이 가능합니다.");
		return;
	}
	window.open("content.jsp?seq="+sendseq,"content","width=500;,height=700;");
}



</script>
<form method = "post">
	
		<input type="button" value="join membership"  class="button_type1" onclick="winopen();">
		<%
			if(session.getAttribute("id") == null ||  "".equals(session.getAttribute("id")))
			{
				%>
				<input type="button" value="login"  class="button_type1" onclick="login();">
				<% 
			}
			else
			{
				%>
				<input type="button" value="logout"  class="button_type1" onclick="logout();">
				<% 
			}
		%>
		<input type="button" value="member" class="button_type1" onclick="memopen();">
		<input type="button" value="car pool"  class="button_type1" onclick="postwrite();">
	</form>
<div class="content" data-content="content-2">
	<h2>카풀 요청목록</h2>
	<div class="board_type1_wrap">
		<table class="board_list_type1">
			
			<thead>
				<tr>
					<th style="width:10%">번호</th>
			
					<th style="width:40%">제목</th>
					<th class="active_type" style="width:10%">작성자</th>
					<th class="active_type" style="width:12%">작성날짜</th>
				</tr>
			</thead>
			<tbody>
<% 

String sql = "select a.seq,a.id,a.title,date_format(a.reg_time,'%Y-%m-%d %H:%i:%s') reg_time,b.name from board a,member b where a.id = b.id order by seq desc";
rs = stmt.executeQuery(sql);
out.println("<table border=1>");
int inc = 0;
String sendseq = "";
while(rs.next())
{
	out.println("<tr>");
	
	
	sendseq = rs.getString(1);




%>								
			<td style="width:10%"><%=rs.getString(1) %></td>
			<td style="width:40%" class="left"><a href='javascript:cont("<%=rs.getString(1) %>")'><%=rs.getString(3) %></a></td>
			<td style="width:10%" class="active_type"><%=rs.getString(5) %></td>		
			<td style="width:12%" class="active_type"><%=rs.getString(4) %></td>
<%
	out.println("</tr>");
	inc++;
}
//pstmt.close();
connMgrSel.freeStatement();
%>						
			</tbody>
		</table>
	</div>

