<%@ include file="header.jsp" %>
<div class="container">
    <div class="row mt-5">
        <div class="card w-75 m-auto mt-5">
            <div class="card-body">
                <form action="front-controller" method="post">
                    <input type="hidden" name="command" value="takeBook"/>
                    <input type="hidden" name="bookId" value="${requestScope.book.id}"/>
                    <c:if test="${not empty takeBookSuccessful}">
                        <div class="alert alert-success" role="alert">
                                ${takeBookSuccessful}
                        </div>
                    </c:if>
                    <h5 class="card-title">${requestScope.book.name}</h5>
                    <h6 class="card-title">by <c:forEach var="author" items="${requestScope.book.authors}">
                        ${author.firstName} ${author.lastName} <br>
                    </c:forEach></h6>
                    <p class="card-text">${requestScope.book.description}</p>
                    <button type="submit"
                            <c:if test="${requestScope.bookIsAlreadyTaken}"> disabled class="btn btn-secondary" </c:if>
                            <c:if test="${!requestScope.bookIsAlreadyTaken}"> class="btn btn-primary" </c:if>
                    >
                        <fmt:message key="Take_for_1_month"/>
                    </button>
                    <c:choose>
                        <c:when test="${not empty takeBookSuccessful}">
                            <a href="javascript:history.go(-2)" class="btn btn-primary">
                                <fmt:message key="Back"/>
                            </a>
                        </c:when>
                        <c:when test="${empty takeBookSuccessful}">
                            <a href="javascript:history.go(-1)" class="btn btn-primary">
                                <fmt:message key="Back"/>
                            </a>
                        </c:when>
                    </c:choose>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
