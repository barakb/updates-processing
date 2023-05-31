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


### Publishe subscriber and Subscription

```java
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> s);
}

public interface Subscriber<T> {
    void onSubscribe(Subscription s);
    void onNext(T t);
    void onError(Throwable t);
    void onComplete();
}

public interface Subscription {
    void request(long n);
    void cancel();
}

```
Reactor expose 2 primitive types of Publisher
 * *Mono* - publish 0 or 1 element
 * *Flux* - publish 0 or more elements

The subscription object allow the consumer to adjust the control of the arriving elements.

