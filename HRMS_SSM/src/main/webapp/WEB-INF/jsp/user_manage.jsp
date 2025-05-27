<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="用户管理" scope="request"/>
<%@ include file="common/header.jspf" %>

<%-- Specific content for user_manage.jsp --%>
<h2>系统用户管理</h2>

<div style="margin-bottom: 15px;">
    <a href="<c:url value='/users/add'/>" class="button-like">添加新用户 (Placeholder)</a>
    <%-- Removed "返回主页" link as it's now in the global header navigation --%>
</div>

<%-- Global Success/Error messages are handled by header.jspf from sessionScope --%>
<c:if test="${not empty message}"> <%-- For general page-specific messages from controller --%>
    <p><c:out value="${message}"/></p>
</c:if>

<p><i>User listing and detailed management functionalities (edit, delete, roles) will be implemented here, integrated with Spring Security.</i></p>
        
<%-- Placeholder for where a user list might go --%>
<%-- 
<c:choose>
    <c:when test="${not empty users}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.username}"/></td>
                        <td>
                            <a href="<c:url value='/users/edit/${user.id}'/>">Edit</a>
                            &nbsp;|&nbsp;
                            <a href="<c:url value='/users/delete/${user.id}'/>" 
                               onclick="return confirm('Delete user ${user.username}?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>No users found or feature not yet implemented.</p>
    </c:otherwise>
</c:choose>
--%>

<%@ include file="common/footer.jspf" %>
