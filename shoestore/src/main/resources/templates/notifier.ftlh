<#ftl encoding="UTF-8">
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Your order has been delivered to the pick-up point</title>
    <style>
        #body {
            display: flex;
            flex-direction: column;
            font-family: "Segoe UI", serif;
            align-items: center;
            background-color: #607FF2;
            font-size: 20px;
            height: 100%;
        }

        #div {
            display: block;
            flex-direction: column;
            margin: 10%;
            font-family: "Segoe UI", serif;
            background-color: white;
            padding: 3%;
            border-radius: 20px;
            color: black;
        }

    </style>
</head>
<body>
<div id="body">
    <div id="div">
        <h1 style="text-align: center">Notifier</h1>
        <p>Hello, ${name}!</p>
        <p>Your order has been delivered to the pick-up point.</p>
        <p>You can get your ${title} in ${city}, ${address}.</p>
        <p>With best regards, <br> the ShoeStore app development team!</p>
    </div>
</div>
</body>
</html>