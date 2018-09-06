<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
<style type="text/css">
*{
	padding: 0px;
	margin: 0px;

}
.box{
	width:100%;
	height: 100px;
	box-shadow: 5px 2px 2px #888888;

}
	.box1{
		width: 500px;
		height: 100px;
		margin: 0 auto;	
		text-align: center;
		line-height: 100px;
		letter-spacing:5px;
		font-size: 23px;
		color: #33CCCC;
				
	}
	.box2{
		width: 300px;
		height: 100px;
		
		position:relative;
		top:-100px;
		float: right;
				
	}
	.box3{
		width: 300px;
		height: 30px;
		
		margin-top:70px;
				
	}
	.box3-1{
		width: 150px;
		height: 28px;
		text-align:center;
		color:blue;
		float: left;
				
	}
	

</style>
	
	</head>
	<body >
		
			<div class="box1">
			员工管理系统
				</div>
				
		<div class="box2">
			<div class="box3">
			<div class="box3-1">${sessionScope.username }</div>
			<div class="box3-1">
			
			<i style="float: left;"><a href="user?type=loginout" target="_top">退出</a></i>
		
			
			
			</div>
			</div>
		
		</div>
		
	</body>
</html>