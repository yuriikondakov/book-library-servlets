<%@ include file="header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-3"></div>
        <div class="col-6 mt-5">
                <form action="front-controller" method="post">
                    <input type="hidden" name="command" value="login"/>
                    <c:if test="${not empty registrationMessage}">
                        <div class="alert alert-success" role="alert">
                                ${registrationMessage}
                        </div>
                    </c:if>
                    <div class="form-group">
                        <label for="exampleInputEmail1"><fmt:message key="Email"/></label>
                        <input type="email" class="form-control" name="email" id="exampleInputEmail1"
                               aria-describedby="emailHelp" placeholder="<fmt:message key="Email"/>" value="${email}" required
                               autofocus>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1"><fmt:message key="Password"/></label>
                        <input type="password" class="form-control" name="password"
                               id="exampleInputPassword1" placeholder="<fmt:message key="Password"/>" required>
                    </div>
                    <c:if test="${not empty loginWarningMessage}">
                        <div class="alert alert-danger" role="alert">
                                <fmt:message key="loginWarningMessage"/>
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-primary"><fmt:message key="SignIn"/></button>
                    <a class="btn btn-success ml-3" href="/front-controller?command=register"><fmt:message key="Registration"/></a>
                </form>
        </div>
        <div class="col-3"></div>
    </div>
</div>
<%@ include file="footer.jsp" %>

