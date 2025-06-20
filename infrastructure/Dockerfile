FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=build/libs/*-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT [
  "java", "-jar",
  "-DappPort=${APP_PORT}",
  "-DdbUrl=${DB_URL}",
  "-DdbUsername=${DB_USERNAME}",
  "-DdbPassword=${DB_PASSWORD}",
  "-DrsaPrivateKey=${RSA_PRIVATE_KEY}",
  "-DrsaPublicKey=${RSA_PUBLIC_KEY}",
  "-DaesSecretKey=${AES_SECRET_KEY}",
  "-DcurrencyApiKey=${CURRENCY_API_KEY}",
  "-DkeyStorePassword=${KEY_STORE_PASSWORD}",
  "-DfrontendUrl=${FRONTEND_URL}",
  "-DmailersendToken=${MAILERSEND_TOKEN}",
  "-DemailSenderName=${EMAIL_SENDER_NAME}",
  "-DemailSenderEmail=${EMAIL_SENDER_EMAIL}",
  "app.jar"
]

EXPOSE 8443