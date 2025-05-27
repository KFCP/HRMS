<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("user_login.jsp");
        return;
    }

    String empIdStr = request.getParameter("employee_id");
%>

<html>
<head><title>员工薪酬管理</title><link rel="stylesheet" type="text/css" href="css/style.css"></head>
<body>
<h2>员工薪酬信息管理</h2>

<form method="get" action="salary_manage.jsp">
  员工ID: <input type="number" name="employee_id" value="<%= empIdStr != null ? empIdStr : "" %>" required>
  <input type="submit" value="查询薪酬">
</form>

<%
if (empIdStr != null && !empIdStr.trim().isEmpty()) {
    int empId = Integer.parseInt(empIdStr);

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms_db?useSSL=false&serverTimezone=UTC", "root", "password");

        // 查询薪酬记录
        String sql = "SELECT salary_amount, pay_date FROM salaries WHERE employee_id = ? ORDER BY pay_date DESC";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, empId);
        rs = ps.executeQuery();

        out.println("<table border='1' cellpadding='5'>");
        out.println("<tr><th>薪资金额</th><th>支付日期</th></tr>");

        boolean hasRecord = false;
        while (rs.next()) {
            hasRecord = true;
            out.println("<tr>");
            out.println("<td>" + rs.getDouble("salary_amount") + "</td>");
            out.println("<td>" + rs.getDate("pay_date") + "</td>");
            out.println("</tr>");
        }
        if (!hasRecord) {
            out.println("<tr><td colspan='2'>无薪酬记录</td></tr>");
        }
        out.println("</table>");
    } catch (Exception e) {
        out.println("<p>错误：" + e.getMessage() + "</p>");
    } finally {
        if (rs != null) try { rs.close(); } catch (Exception ignored) {}
        if (ps != null) try { ps.close(); } catch (Exception ignored) {}
        if (conn != null) try { conn.close(); } catch (Exception ignored) {}
    }
}
%>

<p><a href="employee_query.jsp">返回员工列表</a></p>
<p><a href="user_logout.jsp">退出登录</a></p>

</body>
</html>
