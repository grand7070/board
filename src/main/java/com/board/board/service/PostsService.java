package com.board.board.service;

import com.board.board.domain.dto.PostsListResponseDto;
import com.board.board.domain.dto.PostsResponseDto;
import com.board.board.domain.dto.PostsSaveRequestDto;
import com.board.board.domain.dto.PostsUpdateRequestDto;
import com.board.board.domain.posts.Posts;
import com.board.board.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional //??
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        /*
            스프링 Data JPA 는 Repository 에서 리턴 타입을 Optional 로 바로 받을 수 있게 지원해준다.
            Optional null 이 될 수도 있는 객체를 감싸는 래퍼 클래스로 사용하면 반복적인 null 체크를 줄일 수 있다.
            개발할 때 가장 많이 발생하는 예외 중 하나가 NPE(NullPointerException) 이다.
            NPE 피하기 위해 null 검사해야 하는데 코드가 복잡해지고 상당히 번거롭다.
            https://www.daleseo.com/java8-optional-after/
            http://tcpschool.com/java/java_stream_optional
            https://mangkyu.tistory.com/70
            orElseThrow()는 Repository 에서 Optional 을 반환하는 경우 원하는 값이 있으면 원하는 객체로 받고, 없으면 Exception 처리

            IllegalArgumentException 은 적합하지 않거나 적절하지 못한 인자를 메소드에 넘겨주었을 때 발생.

            람다 식은 함수를 하나의 식으로 표현한 것이다. 메소드의 이름이 필요 없어서 익명 함수의 한 종류라고 볼 수 있다.
            함수를 따로 만들지 않고 코드 한 줄에 함수를 써서 그것을 호출하는 방식.
            https://atoz-develop.tistory.com/entry/JAVA-%EB%9E%8C%EB%8B%A4%EC%8B%9DLambda-Expression
            (매개변수, ...) -> {실행문...}

         */
        posts.update(requestDto.getTitle(), requestDto.getContent());
        /*
            엔티티 자체를 repository 통해 DB 에서 가져오고 그걸 수정한 뒤 return 은 엔티티가 아닌 dto 로 해준다!
            수정하는 건 엔티티 이므로 엔티티 내부에 해당 메소드 마련.
         */
        return id; //??
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) //트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도 개선.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream() //??
                .map(PostsListResponseDto::new) //.map(posts ->PostsListResponseDto(posts)) //??
                .collect(Collectors.toList()); //??
    }

    @Transactional
    public void delete(Long id) { //얜 왜 반환 안함?
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }
}
