package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

/**
 * 서비스 계층은 어플리케이션의 핵심 비즈니스 규칙을 구현한다.
 * 사용자의 요청을 처리하기 위해 리포지토리 등을 활용한다.
 */
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * Constructor
     *  - 같은 인스턴스를 사용하도록 설계하기 위해 외부에서 넣을 수 있도록 수정
     *  - (= DI, Dependency Injection, 의존성 주입, 같은 곳에 의존할 수 있도록 외부에서 주입)
     *  - 장점 : 유연성이 높아짐, 테스트 용이
     * @param memberRepository
     */
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Repository의 함수명은 데이터 삽입, 추출과 가까운 표현
     * Service의 함수명은 비즈니스에 가까운 표현
     */

    /**
     * 회원가입
     */
    public Long join(Member member) {
        // 조건 1. 중복 회원 유무 검증
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Optional<Member>
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * - Repository의 함수명은 데이터 삽입, 추출과 가까운 표현
     * - Service의 함수명은 비즈니스에 가까운 표현
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 단일 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
