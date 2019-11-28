<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="Library"/></title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link href="http://fonts.googleapis.com/css?family=Roboto:400,300,500,700" rel="stylesheet" type="text/css">
    <script src="js/jquery-3.4.1.min.js" type="text/javascript"></script>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/"><fmt:message key="Library"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto ml-5">
            <c:if test="${not empty user}">
                <li class="nav-item">
                    <a class="navbar-brand" href="front-controller?command=catalog"><fmt:message key="Books"/></a>
                </li>
                <li class="nav-item">
                    <form action="front-controller" class="form-inline my-2 my-lg-0" method="post">
                        <input type="hidden" name="command" value="catalog"/>
                        <input class="form-control" type="text" name="searchField" placeholder="Search book"
                               value="${searchField}"/>
                        <button type="submit" class="btn btn-success ml-2">Go</button>
                    </form>
                </li>
            </c:if>
        </ul>

        <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="true">
                    ${sessionScope.lang}
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="front-controller?command=locale&locale=en">EN</a>
                    <a class="dropdown-item" href="front-controller?command=locale&locale=uk">UA</a>
                </div>
            </li>
        </ul>

        <form class="form-inline my-2 my-lg-0">
            <c:if test="${not empty user}">
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" role="button" data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="true">
                                ${user.name}(${user.role})
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="front-controller?command=logout"><fmt:message
                                    key="Logout"/></a>
                            <c:choose>
                                <c:when test="${user.role eq 'USER'}">
                                   <%-- <a class="dropdown-item" href="front-controller?command=user_account">
                                        <fmt:message key="user_account"/>
                                    </a>--%>
                                    <a class="dropdown-item" href="front-controller?command=my_books">
                                        <fmt:message key="my_books"/>
                                    </a>
                                </c:when>
                                <c:when test="${user.role eq 'ADMIN'}">
                                    <a class="dropdown-item" href="front-controller?command=add_book">
                                        <fmt:message key="AddBook"/>
                                    </a>
                                </c:when>
                            </c:choose>
                        </div>
                    </li>
                </ul>
            </c:if>
        </form>
    </div>
</nav>
