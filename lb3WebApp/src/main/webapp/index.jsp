<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body bgcolor="#aaccff">
<Font color="green" size="10">
    Форма для работы со словарем
</Font>
<br>
<br>
<form name="frm" method="Get" action="MyServlet">
    <Font color="blue" size="6"> Введите русское слово:</Font>
    <Input type="Text" name="txt" value="<%=request.getAttribute("translatedRuWord") == null ? "" : request.getAttribute("translatedRuWord")%>"/>
    <br>
    <br>
    <Font color="blue" size="6">Перевод: </Font>
    <input type="text" name ="trans" value="<%=request.getAttribute("translatedEnWord") == null ? "" : request.getAttribute("translatedEnWord")%>"/><br>
    <h4>Кликни здесь для получения перевода :<Input type="submit" value="Перевести"/>
    </h4>
</form>

<img src="<%=request.getContextPath()%>/getImage">

</body>
