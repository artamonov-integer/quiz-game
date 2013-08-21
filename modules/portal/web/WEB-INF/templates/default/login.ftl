<#import "/spring.ftl" as spring />

<#function msg code>
    <#assign messageBody>${message("com.integer.quiz.portal", code)}</#assign>
    <#return messageBody/>
</#function>

<html>
    <head>
        <title>
            Login page
        </title>
        <link rel="shortcut icon" type="image/x-icon" href="<@spring.url "/resources/favicon.ico"/>">
        <link rel="icon" type="image/gif" href="<@spring.url "/resources/favicon.ico"/>">
        <link rel="stylesheet" href="<@spring.url "/resources/css/common.css"/>"/>
        <link rel="stylesheet" href="<@spring.url "/resources/themes/default/default.css"/>"/>
    </head>
    <body>
        <h2>${msg("page.head")}</h2>
        <div class="span6">
            <h3>Users</h3>

            <form id="loginForm" method="POST">
                <div>
                    <div>
                        <label style="width: 80px" for="login">Login</label>
                    <@spring.formInput path="loginUserCommand.login" fieldType="text"/>
                    </div>
                    <div>
                        <label style="width: 80px" for="password">Password</label>
                    <@spring.formInput path="loginUserCommand.password" fieldType="password"/>
                    </div>
                </div>
                <button type="submit">Login</button>
            </form>

        </div>
    </body>
</html>
