package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    /**
     * 각 메서드가 끝난 후, 실행할 동작 메서드
     *      - Test 메서드 간에는 실행 순서가 없음.
     *      - Repository에 저장되는 값은 공유되므로, 중복 가능성 존재
     *      - 중복 문제로 개별 실행 시 이상 없을지라도, 전체 실행 시, 테스트 에러 발생 가능
     *      - 테스트 코드에서는 repository에 있는 데이터를 메서드가 끝난 후, "초기화" 필요
     */
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        repository.save(member);
        Member result = repository.findById(member.getId()).get();

        // then
        // 1. org.junit.jupiter.api.Assertions
//        Assertions.assertEquals(member.getName(), result.getName());
        // 2. org.assertj.core.api.Assertions
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        Member result = repository.findByName(member1.getName()).get();

        // then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        List<Member> result = repository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }
}
