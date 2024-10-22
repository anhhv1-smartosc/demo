# Sử dụng hình ảnh Java chính thức
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép tệp jar vào container
COPY target/demo.jar demo.jar

# Chạy tệp jar
ENTRYPOINT ["java", "-jar", "demo.jar"]
