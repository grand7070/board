package com.board.board.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
/*
    https://ict-nroo.tistory.com/129
    JPA Entity 클래스들이 상속할 경우 필드들도 column 으로 인식하도록 함. 상속관계 매핑이 아니다.
    단순히 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공. 엔티티가 아니므로 테이블과 매핑도 안됨.
    테이블과 관계가 없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할을 한다.
    주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용
    시간은 거의 사용하고 등록자 수정자는 때에 따라 다름. 그래서 public class BaseEntity extends BaseTimeEntity{} 으로 사용하면 편함.
    등록자는 @CreatedBy 수정자는 @LastModifiedBy
 */
@EntityListeners(AuditingEntityListener.class) //이 클래스에 Auditing 기능 포함. orm.xml 에 설정 추가하면 생략 가능.
public abstract class BaseTimeEntity {
    @CreatedBy //엔티티가 생성되어 저장될 때 시간이 자동 저장.
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 엔티티의 값을 변경할 때 시간이 자동 저장.
    private LocalDateTime lastModifiedDate;
}
/*
    JPA Auditing https://webcoding-start.tistory.com/53
    Java ORM 기술인 JPA 로 도메인을 관계형 데이터베이스 테이블에 매핑할 때 공통적으로 도메인들이 가지고 있는 필드나 컬럼들이 존재
    대표적으로 생성일자, 수정일자, 식별자 같은 필드 및 컬럼. 도메인마다 공통으로 존재한다는 건 결국 코드가 중복된다는 말
    JPA 에선 시간에 대해서 자동으로 값을 넣어주는 기능인 Audit 라는 기능 제공.
    날짜 타입으론 LocalData 과 LocalDataTime.
 */