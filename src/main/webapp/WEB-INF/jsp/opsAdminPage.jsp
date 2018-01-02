<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>OPS Admin page</title>
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>
        Welcome ${pageContext.request.userPrincipal.name} ! You are an authenticated ops admin
        <p>Your Session id is: "${pageContext.request.session.id}"</p>
    </h2>

    <div>
        <a href="${pageContext.request.contextPath}/security/manualLogout">
            <button>Logout</button>
        </a>

    </div>
</c:if>

</body>
</html>