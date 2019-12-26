<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Statement" %>
<%@page import="com.hj.ConnectionManager" %>
<%@page import="java.sql.*" %>
<%
request.setCharacterEncoding("UTF-8");
String sid 			= request.getParameter("id");
String stitle 		= request.getParameter("title");
String scont 		= request.getParameter("content");
String start_time	= request.getParameter("start_time");
String start_gu		= request.getParameter("start_gu");
String rege_gu 		= request.getParameter("end_gu");
String seq			= request.getParameter("seq");
Statement stmt = null;
ResultSet rs = null;
ConnectionManager connMgr = new ConnectionManager();
stmt = connMgr.getStatement();
try
{
	
	if(seq != null && !"".equals(seq))
	{
		String sSql="update board set title = '"+stitle+"', content = '"+scont+"', start_time = '"+start_time+"', reg_gu = '"+start_gu+"', end_gu= '"+rege_gu+"' where seq = "+seq+" ";
		stmt.executeUpdate(sSql);		
	}else{
		String sSql = "insert into carpool.board(id,title,content,start_time,reg_gu,end_gu,reg_time) values ('"+session.getAttribute("id")+"','"+stitle+"','"+scont+"','"+start_time+"','"+start_gu+"','"+rege_gu+"',sysdate())";
		stmt.executeUpdate(sSql);
	}
	if(stmt!=null)
	{
		stmt.close();
	}
%>
	<script>
		alert("저장되었습니다.");
		opener.document.location.href="lobby.jsp";
		self.close();
	</script>
<%	
}
catch(Exception e)
{
	if(stmt != null)
	{
		stmt.close();
	}
%>
	<script>
	alert("저장에실패하였습니다.");
	opener.document.location.href="lobby.jsp";
	self.close();
</script>
<%

}


%>
</body>
</html>
