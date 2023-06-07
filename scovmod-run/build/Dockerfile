# FROM openjdk:11-jre
FROM adoptopenjdk:11-jre-hotspot-bionic

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update -y \
&&  apt-get -y dist-upgrade \
&&  apt-get install -y --no-install-recommends\
        vim \
        nano \
        htop \
        iproute2 \
&&  apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

WORKDIR /app
ADD app .

ENV PATH="/app/bin:${PATH}"
RUN chmod -R u+x /app/bin/*

CMD ["welcome.sh"]
