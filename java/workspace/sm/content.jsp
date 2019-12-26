<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Statement" %>
<%@page import="com.hj.ConnectionManager" %>
<%@page import="java.sql.*" %>
<%
String seq = request.getParameter("seq");
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
    <link rel="stylesheet" type="text/css" href="board_type1.css" />
<script>
var sessname = '<%=session.getAttribute("name")%>';

function modi(sendseq)
{
	document.f.action="modify.jsp?seq="+sendseq;
	document.f.submit();
}
function reg_id(sendseq)
{
	window.open("register.jsp?seq="+sendseq,"reg","width=700;,height=700;");	
}
function del(sendseq)
{
	document.f.action="postpagedelete.jsp?seq="+sendseq;
	document.f.submit();
}
function del_complete()
{
	opener.document.location.href="lobby.jsp";
}
function selDriver()
{
	var selId = document.f.sel_id.value;
	var selSeq = '<%=seq%>'
	window.open("select.jsp?seq="+selSeq+"&selId="+selId,"delete","width=0;,height=0;");	
}
</script>
<body>
<h2>카풀 상세내용</h2>
<%
String sql = "select * from carpool.board where seq = '"+seq+"'";
rs = stmt.executeQuery(sql);
String sendseq = "";
String regId = "";
String sessionId = session.getAttribute("id")+"";
if(rs.next())
{
	out.println("<tr>");
	sendseq = rs.getString(1);
	regId = rs.getString(2);
%>								
				<table class="board_write_type1">
					<tr>
						<td class="left">
							<ul class="split_three">
								<li><fieldset>
                						<legend>제목</legend>
										<div class="column_name"><%=rs.getString(3) %></div>
									</fieldset>
									<fieldset>
                						<legend>출발지</legend>
										<div class="column_name"><%=rs.getString(6) %> <%=rs.getString(5) %> 시</div>
									</fieldset>
									<fieldset>
                						<legend>도착지</legend>
										<div class="column_name"><%=rs.getString(7) %></div>
									</fieldset>
									<fieldset>
                						<legend>내용</legend>
										<div class="column_name"><%=rs.getString(4) %></div>
									</fieldset>
									<fieldset>
                						<legend> </legend>
	<%
	if(rs.getString(2).equals(session.getAttribute("id")))
	{
		%>
		<input type="button" value="수정하기" onclick = "modi('<%=seq%>')"> <input type="button" value="삭제하기" onclick = "del('<%=seq%>')"> <input type="button" value="닫기" onclick = "window.close();">
		<%
	}
	else
	{
		%>
		<input type="button" value="지원하기" onclick = "reg_id('<%=seq%>')"> <input type="button" value="닫기" onclick = "window.close();">
		<%
	}
}
%>									

									</fieldset>
								</li>	
							</ul>
						</td>
					</tr>

				</table>
			</div>

<%

drs = stmt.executeQuery("select * from carpool.driver where seq = '"+seq+"'");
%>
<h2>지원자</h2>
<form name = "f" method="post" action = "select.jsp">

<%
out.println("<table border=1>");
int sum=0;
String sendyseq="";
out.println("<tr>");
out.println("<td>선택</td>");
out.println("<td>지원자 ID</td>");
out.println("<td>선택여부</td>");
out.println("</tr>");
while(drs.next())
{
	out.println("<tr>");
	out.println("<td><input type = 'radio' name='sel_id' value = '"+drs.getString(2)+"'></td>");
	out.println("<td>");
	out.println(drs.getString(2));
	out.println("</td>");
	out.println("<td>");
	out.println(drs.getString(3));
	out.println("</td>");
	
	sendyseq = drs.getString(1);
	
	out.println("</tr>");
}
out.println("</table>");
%>
</form>
<%
if (sessionId != null && sessionId.equals(regId))
{
%>
<P>
	<input type = "button" value = "지원자 선택" onclick="selDriver()">
<%
}
%>
</script>

</body>
</html>