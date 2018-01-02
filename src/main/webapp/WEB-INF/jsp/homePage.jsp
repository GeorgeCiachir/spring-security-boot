<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Home</title>
</head>
<body>
    <h2>This is the application's home Page</h2>
    <div>
        <a href="${pageContext.request.contextPath}/security/manualLogin">
            <button>Go to Login page</button>
        </a>
    </div>
</body>
</html>