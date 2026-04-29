FROM openjdk:17-jdk-slim
WORKDIR /app #Carpeta dentro del container
COPY target/*.jar app.jar
EXPOSE 8080 #Para exponer el puerto de SpringBoot
ENTRYPOINT ["java","-jar","app.jar"] #.exe
