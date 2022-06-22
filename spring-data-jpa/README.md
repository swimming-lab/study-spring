# Spring Data JPA

![jpa](https://github.com/swimming-lab/spring-study/blob/master/spring-data-jpa/jpa.PNG)

**ORM**

- JAVA에서 제공하는 ORM기술에 대한 표준 명세 API
- ORM은 DB테이블을 자바 객체로 매핑함으로써 객체간의 관계를 바탕으로 SQL을 자동으로 생성한다.
- DB데이터 ↔ mappling ↔ Object 필드
- 객체와 DB데이터를 자동으로 매핑하고, 메소드로 데이터를 조작한다

**Mapper**

- SQL을 작성하여 필드를 매핑시킨다.
- SQL ↔ mapping ↔ Object 필드
- Mybatis, jdbcTemplate

**JDBC**

![jdbc](https://github.com/swimming-lab/spring-study/blob/master/spring-data-jpa/jdbc.PNG)

JDBC는 DB에 접근할 수 있도록 자바에서 제공하는 API이다.

모든 Persistance Framework는 내부적으로 JDBC를 사용한다.

**Spring Data JPA**

![spring-data-jpa](https://github.com/swimming-lab/spring-study/blob/master/spring-data-jpa/spring-data-jpa.PNG)

JPA는 ORM을 위한 자바 EE표준이며 Spring Data JPA는 JPA를 쉽게 사용하기 위해 스프링에서 제공하고 있는 프레임워크이다.

*tip.*

Spring Data JPA, Spring Data MongoDB, Spring Data Redis 등 Spring Data 하위 프로젝트들은 동일한 인터페이스를 가지고 있기 때문에 저장소 교체가 용이한다.(findAll(), save() 등)

### JPA 동작 과정

**INSERT**

![jpa_insert](https://github.com/swimming-lab/spring-study/blob/master/spring-data-jpa/jpa_insert.PNG)

- Member 객체를 분석
- INSTERT SQL 생성
- JDBC API를 사용하여 SQL을 실행

**FIND**

![jpa_find](https://github.com/swimming-lab/spring-study/blob/master/spring-data-jpa/jpa_find.PNG)

- Entity 객체의 정보로 SELECT SQL 생성
- JDBC API를 사용하여 SQL을 실행
- 결과(ResultSet)를 객체에 매핑

### JPA를 사용하는 이유

- SQL 중심에서 객체 중심으로 개발이 가능하다.
    - SQL코드를 짜는 사람이 개발자인거처럼 모든 비즈니스가 SQL에 집중되는 것을 막아준다.
- 생산성 증가
    - 간단한 CRUD를 메소드로 제공한다.
- 유지보수 용이
    - 기존에는 테이블 ALTER 발생 시 모든 SQL 수정 필요
    - 엔티티 객체 필드만 변경하면 된다.
- 객체지향과 관계지향 데이터베이스의 패러다임 불일치 해결

**JPA 영속성**

JPA의 핵심 내용은 Entity가 영속성 컨텍스트에 포함되어 있는지, 아닌지로 갈린다. 
JPA의 Entity Manager가 활성화된 상태로 @Transactional 안에서 DB 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태이다. 이 상태에서 해당 데이터 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경 내용을 반영하게 된다. 따라서 별도로 update 쿼리를 날릴 필요가 없게된다. 이 것을 더티 체킹(Dirty Checking)이라고 한다.

**지연 로딩(Lazy Loading)**

- 객체가 실제로 사용될 때 로딩하는 전략
<pre>
Member member = memberRepository.findById(memberId);
// select * from member 테이블에 대한 쿼리만 실행

Team team = member.getTeam();
// Team 객체는 프록시 객체로 가져온다.

String teamName = team.getName();
// 사용될 때 select * from group 쿼리가 실행
</pre>
**즉시 로딩(EAGER)**
- 연결된 Entity들을 조인으로 한번에 쿼리를 실행시킨다.
- 프록시 객체가 아닌 실제 엔티티 객체를 가져온다.

**N+1**

쿼리를 1개만 날렸는데, 연관관계 때문에 추가 쿼리가 N개 나가는 것을 의미한다.
