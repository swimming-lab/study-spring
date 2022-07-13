package swm.spring.jpa.post;

/**
 * 이 인터페이스는 그대로 class형태로도 만들 수 있다.
 * 그러다 코드 간결성은 인터페이스가 좋다.
 */
public interface CommentSummary {

    String getComment();

    int getUp();

    int getDown();

    /**
     * Projection을 이용하여 SQL에서 Alias를 걸어서 특정 결과값을 만들어낼 때 사용함
     * 그러다 이 방법은 쿼리에 컬럼값을 모두 조회한 결과를 가지고 조합는거라 성능상 이득은 없음
     * 
     * select
     *         comment0_.id as id1_0_,
     *         comment0_.best as best2_0_,
     *         comment0_.comment as comment3_0_,
     *         comment0_.down as down4_0_,
     *         comment0_.post_id as post_id6_0_,
     *         comment0_.up as up5_0_ 
     * ...
     */
//    @Value("#{target.up + ' ' + target.down}")
//    String getVotes();

    /**
     * 이렇게하면 SQL조회할 때부터 컬럼을 필요한 컬럼만 조회하게 된다.
     *
     * select
     *         comment0_.comment as col_0_0_,
     *         comment0_.up as col_1_0_,
     *         comment0_.down as col_2_0_
     * ...
     */
    default String getVotes() {
        return getUp() + " " + getDown();
    }
}
