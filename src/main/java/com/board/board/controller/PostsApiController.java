package com.board.board.controller;

import com.board.board.domain.dto.PostsResponseDto;
import com.board.board.domain.dto.PostsSaveRequestDto;
import com.board.board.domain.dto.PostsUpdateRequestDto;
import com.board.board.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor //final 이나 @NonNull 인 필드 값만 파라미터로 받는 생성자를 만들어줌
@Slf4j //log.info()
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}") //@PathVariable 은 url 에서 각 구분자에 들어오는 값 처리
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}

/*
클라이언트->서버 요청 데이터 전달할 땐 3가지 방법 사용
1. get - 쿼리 파라미터 : /url?username=hello&age=20
2. post - HTML Form : 메세지 바디에 쿼리 파라미터 형식으로 전달
3. HTTP message body 에 데이터 담아 요청. HTTP API에서 주로 사용. JSON(주로), XML, TEXT

컨트롤러에서는 넘어오는 url 에서 파라미터 빼낼 수 있음
@RequestParam("username") String username : "username"이라는 파라미터 가져옴
@RequestParam String username : Http 파라미터 이름이 변수 이름과 같으면 생략가능
String username : String, int, Integer 등의 단순 타입이면 @RequestParam 도 생략 가능, 근데 너무 생략됨

필수 파라미터 필수 여부 설정 가능 @RequestParam(required = true) (기본 값) : 없으면 오류.
false 면 null 근데 int 면 0이므로 Integer 권장
파라미터 이름만 있고 값이 없으면 빈문자로 통과 username=
defaultValue = ~~ 는 파리미터 안넘어오거나 빈문자 처리해줌

요청 파라미터 받아서 필요한 객체 만들고 그 객체에 값 넣어줘야 함. -> @ModelAttribute
@ModelAttribute Member member 이렇게 하면 넘어오는 파라미터들이 자동으로 객체에 들어감. 생략되나 너무 생략됨.

요청 파라미터와 다르게 HTTP 메시지 바디 통해 데이터 직접 넘어오는 경우 @RequestParam 이나 @ModelAttribute 사용 못함
그럴 때 @RequestBody. HTTP 메시지 바디 읽어서 여기에 넣어줌. @ModelAttribute 마냥 @RequestBody Member member 가능. 생략 불가.
응답은 @ResponseBody. 응답 결과를 HTTP 메시지 바디에 담아 전달. 이건 @RestController 에 합쳐짐.

요청 파라미터 vs HTTP 메시지 바디
요청 파라미터를 조회하는 기능 : @RequestParam, @ModelAttribute
HTTP 메시지 바디를 직접 조회하는 기능 : @RequestBody
======================================================
@Valid 는 파라미터를 검증한다. service 단이 아닌 객체 안에서 들어오는 값 검증 가능. 컨트롤러에서 Dto 유효성 검증.
Dto 안에 있는 값들에 특정 어노테이션마다 조건 붙음. ex) @NotNull, @NotEmpty, @Max(~), @Min(~), @Past, @Email, ....
List<UserDto> list 와 같은 형태에서는 검증할 수 없음. (다른 방법을 통해 가능)

BindingResult 는 에러 내역을 볼 수 있다. (@RequestBody @Valid UserDto userDto, BindingResult bindingResult)
그런데 굉장히 많은 에러 메시지를 내려준다. 시스템 내부 정보가 포함되있고 클라이언트단에 내려주는 형식도 너무 복잡해서 사용하기 힘들다.

그래서 @ExceptionHandler, @ControllerAdvice, @RestControllerAdvice 로 Exception 처리를 한다.
@ControllerAdvice 는 @Controller 전역에서 발생할 수 있는 예외를 잡아 처리해주는 annotation 이다.

@ExceptionHandler 에 지정한 예외, 그 예외의 자식 클래스가 발생하면 해당 메소드가 호출된다. @ExceptionHandler(IllegalArgumentException.class)
예외를 지정하지 않으면 해당 메소드의 파라미터 예외를 사용한다. public ErrorResult illegalExHandler(IllegalArgumentException e) {...
@ResponseStatus(HTTPStatus.BAD_REQUEST) 같이 HTTP 상태 코드 지정 가능.

ResponseEntity 로 HTTP 메시지 바디에 직접 응답. HTTP 응답 코드를 프로그래밍해서 동적으로 변경할 수 있음. 앞의 건 HTTP 응답 코드 동적 변경 X.
@ExceptionHandler
public ResponseEntity<ErrorResult> userExHandler(UserException e) {
    log.error("[exceptionHandle] ex", e);
    ErrorResult errorResult = new ErrorResult("USER_EX", e.getMessage());
    return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
}
HttpEntity 는 HTTP 요청 또는 응답에 해당하는 HttpHeader 와 HttpBody 를 포함하는 클래스이다.
HttpEntity 클래스를 상속받아 구현한 클래스가 RequestEntity, ResponseEntity 클래스이다.
사용자의 HttpRequest 에 대한 응답 데이터를 포함하는 클래스로 HttpStatus, HttpHeaders, HttpBody 를 포함한다.
https://devlog-wjdrbs96.tistory.com/182

@ControllerAdvice 또는 @RestControllerAdvice 를 사용하면 정상 코드, 예외 코드 분리, 전역 예외 처리 가능.
대상 지정하지 않으면 모든 컨트롤러에 적용.
@ControllerAdvice(annotations = RestController.class) 특정 어노테이션이 있는 컨트롤러 지정
@ControllerAdvice("org.example.controllers") 특정 패키지 직접 지정, 해당 패키지와 하위에 있는 컨트롤러
@ControllerAdvice(assignTypes = {ControllerInterface.class, AbstractController.class}) 특정 클래스 지정 (부모 클래스, 특정 클래스)
==========================================================
 */