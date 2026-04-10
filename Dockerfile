FROM maven:3.9-eclipse-temurin-21-alpine AS build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:10.1-alpine

# Копируем WAR файл
COPY --from=build target/*.war /usr/local/tomcat/webapps/ROOT.war

# Убираем стандартный ROOT и деплоим наш
RUN rm -rf /usr/local/tomcat/webapps/ROOT && \
    mv /usr/local/tomcat/webapps/*.war /usr/local/tomcat/webapps/ROOT.war

# Переменные окружения для подключения к БД передаются через docker-compose
# в переменной JAVA_OPTS и доступны через System.getProperty()
