# Spring Data JPA

![jpa](https://github.com/swimming-lab/spring-study/blob/master/spring-data-jpa/jpa.PNG)

### ORM(Object Relational Mapping)

- JAVA에서 제공하는 ORM기술에 대한 표준 명세 API
- ORM은 DB테이블을 자바 객체로 매핑함으로써 객체간의 관계를 바탕으로 SQL을 자동으로 생성한다.
- DB데이터 ↔ mappling ↔ Object 필드
- 객체와 DB데이터를 자동으로 매핑하고, 메소드로 데이터를 조작한다

### Mapper

- SQL을 작성하여 필드를 매핑시킨다.
- SQL ↔ mapping ↔ Object 필드
- Mybatis, jdbcTemplate

### JDBC

![jdbc](https://github.com/swimming-lab/spring-study/blob/master/spring-data-jpa/jdbc.PNG)

JDBC는 DB에 접근할 수 있도록 자바에서 제공하는 API이다.

모든 Persistance Framework는 내부적으로 JDBC를 사용한다.

### Spring Data JPA

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

### JPA 영속성

JPA의 핵심 내용은 Entity가 영속성 컨텍스트에 포함되어 있는지, 아닌지로 갈린다. 
JPA의 Entity Manager가 활성화된 상태로 @Transactional 안에서 DB 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태이다. 이 상태에서 해당 데이터 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경 내용을 반영하게 된다. 따라서 별도로 update 쿼리를 날릴 필요가 없게된다. 이 것을 더티 체킹(Dirty Checking)이라고 한다.

**지연 로딩(Lazy Loading)**

- 객체가 실제로 사용될 때 로딩하는 전략
<pre>
Member member = memberRepository.findById(memberId);
// select * from member 테이블에 대한 쿼리만 실행

Set<Articles> articles = member.getArticles();
// Articles 객체는 프록시 객체로 가져온다.

String title = articles.get(article_id).getTitle();
// 사용될 때 article에 대한 쿼리를 실행
</pre>
**즉시 로딩(EAGER)**
- 연결된 Entity들을 조인으로 한번에 쿼리를 실행시킨다.
- 프록시 객체가 아닌 실제 엔티티 객체를 가져온다.

### N+1

쿼리를 1개만 날렸는데, 연관관계 때문에 추가 쿼리가 N개 나가는 것을 의미한다.
findById와 같은 entityManager.find() 요청은 jpa가 내부적으로 join문에 대한 쿼리를 만들어서 반환하기 때문에 문제가 없다.

단, 실무에서는 jpql문을 짜기도 하고,
findBy~ 의 쿼리 메소드도 사용하기 때문에 jpql이 만들어져 N+1이 발생하게 된다.

**해결 방법**

- 지연 로딩
  - 지연 로딩하면 연관 관계의 객체를 프록시 객체로 생성하여 실제 사용 시점에 쿼리를 날린다.
  - 그러나 사용하는 순간에 N+1 문제를 피할 수 없다.(반복문으로 모든 연관 객체의 데이터를 읽을 경우)
  - 지연 로딩, 즉시 로딩은 쿼리를 실행시키는 시점에 차이만 있을 뿐 해결책이 아니다.
- Fetch join
  - 일반적은 조인문
    - @Query("select m from Member m left join m.articles")
      - 조인된 쿼리가 발생하지만 연관관계 객체들은 여전히 지연 로딩이어서 프록시 객체로 조회되어 사용될 때 N+1 문제가 유지 된다.
  - fetch join
    - @Query("select m from Member m left join fetch m.articles")
    - join문에 fetch를 걸어 지연 로딩이 걸려 있는 연관 관계에 대해 한번에 같이 즉시 로딩해주는 구문이다.
  - EntityGraph 방법
    - @EntityGraph(attributePaths = {"article"}, type = EntityGraphType.FETCH) @Query(일반쿼리...)
    - fetch join을 jpql문에 하드코딩을 하지 않고도 fetch join 할 수 있다.
  - 그러나 이 방법에도 문제가 있다.
    - Pagination 처리를 할 수 없다.
      - Pageable 객체를 파라미터에 추가한다고 해도 쿼리에 limit, offset 이 추가되지 않는다.
      - 모든 데이터를 조회하고 거기서 페이징처리를 한다.(appliying in memory)
      - 데이터양이 많아질 경우 Out Of Memory가 발생할 수 있다.
      - 이유는 distinct 때문에 jpa는 limit, offset을 걸지 않고 메모리로 가져온 뒤 페이징하기 때문이다.
    - Pagination 해결 방법?
      - ManyToOne 관계로 페이징 처리를 요청하면 된다.
      - Batch Size로 해결 방법
        - 이건 페이징이라기 보단 크기를 알 수 있는 데이터에 갯수를 지정한 만큼만 가져오는 방법이다.
        - Member 를 Batch Size만큼 조회하고 연관관계인 Articles을 조회할 때 `in` 절로 member_id를 넣는 방법이다.
        - @BatchSize의 기준은 Article이 아닌 Member이다.

### distinc를 사용하는 이유

하나의 연관관계에 대해서 fetch join으로 가져온다고 했을 때 중복된 데이터가 많기 때문에 실제로 원하는 데이터 양보다 중복되어 많이 들어오게 된다.  
연관관계에서 article이 list형태로 들어가기 때문에 Member가 여러개로 분리되어 생성되는 것을 막아준다.(중복 제거)

### mapperd by

mapperd by 옵션은 객체간 양방향 연관관계일 때 사용한다.  
연관관계의 주인은 해당 옵션을 사용하지 않고 반대쪽 객체에서 사용한다.(외래키를 가지고 있는 @ManyToOne이 주인이다.)  
즉, 양방향 관계일 때 반대쪽에 매핑되는 엔티티의 필드값을 넣는다.

```java
public class Article {
	...
	@ManyToOne
	private Member member
	...
}

public class  Member {
	...
	@OneToMany(mappedBy = "member")
	private Set<Article> articles;
	...
}
```

### 1차 Cache

1차 cache는 영속성 컨텍스트 내부에 있다. EntityManager로 조회하거나 변경하는 모든 Entity는 1차 cache에 저장된다.

*영속성 컨텍스트 자체가 사실상 1차 cache이다(on/off 불가)*

**동작방식**

- 최초 조회할 때 1차 cache에 Entity가 존재하지 않는다.
- 데이터베이스에서 Entity를 조회한다.
- Entity를 1차 캐시에 보관한다.
- 1차 cache에 보관된 Entity를 반환한다.
- 이후 요청은 모두 1차 cache에 Entity가 존재하므로 그대로 반환한다.

**특징**

- 객체의 동일성을 보장한다.(A == B)
- 영속성 컨텍스트 범위에 포함되므로 트랜잭션과 생명주기를 같이한다.

**Write Behind**

EntityManager는 트랜잭션을 커밋하기 직전까지 데이터베이스에 엔티티를 저장하지 않고 내부 쿼리 저장소에 SQL문을 기록했다가 커밋할 때 모아둔 쿼리를 전송한다.

### 2차 Cache

어플리케이션에서 공유하는 cache를 jpa에서는 공유 캐시(Shared Cache)라고 하며 2차 cache로 부른다.

어플리케이션 범위의 캐시로서 어플리케이션 생명주기를 따른다. 분산 환경이나 클러스터링 환경에서는 더 오래 유지될 수도 있다.

EntityManager를 통해 데이터를 조회할 때 우선 2차 cache에서 찾고 없으면 데이터베이스를 조회한다.

데이터베이스 조회 횟수를 획기적으로 줄일 수 있다.

**동작방식**

- 영속성 컨텍스트는 Entity가 필요하면 2차 cache를 조회한다.
- 2차 cache에 Entity가 없으면 데이터베이스를 조회한다.
- 결과를 2차 cache에 저장한다.
- 2차 cache는 자신이 보관하고 있는 Entity를 복제해서 반환한다.

**특징**

- 어플리케이션에서 공유하므로 모든 트랜잭션에서 공유가 가능하다.
- 객체를 그대로 반환하는 것이 아니라 복사본을 만들어서 반환한다.
- 데이터베이스 기본 키를 기준으로 캐시하지만 영속성 컨텍스트가 다르면 객체 동일성을 보장하지 않는다.(A ≠ B)