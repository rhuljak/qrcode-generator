FROM eclipse-temurin:18-jdk
MAINTAINER robert.huljak@gmail.com

EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/fonts/
RUN mkdir -p /app/logs/
RUN mkdir -p /app/generate/
RUN mkdir -p /app/generate/results
COPY src/main/resources/stitky-adobe-crack.pdf /app/stitky-adobe-crack.pdf
COPY src/main/resources/user-export-agenda.pdf /app/user-export-agenda.pdf
COPY src/main/resources/rozhodnuti-o-prijeti-1.pdf /app/rozhodnuti-o-prijeti-1.pdf
COPY src/main/resources/rozhodnuti-o-neprijeti-2.pdf /app/rozhodnuti-o-neprijeti-2.pdf
COPY src/main/resources/rozhodnuti-o-neprijeti-3.pdf /app/rozhodnuti-o-neprijeti-3.pdf
COPY src/main/resources/rozhodnuti-o-neprijeti-cizinci-3.pdf /app/rozhodnuti-o-neprijeti-cizinci-3.pdf
COPY src/main/resources/fonts/arial.ttf /app/fonts/arial.ttf
COPY src/main/resources/fonts/times.ttf /app/fonts/times.ttf
COPY src/main/resources/fonts/timesbd.ttf /app/fonts/timesbd.ttf
ADD target/qrcode-generator-1.2.0.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]