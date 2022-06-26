# spring-study

스프링 프레임워크(스프링부트) 학습을 기록한다.


# Spring VS SpringBoot

### Spring?

자바 생태계에서 가장 대중적인 응용프로그램 개발 프레임워크
의존성 주입(DI), 제어의 역전(IOC, Inversion Of Control)이 가장 중요한 특징

이들로 인해 결합도를 낮추는 방식으로 개발할 수 있음
이러한 개발 방식으로 개발한 응용프로그램은 단위테스트가 용이하기 떄문에 보다 높은 퀄리티로 개발할 수 있음

스프링은 약 20개의 모듈로 나누어져 있으며
흔히 발생하는 문제들을 해결하고 있다.

Spring JDBC
Spring MVC
Spring AOP
Spring ORM
Spring JMS
Spring TEST
...

### 그럼 왜 스프링부트가 필요할까?

Transaction Manager, Hibernate Datasource, Entity Manager, Session Factory와 같은 설정을 하는데에 어려움이 많고 최소한의 기능으로 Spring MVC를 사용하여 기본 프로젝트를 셋팅하는데 개발자에게 너무 많은 시간이 걸렸다.

### 이런 문제점들을 어떻게 해결했을까?

스프링부트는 자동설정(AutoConfiguration)을 이용하여 어플리케이션 개발에 필요한 모든 내부 dependency를 관리한다.
개발자가 해야하는건 실행뿐이다.
스프링의 jar파일이 classpath에 있는 경우 자동으로 Dispatcher Servlet으로 구성한다.
미리 설정된 starter 패키지(프로젝트)를 제공한다.

우리가 개발하는 동안 사용하는 Spring Core, Log4j 및 외부 라이브러리들을 서로 호환되는 버전들을 따로 선택해야했는데 이런 복잡도를 줄이기 위해 SpringBoot starter를 도입했다.
Starter는 의존성을 관리하는 세트이다.
필요한 라이브러리들을 pre-packaged된 형태로 제공한다.

내장된 톰캣이나 제티로 웹 어플리케이션을 바로 실행할 수 있다.

이런 문제점들을 개선하여 개발자들을 하여금 비즈니스에만 집중하게 해준다.

### 정리

- starter를 이용한 depedency 자동화
- Embed Tomcat(was)를 사용하기 때문에 따로 tomcat을 설치하거나 버전을 관리할 필요가 없다.
- xml 설정을 하지 않는다.
- 빌드 결과물인 jar를 가지고 손쉽게 배포가 가능하다.
- Spring Actuaor를 이용한 어플리케이션 모니터링과 관리를 제공한다.
