# ─────────────────────────────────────────────
# STAGE 1: BUILD
# Maven image use karo app ko compile karne ke liye
# ─────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Container ke andar working directory set karo
WORKDIR /app

# Pehle sirf pom.xml copy karo (dependencies cache hogi)
COPY pom.xml .

# Dependencies download karo (ye layer cache hoti hai — fast builds)
RUN mvn dependency:go-offline -B

# Ab poora source code copy karo
COPY src ./src

# App build karo (tests skip karo — Docker build mein DB nahi hota)
RUN mvn clean package -DskipTests

# ─────────────────────────────────────────────
# STAGE 2: RUN
# Sirf JRE chahiye run karne ke liye (Maven nahi)
# Ye image builder se BAHUT chhoti hogi
# ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Sirf built JAR copy karo Stage 1 se
COPY --from=builder /app/target/*.jar app.jar

# Port expose karo (documentation ke liye)
EXPOSE 8081

# App start karo
ENTRYPOINT ["java", "-jar", "app.jar"]