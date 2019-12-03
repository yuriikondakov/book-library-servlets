<%@ include file="header.jsp" %>
<div class="container">
    <div class="row mt-5">
        <div class="card w-75 m-auto mt-5">
            <h5 class="card-header"><fmt:message key="AddingBook"/></h5>
            <div class="card-body">
                <form action="front-controller" method="post">
                    <input type="hidden" name="command" value="add_book"/>
                    <%--<input type="hidden" name="bookId" value="${requestScope.book.id}"/>--%>
                    <c:if test="${not empty addBookSuccessful}">
                        <div class="alert alert-success" role="alert">
                                ${addBookSuccessful}
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label for="FormControlSelect1" ><fmt:message key="Author"/></label>
                        <select class="form-control" id="FormControlSelect1" name="author">
                            <c:forEach var="item" items="${authorList}">
                                <option value="${item.id}">${item.firstName} ${item.lastName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="InputName"><fmt:message key="Name"/></label>
                        <input type="text" class="form-control" name="name" value="${name}"
                               id="InputName" placeholder="<fmt:message key="Name"/>" required autofocus>
                    </div>

                    <div class="form-group">
                        <label for="InputDescription"><fmt:message key="Description"/></label>
                        <input type="text" class="form-control" name="description" value="${description}"
                               id="InputDescription" placeholder="<fmt:message key="Description"/>" required>
                    </div>

                    <button type="submit" class="btn btn-primary mt-3">
                        <fmt:message key="AddBook"/>
                    </button>

                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
