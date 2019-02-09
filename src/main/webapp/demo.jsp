<%--
  Created by IntelliJ IDEA.
  User: Yang
  Date: 2019/2/8
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>tese</title>
</head>
<body>
</body>
<script src="scripts/jquery/2.1.4/jquery.min.js"></script>
<script>
    $(function () {
        $.post('/user/login.do', {username: 'admin', password: '1'}, function (data) {
            console.log(data);
        })
    })
</script>
</html>
