<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<link rel="stylesheet" type="text/css" href="board_type1.css" />
<script>
function winpost()
{
	document.f.submit();
}
function windelete()
{
	window.open("postpagedelete.jsp","blank1",'width=500,height=500,menubar=no,status=no,toolbar=no');
	
}
</script>

<form name="f" type="post" action="postpage.jsp">	
<body>
	<div class="content" data-content="content-2">
		<h2>카풀 등록</h2>
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
									<div class="column_name">도착지</div>
										<select name="end_gu"> <!-- optgroup을 적용하지 않음 -->
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
					<button type="button" value="register" class="button button_type1"  onclick="winpost()">작성완료</button>
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

