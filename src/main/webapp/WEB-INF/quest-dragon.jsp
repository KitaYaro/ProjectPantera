<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Как приручить дракона</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<jsp:include page="/WEB-INF/header.jsp"/>

<h2>Как приручить дракона</h2>

<c:set var="stage" value="${sessionScope.stage}" />
<c:set var="trust" value="${sessionScope.trust}" />
<c:set var="playerName" value="${sessionScope.playerName}" />

<c:if test="${trust != null && stage >= 3}">
    <p><strong>Доверие дракона:</strong> ${trust}%</p>
</c:if>

<c:choose>

    <c:when test="${stage >= 4 && stage <= 7 && trust < 50}">
        <h3>❌ Поражение ❌</h3>

        <c:choose>
            <c:when test="${stage == 4}">
                <p>Дракон резко отдёргивается и делает угрожающий шаг назад.<br>
                    Наставник качает головой: доверие нельзя требовать — его нужно заслужить.</p>
            </c:when>
            <c:when test="${stage == 5}">
                <p>Дракон фыркает и отворачивается.<br>
                    — Видишь, ${playerName}, — вздыхает наставник, — с таким подходом партнёров не заводят.</p>
            </c:when>
            <c:when test="${stage == 6}">
                <p>Наставник строго смотрит на тебя.<br>
                    — Полёт без понимания — это падение с задержкой.</p>
            </c:when>
            <c:when test="${stage == 7}">
                <p>Дракон нервно переступает с лапы на лапу.<br>
                    Он не чувствует в тебе надёжного всадника.</p>
            </c:when>
            <c:when test="${stage == 8}">
                <p>Дракон резко приземляется, не желая продолжать.<br>
                    — Страх чувствуется сразу, — говорит наставник.</p>
            </c:when>
        </c:choose>

        <form action="/home-page">
            <button type="submit">Начать заново</button>
        </form>
    </c:when>

    <c:when test="${stage >= 8 && stage <= 11 && trust < 70}">
        <h3>❌ Поражение ❌</h3>

        <c:choose>
            <c:when test="${stage == 8}">
                <p>Дракон начинает сильно нервничать и теряет уверенность.<br>
                    Полёт приходится прервать раньше времени.</p>
            </c:when>
            <c:when test="${stage == 9}">
                <p>Дракон резко уходит вниз, избегая столкновения.<br>
                    Он больше не чувствует поддержки.</p>
            </c:when>
            <c:when test="${stage == 10}">
                <p>Приземление выходит резким.<br>
                    Дракон отстраняется, явно недовольный тем, как всё закончилось.</p>
            </c:when>
        </c:choose>

        <form action="/home-page">
            <button type="submit">Начать заново</button>
        </form>
    </c:when>


    <c:otherwise>

        <c:if test="${stage == 0}">
            <p>
                Ты всегда знал, что однажды этот день настанет.<br>
                После долгого пути ты оказываешься в долине всадников — месте, где люди и драконы становятся напарниками на всю жизнь.<br>
                Сегодня тебе предстоит пройти испытание, от которого зависит, примет ли тебя дракон…
            </p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="0"/>
                <button type="submit">Продолжить</button>
            </form>
        </c:if>

        <c:if test="${stage == 1}">
            <p>
                Перед тобой медленно открываются массивные ворота долины.<br>
                Старый наставник говорит:<br>
                — Ну что ж, смелости тебе не занимать. Назови своё имя, будущий всадник.
            </p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="1"/>
                <input type="text" name="playerNameInput" required/>
                <button type="submit">Продолжить</button>
            </form>
        </c:if>

        <c:if test="${stage == 2}">
            <p>Наставник ведёт тебя в просторный вольер.<br>
                Перед тобой — четыре дракона, каждый оценивает тебя по-своему.</p>

            <img src="/images/fourDragonImg.jpg" width="300"/>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="2"/>
                <button name="choice" value="0">🔥 Огненный</button><br>
                <button name="choice" value="0">🌿 Лесной</button><br>
                <button name="choice" value="0">⚡ Грозовой</button><br>
                <button name="choice" value="0">❄ Снежный</button>
            </form>
        </c:if>

        <c:if test="${stage == 3}">
            <p>Ты подходишь ближе. Дракон внимательно следит за тобой.</p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="3"/>
                <button name="choice" value="15">Поднести руку</button><br>
                <button name="choice" value="10">Погладить</button><br>
                <button name="choice" value="-20">Схватить повод</button><br>
                <button name="choice" value="-15">Сказать, "не бойся, я друг, я не обижу"</button>
            </form>
        </c:if>

        <c:if test="${stage == 4}">
            <p>Наставник кивает и протягивает тебе мешочек с угощением.
                — Посмотрим, ${playerName}, умеешь ли ты договариваться не только словами,
                — говорит он. Дракон принюхивается и слегка наклоняет голову.
                Твои действия:</p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="4"/>
                <button name="choice" value="15">Аккуратно предложить угощение с ладони</button><br>
                <button name="choice" value="10">Показать лакомство и дать дракону самому подойти</button><br>
                <button name="choice" value="-10">Бросить угощение на землю и убежать</button><br>
                <button name="choice" value="-15">Спрятать угощение «на потом»</button>
            </form>
        </c:if>

        <c:if test="${stage == 5}">
            <p>Наставник начинает подробно рассказывать о правилах первого полёта.
                Где-то на середине объяснений ты ловишь себя на мысли,
                что информации стало слишком много, а дракон смотрит на тебя так,
                будто тоже не всё понял.
                Твои действия:</p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="5"/>
                <button name="choice" value="10">Внимательно слушать и кивать</button><br>
                <button name="choice" value="-5">Отвлечься: «О! Сова!»</button><br>
                <button name="choice" value="-10">Посмотреть на дракона с видом «надеюсь, ты всё запомнил»</button><br>
                <button name="choice" value="-10">Молча сделать вид, что ты всё понял и вопросов точно нет</button>
            </form>
        </c:if>

        <c:if test="${stage == 6}">
            <p>Перед взлётом тебе предлагают экипировку.
                Наставник хмурится и добавляет: — Это не обязательно,
                    ${playerName}… но падать без неё больнее.
                Твои действия:</p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="6"/>
                <button name="choice" value="15">Надеть защитные перчатки и крепко зафиксировать ремни</button><br>
                <button name="choice" value="5">Выбрать облегчённую экипировку, оставив только самое нужное</button><br>
                <button name="choice" value="-20">Отказаться от экипировки — «я и так справлюсь»</button><br>
                <button name="choice" value="-10">Нерешительно посмотреть на дракона, пытаясь угадать его реакцию</button>
            </form>
        </c:if>

        <c:if test="${stage == 7}">
            <p>Дракон напрягается, расправляет крылья и делает первый резкий рывок.
                Земля начинает уходить из-под ног, и становится ясно — пути назад нет.
                Твои действия:</p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="7"/>
                <button name="choice" value="15">Сосредоточиться и довериться дракону</button><br>
                <button name="choice" value="-10">Попытаться отдавать команды наугад</button><br>
                <button name="choice" value="-15">Запаниковать и вцепиться изо всех сил</button><br>
                <button name="choice" value="-5">Надеяться, что дракон сам разберётся</button>
            </form>
        </c:if>

        <c:if test="${stage == 8}">
            <p>Вы уже высоко над землёй. Ветер шумит в ушах, а долина остаётся далеко внизу — красиво,
                захватывающе и немного страшно.
                Твои действия:</p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="8"/>
                <button name="choice" value="10">Расслабиться и поймать ритм полёта</button><br>
                <button name="choice" value="0">Начать активно оглядываться по сторонам</button><br>
                <button name="choice" value="-15">Резко управлять поводьями</button><br>
                <button name="choice" value="5">Улыбнуться и позволить себе насладиться полётом</button>
            </form>
        </c:if>

        <c:if test="${stage == 9}">
            <p>Впереди неожиданно появляется стая крупных птиц.
                Дракон напряжённо взмахивает крыльями, готовясь маневрировать, и ждёт твоей реакции.
                Твои действия:</p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="9"/>
                <button name="choice" value="15">Сохранять спокойствие и дать дракону самому принять решение</button><br>
                <button name="choice" value="-15">Попытаться уклониться, дёргая поводья</button><br>
                <button name="choice" value="-10">Закричать</button><br>
                <button name="choice" value="-5">Зажмуриться и надеяться, что всё обойдется</button>
            </form>
        </c:if>

        <c:if test="${stage == 10}">
            <p>Дракон плавно идёт на снижение. Земля быстро приближается,
                и сейчас от твоих действий зависит, чем закончится этот полёт.
                Твои действия:</p>

            <form method="post" action="/quest-dragon">
                <input type="hidden" name="stage" value="10"/>
                <button name="choice" value="10">Следовать движениям дракона и сохранять равновесие</button><br>
                <button name="choice" value="-20">Попытаться спрыгнуть раньше времени</button><br>
                <button name="choice" value="-15">Напрячься и дёрнуть поводья насебя</button><br>
                <button name="choice" value="15">Довериться дракону до самого касания земли</button>
            </form>
        </c:if>

        <c:if test="${stage == 11}">
            <c:choose>
                <c:when test="${trust >= 70}">
                    <h3>🏁 Успех 🏁</h3>
                    <p>Дракон склоняет голову, принимая тебя как своего всадника.
                        Сегодня ты сделал первый шаг на пути, который изменит вашу жизнь.</p>
                </c:when>
                <c:otherwise>
                    <h3>❌ Провал ❌</h3>
                    <p>Дракон отступает, ясно давая понять — вы пока не готовы быть командой.
                        Возможно, в следующий раз ты сможешь подружиться с драконом, нужна практика.</p>
                </c:otherwise>
            </c:choose>
            <form action="/home-page">
                <button type="submit">Начать заново</button>
            </form>
        </c:if>
    </c:otherwise>
</c:choose>

</body>
</html>
