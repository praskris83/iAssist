<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getServletContext().getContextPath()%>/resources/accordin_style.css">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet"
	integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN"
	crossorigin="anonymous">
<title>Accordion Style</title>
</head>

<body>
	<div class="demo">
	<div align="right" style="background-color: #FFF;
  color: black;
  padding: 16px 20px;
  border: none;
  cursor: pointer;
  opacity: 0.8;
  position: fixed;
  right: 28px;
  width: 280px;
  height: 700px">
  Sended Messages for 
  <ul style="text-align: left" id="list">
  </ul>
  </div>
		<div class="container">
			<div class="row text-center">
				<h1 class="heading-title">Floors</h1>
			</div>
			<div class="row">
				<div class="col-md-offset-3 col-md-6">
					<div class="panel-group" id="accordion" role="tablist"
						aria-multiselectable="true">
						<div class="panel panel-default" onclick="floor1Click()">
							<div class="panel-heading" role="tab" id="headingOne">
								<h4 class="panel-title">
									<a class="" role="button" data-toggle="collapse"
										data-parent="#accordion" href="#collapseOne"
										aria-expanded="true" aria-controls="collapseOne"> Floor-1
									</a>
								</h4>
							</div>
							<div id="collapseOne" class="panel-collapse collapse in"
								role="tabpanel" aria-labelledby="headingOne">
								<div class="panel-body">
									<div class="panel-body">
									<img alt="" src="<%=request.getServletContext().getContextPath()%>/resources/img_1.jpg" width="350" height="150">
										<textarea name="" class="form-control type_msg"
											placeholder="Type your message..." id="input1"></textarea>
										<input type="submit"  onclick="sub1()"/>
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default" onclick="floor2Click()">
							<div class="panel-heading" role="tab" id="headingTwo">
								<h4 class="panel-title">
									<a class="collapsed" role="button" data-toggle="collapse"
										data-parent="#accordion" href="#collapseTwo"
										aria-expanded="false" aria-controls="collapseTwo"> Floor-2
									</a>
								</h4>
							</div>
							<div id="collapseTwo" class="panel-collapse collapse"
								role="tabpanel" aria-labelledby="headingTwo">
								<div class="panel-body">
									<div class="panel-body">
									<img alt="" src="<%=request.getServletContext().getContextPath()%>/resources/img_2.gif" width="350" height="150">
										<textarea name="" class="form-control type_msg"
											placeholder="Type your message..."id="input2"></textarea>
										<input type="submit" onclick="sub2()" />
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default" onclick="floor3Click()">
							<div class="panel-heading" role="tab" id="headingThree">
								<h4 class="panel-title">
									<a class="collapsed" role="button" data-toggle="collapse"
										data-parent="#accordion" href="#collapseThree"
										aria-expanded="false" aria-controls="collapseThree">
										Floot-3 </a>
								</h4>
							</div>
							<div id="collapseThree" class="panel-collapse collapse"
								role="tabpanel" aria-labelledby="headingThree">
								<div class="panel-body">
								<img alt="" src="<%=request.getServletContext().getContextPath()%>/resources/img_3.jpg" width="500" height="150">
									<textarea name="" class="form-control type_msg"
										placeholder="Type your message..." id="input3"></textarea>
									<input type="submit" onclick="sub3()" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function sub3() {
			var content=document.getElementById("input3");
			console.log(content.value);
			ajaxPost("topic3",content.value);
			floor3.push(content.value);
		}
		function sub2() {
			var content=document.getElementById("input2");
			console.log(content.value);
			ajaxPost("topic2",content.value);
			floor2.push(content.value);
		}
		function sub1() {
			var content=document.getElementById("input1");
			console.log(content.value);
			ajaxPost("topic1",content.value);
			floor1.push(content.value);
		}
		function ajaxPost(topic,message){
			var obj=new XMLHttpRequest();
			obj.open("GET","http://localhost:8080/receiveMsg/?topic="+topic+"&message="+message);
			obj.send();
			console.log("sended");
		}
		function floor1Click(){
			var ul=document.getElementById("list");
			ul.innerHTML="";
			for(var i=0;i<floor1.length;i++){
				var li=createLi(floor1[i]);
				ul.appendChild(li);
			}
			
		}
		function floor2Click(){
			var ul=document.getElementById("list");
			ul.innerHTML="";
			for(var i=0;i<floor2.length;i++){
				var li=createLi(floor2[i]);
				ul.appendChild(li);
			}
			
		}
		function floor3Click(){
			var ul=document.getElementById("list");
			ul.innerHTML="";
			for(var i=0;i<floor3.length;i++){
				var li=createLi(floor3[i]);
				ul.appendChild(li);
			}
			
		}
		function createLi(content){
			var li=document.createElement("li");
			li.textContent=content;
			return li;
		}
		var floor1=[];
		var floor2=[];
		var floor3=[];
	</script>
</body>
</html>
