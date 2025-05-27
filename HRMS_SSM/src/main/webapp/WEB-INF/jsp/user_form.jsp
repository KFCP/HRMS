<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="${empty user.id ? '添加用户 (Placeholder)' : '编辑用户 (Placeholder)'}" scope="request"/>
<%@ include file="common/header.jspf" %>

<%-- Specific content for user_form.jsp --%>
<h2><c:choose><c:when test="${empty user.id}">添加新用户</c:when><c:otherwise>编辑用户: <c:out value="${user.username}"/></c:otherwise></c:choose> (Placeholder)</h2>

<c:url var="formAction" value="${empty user.id ? '/users/add' : '/users/edit/${user.id}'}"/>

<form:form method="post" action="${formAction}" modelAttribute="user">
    <form:hidden path="id"/>

    <div>
        <form:label path="username">用户名:</form:label><br/>
        <form:input path="username" required="required"/>
        <form:errors path="username" cssClass="error-message"/>
    </div>
    <div>
        <form:label path="password">密码:</form:label><br/>
        <form:password path="password" required="${empty user.id}"/> <%-- Required only for new users --%>
        <form:errors path="password" cssClass="error-message"/>
        <c:if test="${not empty user.id}">
            <p><small><i>Leave password blank to keep current password when editing.</i></small></p>
        </c:if>
        <p><strong style="color:red;">SECURITY NOTE: Password handling is NOT YET SECURE. This form is a placeholder.</strong></p>
    </div>
            
    <br/>
    <input type="submit" value="<c:choose><c:when test="${empty user.id}">添加用户</c:when><c:otherwise>更新用户</c:otherwise></c:choose> (Placeholder)">
    <a href="<c:url value='/users'/>" style="margin-left: 10px;">取消</a>
</form:form>

<%-- Global Success/Error messages from RedirectAttributes are handled by header.jspf from sessionScope --%>
<c:if test="${not empty message}"> <%-- For general page-specific messages from controller --%>
    <p><c:out value="${message}"/></p>
</c:if>
<%-- Form validation errors (<form:errors>) are still displayed locally --%>
<form:errors path="*" cssClass="error-message" element="div" />

<%@ include file="common/footer.jspf" %>
