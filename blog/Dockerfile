# ---- 构建阶段 ----
FROM maven:3.9-eclipse-temurin-8 AS builder
WORKDIR /build
COPY pom.xml .
COPY blog-common/pom.xml blog-common/pom.xml
COPY blog-pojo/pom.xml   blog-pojo/pom.xml
COPY blog-server/pom.xml blog-server/pom.xml
# 先下载依赖（利用 Docker 层缓存）
RUN mvn dependency:go-offline -q
COPY blog-common/src blog-common/src
COPY blog-pojo/src   blog-pojo/src
COPY blog-server/src blog-server/src
RUN mvn clean package -DskipTests -q

# ---- 运行阶段 ----
FROM eclipse-temurin:8-jre-alpine
WORKDIR /app
COPY --from=builder /build/blog-server/target/blog-server-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
