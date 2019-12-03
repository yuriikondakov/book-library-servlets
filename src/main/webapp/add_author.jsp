<%@ include file="header.jsp" %>
<div class="container">
    <div class="row mt-5">
        <div class="card w-75 m-auto mt-5">
            <h5 class="card-header"><fmt:message key="AddingAuthor"/></h5>
            <div class="card-body">
                <form action="front-controller" method="post">
                    <input type="hidden" name="command" value="add_author"/>
                    <c:if test="${not empty addAuthorSuccessful}">
                        <div class="alert alert-success" role="alert">${addAuthorSuccessful}</div>
                    </c:if>

                    <div class="form-group">
                        <label for="InputName"><fmt:message key="Name"/></label>
                        <input type="text" class="form-control" name="firstName" value="${firstName}"
                               id="InputName" placeholder="<fmt:message key="Name"/>" required autofocus>
                    </div>

                    <div class="form-group">
                        <label for="InputDescription"><fmt:message key="lastName"/></label>
                        <input type="text" class="form-control" name="lastName" value="${lastName}"
                               id="InputDescription" placeholder="<fmt:message key="Description"/>" required>
                    </div>

                    <button type="submit" class="btn btn-primary mt-3">
                        <fmt:message key="AddAuthor"/>
                    </button>

                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
