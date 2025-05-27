<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="员工列表" scope="request"/>
<%@ include file="common/header.jspf" %>

<%-- Specific content for employee_query.jsp --%>
<h2>员工信息管理</h2>

<div style="margin-bottom: 15px;">
    <a href="<c:url value='/employees/add'/>" class="button-like">添加新员工</a>
    <%-- Removed "返回主页" link as it's now in the global header navigation --%>
</div>

<form method="get" action="<c:url value='/employees'/>">
    <label for="keyword">员工姓名搜索:</label>
    <input type="text" id="keyword" name="keyword" value="<c:out value='${keyword}'/>" placeholder="输入员工姓名关键字">
    <input type="submit" value="查询">
</form>

<%-- Success/Error messages are now handled by header.jspf from sessionScope --%>

<c:choose>
    <c:when test="${not empty employees}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>年龄</th>
                    <th>电话</th>
                    <th>邮箱</th>
                    <th>职位</th>
                    <th>薪水</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${employees}" var="emp">
                    <tr>
                        <td><c:out value="${emp.id}"/></td>
                        <td><c:out value="${emp.name}"/></td>
                        <td><c:out value="${emp.gender}"/></td>
                        <td><c:out value="${emp.age}"/></td>
                        <td><c:out value="${emp.phone}"/></td>
                        <td><c:out value="${emp.email}"/></td>
                        <td><c:out value="${emp.position.positionName}"/></td>
                        <td><fmt:formatNumber value="${emp.salary}" type="currency" currencySymbol="¥"/></td>
                        <td>
                            <a href="<c:url value='/employees/edit/${emp.id}'/>">编辑</a>
                            &nbsp;|&nbsp;
                            <a href="<c:url value='/employees/delete/${emp.id}'/>"
                               onclick="return confirm('您确定要删除员工 \'${emp.name}\' 吗？此操作不可恢复。');">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>没有找到符合条件的员工信息。</p>
    </c:otherwise>
</c:choose>

<%@ include file="common/footer.jspf" %>
