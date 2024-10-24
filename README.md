# Тестовое задание 
### Парамеры авторизации
```
Логин - Login
Пароль - Password
```
### Задание
- Ознакомиться с [требованиями работы приложения](Требования%20работы%20приложения.docx)
- Написать 5 автотестов для проверки авторизации
### Требования
- Язык программирования `JAVA`
- Инструмент автоматизации `Appium`
- При написании тестов надо показать использование CSS и Xpath локаторы, а также использование регулярных выражений
- Написанные тесты закинуть на личный GitHub с публичным доступом
- Воспользоваться известными паттернами при написании тестов
- Отчёт в `Allure` (не обязательно)

>[!ВАЖНО]
>
>Данный сайт используется для тренировок по нахождению и регистрации дефектов, поэтому могут встречаться ошибки. Пишете тесты, как в реальной жизни. >Если возникают ошибки, то надо автоматизировать до того момента, на котором возникает блок, чтобы после фикса можно было быстро доделать и получить работающий тест.
>
>На собеседовании необходимо будет продемонстрировать экран, рассказать структуру и запустить тесты.
> 
> 
# You should use for android testing :
> Appium 2.0 with uiautomator driver installed
> Android Gradle Plugin Version 7.4.2
> Gradle version 8.9

# To generate Allure report pls use  : 
> ./gradlew allureReport --clean
> ./gradlew allureServe

# Appium tests location 
> [NOTICE] You should build app module before tests execution
> uitesting/src/test/java/com/alfatest/test/LoginTest.java
> Device config file location : uitesting/src/test/resources/config.properties