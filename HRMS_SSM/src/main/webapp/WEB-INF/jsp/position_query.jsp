<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="职位列表" scope="request"/>
<%@ include file="common/header.jspf" %>

<%-- Specific content for position_query.jsp --%>
<h2>职位信息管理</h2>

<div style="margin-bottom: 15px;">
    <a href="<c:url value='/positions/add'/>" class="button-like">添加新职位</a>
    <%-- Removed "返回主页" link as it's now in the global header navigation --%>
</div>

<form method="get" action="<c:url value='/positions'/>">
    <label for="keyword">职位名称搜索:</label>
    <input type="text" id="keyword" name="keyword" value="<c:out value='${keyword}'/>" placeholder="输入职位名称关键字">
    <input type="submit" value="查询">
</form>

<%-- Success/Error messages are now handled by header.jspf from sessionScope --%>

<c:choose>
    <c:when test="${not empty positions}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>职位名称</th>
                    <th>职位等级</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${positions}" var="pos">
                    <tr>
                        <td><c:out value="${pos.id}"/></td>
                        <td><c:out value="${pos.positionName}"/></td>
                        <td><c:out value="${pos.level}"/></td>
                        <td>
                            <a href="<c:url value='/positions/edit/${pos.id}'/>">编辑</a>
                            &nbsp;|&nbsp;
                            <a href="<c:url value='/positions/delete/${pos.id}'/>" 
                               onclick="return confirm('您确定要删除职位 \'${pos.positionName}\' 吗？此操作不可恢复。');">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>没有找到符合条件的职位信息。</p>
    </c:otherwise>
</c:choose>

<%@ include file="common/footer.jspf" %>
