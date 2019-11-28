<%@ include file="header.jsp" %>
<div class="container">
    <div class="row">
        <table class="table table-sm table-striped mt-3">
            <thead>
            <tr class="text-center">
                <th ><fmt:message key="Id"/></th>
                <th><fmt:message key="Author"/></th>
                <th style="width: 50%"><fmt:message key="Name"/></th>
                <th><fmt:message key="IssueDate"/></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach var="book" items="${requestScope.userBooksList}">
                <tr>
                    <td class="text-center">${book.id}</td>
                    <td><c:forEach var="author" items="${book.authors}">
                        ${author.firstName} ${author.lastName} <br>
                    </c:forEach>
                    </td>
                    <td><a href="front-controller?command=book&id=${book.id}" class="card-link">${book.name}</a></td>
                    <td class="text-center">${book.issueDate}</td>
                    <td class="text-center" style="vertical-align: middle">
                        <a href="front-controller?command=returnBook&bookId=${book.id}" class="btn btn-info">
                            <fmt:message key="Return_book"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<%--<%@ include file="footer.jsp" %> --%>
