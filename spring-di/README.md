# Spring DI

DI를 학습하기전에 객체지향의 원칙인 IoC에 대해 이해를 이해하자.

### IoC(Inversion of Control Principle)

**제어의 역전 원칙**

***무엇을 말하는 것일까?***  
제어란?  
직접 객체를 생성하여 코드를 제어  
객체 생명주기나 메서드의 호출을 직접 제어/관리 한다.

```java
public class A {
    private B b;
	public A() {
		this.b = new B();
	}
}
```

역전이란? 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것

```java
public class A {
	private B b;
	public A(B b) {
		this.b = b;
	}
}
```

***IoC는 왜 필요할까?***

IoC 적용 전

```java
public class ItalianBMT {
	private MealBread mealBread;
	private ParmesanCheese parmesanCheese;
	...

	public ItalianBMT(Bread bread, Cheese cheese) {
		this.mealBread = new MealBread();
		this.parmesanCheese = new ParmesanCheese();
	}
}
```
위 코드처럼 객체를 직접 생성하여 사용할 경우  
MealBread가 다른 WhiteBread 로 변경해야 한다면 ItalianBMT 클래스 내부의 코드를 수정해야 한다.

IoC 적용 후

```java
public class ItalianBMT {
	private Bread bread;
	private Cheese cheese;
	...

	public ItalianBMT(Bread bread, Cheese cheese) {
		this.bread = bread;
		this.cheese = cheese;
	}
}
```
IoC 를 사용함으로써 Bread와 Cheess 인터페이스 구현한 객체를 외부에서 주입받아 변경에 자유로워진다.

 

### DIP(Dependency Inversion Principle)란?

상위 레벨의 모듈은 절대 하위 레벨의 모듈에 의존하지 않는다.  
둘 다 추상화에 의존해야 한다.

위 IoC 코드처럼 고수준 모듈이 저수준 모듈에 의존하지 않고 추상화에 의존하는 것이다.  
(고수준 모듈 ↔  추상화 ↔ 저수준 모듈 이렇게 서로 추상화에 의존)

클래스 간 결합을 느슨히 하기 위함이고, 한 클래스의 변경에 따른 다른 클래스들의 영향을 최소화하게 해준다.  
이 것은 어플리케이션을 지속가능하고 확장성 있게 만들어 준다.

IoC와 함께 사용되기를 권장하고  
IoC를 구현하기 위해 스프링에서는 DI를 지원한다.


### DI(Dependency Injection)란?

**의존성이란?**

클래스 간에 의존 관계가 있다는 것, 한 클래스가 바뀔 때 다른 클래스가 영향을 받는다는 것이다.

의존성 주입이란?  
의존성을 외부에서 주입해 주는 것을 말한다.

IoC는 원칙중 하나고 IoC를 달성하는 디자인패턴 중 하나이다.  


### Spring에서 의존성 주입 방법(@Autowired)

스프링 빈으로 등록되면 스프링이 자동으로 생성해준다. 이 때 필요한 의존성을 자동으로 주입해준다.

- 필드(Field)

```java
@Controller
public class AClass {
	@Autowired
	private BService bService;
}
```

제일 간단한 방법이지만 더 이상 추천하는 방법은 아니다.

수동으로 의존성을 주입할 수 없다.

- 메서드(Setter)

```java
@Controller
public class AClass {
	private BService bService;

	@Autowired
	public void setBService(BService bService) {
		this.bService = bService;
	}
}
```

빈 객체를 만들고 세터를 사용해서 자동으로 의존성을 주입한다.

이 때 빈 객체를 만들고 빈 생성자 혹은 빈 정적 팩토리 메서드를 통해 만들어 final 필드를 만들 수 없어 의존성의 불변을 보장할 수 없다는 특징이 있다.

런타임중에 의존성을 선택적으로 변경이 가능하다. 그러므로 선택적 의존성에만 사용해야 한다.

- 생성자(Constructor)

```java
@Controller
public class AClass {
	private BService bService;

	// @Autowired 생략 가능
	public AClass(BService bService) {
		this.bService = bService;
	}
}
```

스프링에서 공식적으로 추천하는 방법이다.  
생성자 기반 및 세터 기반 DI를 혼합할 수 있고,  
생성자 주입된 컴포넌트들이 완전히 초기화된 상태로 반환되기 때문이다.  

필드를 final로 만들 수 있어 불변을 보장하여 의존이 변하지 않는다.  
그러므로 NullPointerException이 발생하지 않고 순환 참조를 방지할 수 있다.

의존성 방법 우선순위

- 생성자 > 필드 > 세터

주입 대상이 여러 개일 때

```java
@Controller
public class PayController{
	private final PayService payService;

	public PayController(PayService payService) {
		this.payService = payService;
	}
}
...
@Service
public class NaverPayService implements PayService {
...
@Service
public class KaKaoPayService implements PayService {
```

하나만 주입이 가능하여 에러가 발생함.

아래 순서대로 우선순위를 가지고 주입할 수 있음

1. 타입
2. @Qualifier

```java
@Service
@Qualifier("mainPayService")
public class NaverPayService implements PayService {
...
public PayController(@Qualifier("mainPayService") PayService payService) {
    this.payService = payService; // NaverPayService가 주입됨
}
```

3. @Primary

```java
@Service
@Primary
public class NaverPayService implements PayService {
...
public PayController(PayService payService) {
    this.payService = payService; // NaverPayService를 기본값으로 설정하여 주입됨
}
```

4. 변수명
```java
...
public PayController(PayService naverPayService) {
    this.payService = naverPayService; // 이름을 확인하여 NaverParService를 주입함
}
```

### Configuration에서 직접 Bean 등록하여 의존성 주입 방법
@Configuration 어노테이션을 추가한 설정파일 클래스를 만들고서  
@Bean 메소드를 만들어서 원하는 클래스를 생성해준다.
```java
@Configuration
public class SpringConfig {    
    @Bean
    public MemberService() {
        return new MemberService(memberRepository());
    }
    
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
        // return new JpaMemberRepository();    // 원하느 구현체를 지정하여 Service로 넘겨줄 수 있음
        // return new RedisMemberRepository();
    }
}
```


## 정리
- IoC, DIP 는 원칙(Principle)
- DI는 이 것을 구현하는 디자인 패턴
- 객체지향 원칙을 지키기 위함
- 역할과 관심을 분리해 응집도를 높이고 결합도는 낮춤
- 변경에 유연한 코드를 작성할 수 있다.
