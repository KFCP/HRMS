<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="${empty employee.id ? '添加员工' : '编辑员工'}" scope="request"/>
<%@ include file="common/header.jspf" %>

<%-- Specific content for employee_form.jsp --%>
<h2><c:choose><c:when test="${empty employee.id}">添加新员工</c:when><c:otherwise>编辑员工: <c:out value="${employee.name}"/></c:otherwise></c:choose></h2>

<c:url var="formAction" value="${empty employee.id ? '/employees/add' : '/employees/edit/${employee.id}'}"/>

<form:form method="post" action="${formAction}" modelAttribute="employee">
    <form:hidden path="id"/>

    <div>
        <form:label path="name">姓名:</form:label><br/>
        <form:input path="name" required="required"/>
        <form:errors path="name" cssClass="error-message"/>
    </div>
    <div>
        <form:label path="gender">性别:</form:label><br/>
        <form:radiobutton path="gender" value="男" label="男"/>
        <form:radiobutton path="gender" value="女" label="女"/>
        <form:radiobutton path="gender" value="其他" label="其他"/>
        <form:errors path="gender" cssClass="error-message"/>
    </div>
    <div>
        <form:label path="age">年龄:</form:label><br/>
        <form:input type="number" path="age" required="required"/>
        <form:errors path="age" cssClass="error-message"/>
    </div>
    <div>
        <form:label path="phone">电话:</form:label><br/>
        <form:input type="tel" path="phone"/>
        <form:errors path="phone" cssClass="error-message"/>
    </div>
    <div>
        <form:label path="email">邮箱:</form:label><br/>
        <form:input type="email" path="email"/>
        <form:errors path="email" cssClass="error-message"/>
    </div>
    <div>
        <form:label path="positionId">职位:</form:label><br/>
        <form:select path="positionId" required="required">
            <form:option value="" label="---选择职位---"/>
            <form:options items="${allPositions}" itemValue="id" itemLabel="positionName"/>
        </form:select>
        <form:errors path="positionId" cssClass="error-message"/>
    </div>
    <div>
        <form:label path="salary">薪水 (¥):</form:label><br/>
        <form:input type="number" step="0.01" path="salary"/>
        <form:errors path="salary" cssClass="error-message"/>
    </div>
    
    <br/>
    <input type="submit" value="<c:choose><c:when test="${empty employee.id}">添加员工</c:when><c:otherwise>更新员工</c:otherwise></c:choose>">
    <a href="<c:url value='/employees'/>" style="margin-left: 10px;">取消</a>
</form:form>

<%-- Success/Error messages from RedirectAttributes are handled by header.jspf from sessionScope --%>
<%-- Form validation errors (<form:errors>) are still displayed locally --%>
<%-- Display form validation errors from BindingResult (if Spring validation is fully set up) --%>
<form:errors path="*" cssClass="error-message" element="div" />

<%@ include file="common/footer.jspf" %>
