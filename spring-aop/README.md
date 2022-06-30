# Spring AOP

Aspect-Oriented Programming

관심사항들을 모아두는 프로그래밍 기법

객체지향적으로 프로그램을 구성할 수 있도록 도와줌(OOP를 달성하기 위한 패러다임)

### AOP 적용 전 로직

회원가입 로직에 시간을 로그로 남겨야 할 때 아래 코드처럼 비즈니스 서비스 로직에 포함되야 한다.

```java
public void join(Member member) {
	StopWatch stopWatch = new StopWatch();
	try {
		stopWatch.start();
		memberRepository.save(member);
	} finally {
		stopWatch.stop();		
	}
}
```

이러한 인프라 로직(부가기능: 로깅, 권한 체크, 트랜잭션, 성능 측정 등)은 모든 서비스의 관심사항으로 분리할 수 있다.

***인프라 로직 문제점***

- 애플리케이션의 전 영역에 나타날 수 있음
- 중복코드를 만들어낼 가능성 때문에 유지보수가 힘들어짐
- 비즈니스 로직과 함께 있으면 비즈니스 로직을 이해하기 어려워짐

### AOP 적용 로직

```java
@Aspect
@Component
public class AopAdvisor {
	...
	@Aroud("execution(* org.package.**.service.AuthService.*(..))")
	public Object stopWatch(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		try {
			stopWatch.start();
			return joinPoint.proceed();
		} finally {
			stopWatch.stop();
		}
	}
}
```

### AOP 용어

- Target
    - 어떤 대상에 부가 기능을 부여할 것인가
- Advice
    - 어떤 부가 기능? Before, AfterReturning, AfterThrowing, After, Around
- Join Point
    - 어디에 적용할 것인가? 메서드, 필드, 객체, 생성자 등
- Point Cut
    - 실제 advice가 적용될 지점, Spring AOPdptjsms advice가 적용될 메소드를 선정
    -

### Spring에서 AOP를 구현하는 방법

프록시 패턴

- 타켓 클래스를 프록시 클래스로 감싸져있음
- 타켓이라는 필드에 실제 객체가 담겨있음
- 런타임 시 AuthController > AuthService$$Proxy > AuthService 를 의존하게 됨

### 우리가 흔히 사용하는 AOP **@Transctional**

메서드 시작점에 트랜잭션을 시작하고 끝나는 부분에 커밋하는 부분이 반드시 들어가야 하는데

트랜잭션을 실행 종료하는 인프라 로직을 AOP로 구현함으로써 개발자들은 비즈니스 로직에만 집중할 수 있음