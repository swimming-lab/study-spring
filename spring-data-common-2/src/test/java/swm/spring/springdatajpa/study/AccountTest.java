package swm.spring.springdatajpa.study;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import swm.spring.springdatajpa.entity.Account;
import swm.spring.springdatajpa.entity.Study;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class AccountTest {
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void JPA_직접사용() {
        Account account = new Account();
        account.setUsername("swimming");
        account.setPassword("password");

        entityManager.persist(account);
        entityManager.flush();

        Query select_a_from_account_a = entityManager.createQuery("select a from Account a");
        Object singleResult = select_a_from_account_a.getSingleResult();
        System.out.println((Account) singleResult);
    }

    @Test
    void hibernate_직접사용() {
        Account account = new Account();
        account.setUsername("swimming");
        account.setPassword("password");

        Session session = entityManager.unwrap(Session.class);
        session.save(account);
        session.close();

        session = entityManager.unwrap(Session.class);
        org.hibernate.query.Query select_a_from_account_a1 = session.createQuery("select a from Account a");
        select_a_from_account_a1.getSingleResult();
        Object singleResult = select_a_from_account_a1.getSingleResult();
        System.out.println((Account) singleResult);
    }

    @Test
    void JPA_일대다_관계() {
        Account account = new Account();
        account.setUsername("swimming");
        account.setPassword("password");

        Study study = new Study();
        study.setName("Spring Data JPA");

        account.addStudy(study);

        entityManager.persist(account);
        entityManager.persist(study);

        entityManager.flush();

        Account loadAccount = entityManager.find(Account.class, account.getId());
        System.out.println(loadAccount);

        entityManager.flush();
    }

    @Test
    void hibernate_일대다_관계() {
        Account account = new Account();
        account.setUsername("swimming");
        account.setPassword("password");

        Study study = new Study();
        study.setName("Spring Data JPA");

        account.addStudy(study);

        Session session = entityManager.unwrap(Session.class);
        session.save(account);
        session.save(study);

        Account loadAccount = session.load(Account.class, account.getId());
        loadAccount.setUsername("sssswwww");
        // loadAccount.setUsername("sssswwww2");    // 계속 수정하다가 똑같은 이름으로 변경하게되면 업데이트 쿼리를 날리지 않음
        // loadAccount.setUsername("swimming");     // 이런 부분에서 성능적인 이점이 있다.(업데이트 쿼리를 계속 보내지 않음)
        System.out.println("===============");
        System.out.println(loadAccount.getUsername());

        session.flush();

        /**
         * ===============
         * sssswwww
         * Hibernate:
         *     insert
         *     into
         *         account
         *         (city, state, home_street, zip_code, created, password, username, id)
         *     values
         *         (?, ?, ?, ?, ?, ?, ?, ?)
         * Hibernate:
         *     insert
         *     into
         *         study
         *         (name, owner_id, id)
         *     values
         *         (?, ?, ?)
         * Hibernate:
         *     update
         *         account
         *     set
         *         city=?,
         *         state=?,
         *         home_street=?,
         *         zip_code=?,
         *         created=?,
         *         password=?,
         *         username=?
         *     where
         *         id=?
         */
    }
}
