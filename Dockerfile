### Starting from Ubuntu 14.10
FROM ubuntu:14.10


### Setting some ENV variables
ENV JAVA_HOME=/usr/lib/jvm/java-7-oracle
ENV PATH=/usr/bin:/bin:/usr/sbin:/sbin:$JAVA_HOME/bin


### Init apt
RUN apt-get install -y software-properties-common
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update

# Auto accept oracle java license
RUN echo debconf shared/accepted-oracle-license-v1-1 select true | \
         debconf-set-selections
RUN echo debconf shared/accepted-oracle-license-v1-1 seen true | \
         debconf-set-selections

RUN apt-get install -y \
    oracle-jdk7-installer

### Adding sources
ADD ./ /sources
WORKDIR /sources


### Gradle build
RUN ./gradlew build
