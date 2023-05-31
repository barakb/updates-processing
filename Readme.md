[![build](https://github.com/barakb/updates-processing/actions/workflows/build.yml/badge.svg)](https://github.com/barakb/updates-processing/actions/workflows/build.yml)
[![Renovate enabled](https://img.shields.io/badge/renovate-enabled-brightgreen.svg)](https://renovatebot.com/)


# Updates Processing

A simple application that process updates from a "Kafka" topic, use to demonstrate 
usage of reactor Flux and its unit testing, Spring boot and GitHub actions.

### Build

##### A native container image

```bash
mvn -Pnative spring-boot:build-image
```

##### A native image

```bash
mvn -Pnative native:compile
```