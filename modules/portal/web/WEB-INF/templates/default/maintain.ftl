<#import "/spring.ftl" as spring />

<#function msg code>
    <#assign messageBody>${message("com.integer.quiz.portal", code)}</#assign>
    <#return messageBody/>
</#function>

<html>
    <head>
        <title>
            Under construction
        </title>
        <link rel="shortcut icon" type="image/x-icon" href="<@spring.url "/resources/favicon.ico"/>">
        <link rel="icon" type="image/gif" href="<@spring.url "/resources/favicon.ico"/>">
        <link rel="stylesheet" href="<@spring.url "/resources/css/common.css"/>"/>
        <link rel="stylesheet" href="<@spring.url "/resources/themes/default/default.css"/>"/>
    </head>
    <body>
        <h2>Sorry, resource is unavailable</h2>

        Please, try again after 20 min
    </body>
</html>
