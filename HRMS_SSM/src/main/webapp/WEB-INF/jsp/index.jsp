<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<c:set var="pageTitle" value="主页" scope="request"/>
<%@ include file="common/header.jspf" %>

<%-- Specific content for index.jsp --%>
<h2>欢迎您访问人力资源管理系统 (SSM 版本)</h2>

<sec:authorize access="isAuthenticated()">
    <p>您已登录为: <sec:authentication property="principal.username"/>.</p>
    <p>您可以通过上方的导航栏访问各项功能。</p>
</sec:authorize>

<sec:authorize access="!isAuthenticated()">
    <p>请 <a href="<c:url value='/login'/>">登录</a> 以访问系统功能。</p>
</sec:authorize>

<%-- 
    The main navigation menu <ul class="main-menu"> is now in header.jspf.
    Global success/error messages are also handled in header.jspf from sessionScope.
    Any page-specific messages (not from RedirectAttributes) could be displayed here if needed.
--%>

<%@ include file="common/footer.jspf" %>
