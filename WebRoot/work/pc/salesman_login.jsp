<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="login-holder col-md-6 col-md-offset-3">
       <h2 class="page-header text-center text-primary"> Welcome to Cascade </h2>
       <form role="form" action="index.html" method="post">
        <div class="form-group">
          <input class="form-control" id="exampleInputEmail1" placeholder="Enter email" type="email">
        </div>
        <div class="form-group">
          <input class="form-control" id="exampleInputPassword1" placeholder="Password" type="password">
        </div>
        <div class="form-footer">
          <label>
            <input class="hidden" id="input-checkbox" value="0" type="checkbox">  <i class="fa fa-check-square-o input-checkbox fa-square-o"></i> Remember me?
          </label>
          <label>
            <input class="hidden" id="input-checkbox" value="0" type="checkbox">  <i class="fa fa-check-square-o input-checkbox fa-square-o"></i> Forgot Password?
          </label>
          <button type="submit" class="btn btn-info pull-right btn-submit">Login</button>
        </div>

      </form>
    </div>
</body>
</html>