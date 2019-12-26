<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Statement" %>
<%@page import="com.hj.ConnectionManager" %>
<%@page import="java.sql.*" %>
<%
String seq = request.getParameter("seq");
Statement stmt = null;
ResultSet rs = null;
ConnectionManager connMgr = new ConnectionManager();
stmt = connMgr.getStatement();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>modify</title>
</head>
<% 
	String sql = "select * from carpool.board where seq = '"+seq+"'";
	rs = stmt.executeQuery(sql);

	String sendseq = "";
	String tid = "";
	String title = "";
	String content = "";
	String start_time = "";
	String reg_gu = "";
	String end_gu = "";
	
	if(rs.next())
	{
		tid =rs.getString(2);
		title = rs.getString(3);
		content = rs.getString(4);
		start_time =rs.getString(5);
		reg_gu = rs.getString(6);
		end_gu = rs.getString(7);	
	}
%>
<script>
function initvalue()
{
	document.f.id.value = "<%=tid%>";
	document.f.title.value = "<%=title%>";
	document.f.start_time.value = "<%=start_time%>";
	document.f.start_gu.value = "<%=reg_gu%>";
	document.f.end_gu.value = "<%=end_gu%>";
	document.f.content.value = "<%=content%>";
		
}
function modify()
{
	document.f.submit();	
}
</script>

    <link rel="stylesheet" type="text/css" href="board_type1.css" />

<script>
	$(function(){
		$(window).resize(function(){
			var browser_size = window.outerWidth;
			if (browser_size <= 760) {
				var width = browser_size/100*90;
				$(".board_list_type1 tbody tr td:nth-child(3)").css("width", width).css("text-overflow","ellipsis").css("overflow","hidden").css("white-space","nowrap");
			}	
		}).resize();
	});
	
</script>
<form name="f" type="post" action="postpage.jsp">	
<input type = "hidden" name = seq size = "30" rows=30 value = "<%=seq%>">
<input type = "hidden" name = id size = "30" rows=30  value = "<%=tid%>">
<body onload = "initvalue()">
	<div class="content" data-content="content-2">
		<h2>카풀 수정</h2>
		<div class="board_type1_wrap">
			<div class="board_type1_write_wrap">
				<table class="board_write_type1">
					<tr>
						<td class="left" >
							<div class="column_name">제목</div>
							<div class="column_desc"><input type="text" name="title" class="text_type1"/></div>
						</td>
					</tr>
					<tr>
						<td class="left">
							<ul class="split_three">
								<li>
									<div class="column_name">출발지</div>
									<div class="column_desc">
									<select name="start_gu"> <!-- optgroup을 적용하지 않음 -->
										<option value="seoul_gangdong">서울 강동구</option>
										<option value="seoul_gangnam">서울 강남구</option>
										<option value="seoul_seocho">서울 서초구</option>
										<option value="seoul_songpa">서울 송파구</option>
										<option value="gyeonggi_pangyo">경기 판교</option>
										<option value="gyeonggi_pyeongtaek">경기 평택</option>
										<option value="gangwon_wonju">강원 원주</option>			
									</select>
							
							<select name="start_time"> <!-- optgroup을 적용하지 않음 -->
								<option value="0">0시</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
								<option value="13">13</option>
								<option value="14">14</option>
								<option value="15">15</option>
								<option value="16">16</option>
								<option value="17">17</option>
								<option value="18">18</option>
								<option value="19">19</option>
								<option value="20">20</option>
								<option value="21">21</option>
								<option value="22">22</option>
								<option value="23">23</option>
								<option value="24">24</option>		
							</select>
								</div>
									<div class="column_name">도착지</div><select name="end_gu"> <!-- optgroup을 적용하지 않음 -->
											<option value="seoul_gangdong">서울 강동구</option>
											<option value="seoul_gangnam">서울 강남구</option>
											<option value="seoul_seocho">서울 서초구</option>
											<option value="seoul_songpa">서울 송파구</option>
											<option value="gyeonggi_pangyo">경기 판교</option>
											<option value="gyeonggi_pyeongtaek">경기 평택</option>
											<option value="gangwon_wonju">강원 원주</option>		
										</select>
										</div>
									</div>
								</li>	
							</ul>
						</td>
					</tr>
					<tr>
						<td class="left" >
							<div class="column_name">내용</div>
							<div class="column_desc">
								<textarea rows="5"  name="content" cols="55" ></textarea>
							</div>
						</td>
					</tr>	
				</table>
			</div>
			<div class="button_margin"></div>
			<div class="board_type1_write_button_wrap">
				<div class="write_button_right">
					<button type="button" value="register" class="button button_type1"  onclick="modify()">작성완료</button>
				</div>
				<div class="write_button_left">
					<button type="button" value="delete" onclick="javascript:window.close();" class="button button_type2">취소</button>
				</div>
			</div>
		</div>
		<div class="margin"></div>
	</div>
	</body>
</form>

