# Starting from Ubuntu 14.10
FROM ubuntu:14.10

# Init apt
RUN apt-get -y update

# Adding sources
ADD ./ /sources
WORKDIR /sources

# Running build
RUN ./gradlew build