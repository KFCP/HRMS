<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="管理员登录" scope="request"/>
<%@ include file="common/header.jspf" %>

<%-- Specific content for user_login.jsp --%>
<h2>管理员登录</h2>

<form method="post" action="<c:url value='/login'/>">
    <label for="username">用户名:</label>
    <input type="text" id="username" name="username" required autofocus><br>

    <label for="password">密码:</label>
    <input type="password" id="password" name="password" required><br>

    <%-- CSRF token - Spring Security adds this automatically if enabled --%>
    <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
            
    <input type="submit" value="登录">
</form>

<%-- Login specific error messages --%>
<c:if test="${not empty param.error}">
    <p class="error-message">用户名或密码错误，请重试。</p>
</c:if>
<c:if test="${not empty param.logout}">
    <p class="success-message">您已成功退出。</p>
</c:if>
<%-- The global error message from sessionScope (handled in header.jspf) might also catch 
     RedirectAttributes if they are not cleared before header.jspf reads them.
     If a custom error message specifically for this page is set via RedirectAttributes
     and we want it *here* instead of in header, we might need a different key
     or ensure header.jspf doesn't display it for this page.
     For now, assuming param.error and param.logout are primary for this page.
     If 'errorMessage' is set by a controller specifically for a failed login attempt
     before redirecting back to this login page (not via Spring Security's default mechanism),
     it would be caught by the header. If that's not desired, use a different key.
--%>

<%@ include file="common/footer.jspf" %>
