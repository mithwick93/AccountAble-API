FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=build/libs/*-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-DdbUrl=${DB_URL}", "-DdbUsername=${DB_USERNAME}", "-DdbPassword=${DB_PASSWORD}", "-DrsaPrivateKey=${RSA_PRIVATE_KEY}", "-DrsaPublicKey=${RSA_PUBLIC_KEY}", "-DcurrencyApiKey=${CURRENCY_API_KEY}", "app.jar"]

EXPOSE 8080