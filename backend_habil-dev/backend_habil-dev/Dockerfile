FROM harbor.bna.local/image_repo/eclipse-temurin:21-jre-alpine-3.23
RUN mkdir /opt/app
RUN mkdir /opt/app/ressources
RUN mkdir /opt/app/upload/
RUN mkdir /opt/app/upload/TableauEngagement
RUN mkdir /opt/app/upload/contrats
WORKDIR /opt/app/ressources
COPY habilitation.jar /opt/app
#RUN apt update && apt install tzdata -y
ENV TZ="Africa/Tunis"
CMD ["java", "-jar", "/opt/app/habilitation.jar", "--logging.file=/opt/app/ressources/LOGS.log"]
