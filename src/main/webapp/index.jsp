<%@ include file="header.jsp" %>

<c:if test="${empty user}">
    <jsp:forward page="login.jsp"/>
</c:if>
<c:if test="${not empty user}">
    <jsp:forward page="/front-controller?command=catalog"/>
</c:if>

<%@ include file="footer.jsp" %>

