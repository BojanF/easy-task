<%--
  Created by IntelliJ IDEA.
  User: Marijo
  Date: 22-Jun-17
  Time: 00:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="form" action="/api/workers/" method="post">
    Email:<br>
    <input type="text" name="email" id="email">
    <br>
    Name:<br>
    <input type="text" name="name" id="name">
    <br>
    Surname:<br>
    <input type="text" name="surename" id="surename">
    <br>
    Password:<br>
    <input type="password" name="password" id="password">
    <br>
    Username:<br>
    <input type="text" name="username" id="username">
    <br><br>
    <input  id="submit" type="button" value="Submit"  >
</form>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script>
    $(document).ready(function(){
        // click on button submit
        $("#submit").on('click', function(){
            alert();
            // send ajax
            $.ajax({
                url: '/api/workers/', // url where to submit the request
                type : "POST", // type of action POST || GET
                dataType : 'json', // data type
                contentType:"application/json",
                data :  JSON.stringify({
                    email: $("#email").val(),
                    name: $("#name").val(),
                    surename: $("#surename").val(),
                    role:0,
                    password: $("#password").val(),
                    username: $("#username").val()
                }), // post data || get data
                success: function(result) {
                    // you can see the result from the console
                    // tab of the developer tools
                    console.log(result);
                },
                error: function(xhr, resp, text) {
                    console.log(xhr, resp, text);
                }
            })
        });
    });

</script>
</html>
