<%@ include file="header.jsp" %>
<div class="container">
    <div class="row">
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="Id"/></th>
                <th style="width: 30%"><fmt:message key="Author"/></th>
                <th><fmt:message key="Name"/></th>
            </tr>
            </thead>
            <c:forEach var="book" items="${requestScope.bookList}">
                <tr>
                    <td>${book.id}</td>
                    <td><c:forEach var="author" items="${book.authors}">
                        ${author.firstName} ${author.lastName} <br>
                    </c:forEach>
                    </td>
                    <td><a href="front-controller?command=book&id=${book.id}" class="card-link">${book.name}</a></td>
                </tr>
            </c:forEach>
        </table>
        <nav aria-label="Navigation for books">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item">
                        <a class="page-link"
                           href="front-controller?command=catalog&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&searchField=${searchField}">Previous</a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active">
                                <a class="page-link"> ${i} <span class="sr-only">(current)</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link"
                                   href="front-controller?command=catalog&recordsPerPage=${recordsPerPage}&currentPage=${i}&searchField=${searchField}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                    <li class="page-item">
                        <a class="page-link"
                           href="front-controller?command=catalog&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&searchField=${searchField}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
<%--<%@ include file="footer.jsp" %> --%>
