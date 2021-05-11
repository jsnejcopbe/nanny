<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<link rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/style.css">

<script language="JavaScript">
			function dosearch() {
			var sf=document.searchform;
			var submitto = sf.sengine.value + escape(sf.searchterms.value);
			window.location.href = submitto;
			return false;
			}
</script>

<body>
		<div class="header">
			<img src="${pageContext.request.contextPath}/images/Logo_sample.png" />
		</div>
		
		<p class="error">500</p>
		
		<div class="content">
			<h2>page coulnd't be found</h2>
			
			<p class="text">
				Oooooops… it looks like the page you were looking for does not exist anymore or is temporarily unavailable. You might want to search our website or browse our website. Praesent commodo cursus magna, vel scelerisque nisl consectetur et.
			
				<form name="searchform" onSubmit="return dosearch();">
					<input type="hidden" name="sengine" value="http://www.google.com/search?q=site:www.yoursite.com+" />
					<input type="text" name="searchterms" class="inputform">
					<input type="submit" name="SearchSubmit" value="Search" class="button"> 
				</form>
				<!-- Change www.yoursite.com to your website domain -->
			</p>
				
			<p class="links">
				<a id="button" href="http://www.cssmoban.com/">&larr; Back</a> <a href="#">Homepage</a> <a href="#">Portfolio</a> <a href="#">About Us</a> <a href="#">Blog</a>
				<!--These are links. You can change it to a page you want to by replacing the '#' with your url.-->
			</p>
		</div>

</body>
</html>