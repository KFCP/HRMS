<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="${empty position.id ? '添加职位' : '编辑职位'}" scope="request"/>
<%@ include file="common/header.jspf" %>

<%-- Specific content for position_form.jsp --%>
<h2><c:choose><c:when test="${empty position.id}">添加新职位</c:when><c:otherwise>编辑职位: <c:out value="${position.positionName}"/></c:otherwise></c:choose></h2>

<c:url var="formAction" value="${empty position.id ? '/positions/add' : '/positions/edit/${position.id}'}"/>

<form:form method="post" action="${formAction}" modelAttribute="position">
    <form:hidden path="id"/>

    <div>
        <form:label path="positionName">职位名称:</form:label><br/>
        <form:input path="positionName" required="required"/>
        <form:errors path="positionName" cssClass="error-message"/>
    </div>
    <div>
        <form:label path="level">职位等级 (数字):</form:label><br/>
        <form:input type="number" path="level" required="required"/>
        <form:errors path="level" cssClass="error-message"/>
    </div>
    
    <br/>
    <input type="submit" value="<c:choose><c:when test="${empty position.id}">添加职位</c:when><c:otherwise>更新职位</c:otherwise></c:choose>">
    <a href="<c:url value='/positions'/>" style="margin-left: 10px;">取消</a>
</form:form>

<%-- Success/Error messages are now handled by header.jspf from sessionScope --%>
<%-- Form validation errors (<form:errors>) are still displayed locally --%>

<%@ include file="common/footer.jspf" %>
