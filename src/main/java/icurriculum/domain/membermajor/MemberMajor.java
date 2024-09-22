package icurriculum.domain.membermajor;

import icurriculum.domain.common.BaseTimeEntity;
import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static icurriculum.domain.membermajor.MajorType.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 회원 전공 상태
 */
@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class MemberMajor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_major_id")
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false)
    private MajorType majorType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public MemberMajor(MajorType majorType, Department department, Member member) {
        this.majorType = majorType;
        this.department = department;
        this.member = member;
    }

    /**
     * 비즈니스 method 주전공 확인
     */
    public boolean isMain() {
        return this.majorType == 주전공;
    }

}
