# **요구 사항**

## SpringDataJPA 를 설정한다

- [x] datasource : h2



## 엔티티를 구성한다

- [x] 회원(User)

  - [x] id (PK) (auto increment)
  - [x] name
  - [x] age
  - [ ] hobby
    - 계층형 구조를 갖는 `InterestCategory`와 연결하여 처리하려고 엔티티 정의까지는 했습니다만, 시간 문제로 아직 연관관계 설정을 반영하지 못 했습니다.

  - [x] created_at -> createdDate
  - [ ] created_by -> 추후 세션 추가하면서 추가할 예정입니다.

- [x] 게시글(Post)

  - [x] id (PK) (auto increment)
  - [x] title
  - [x] content

  - [x] cre`ated_at -> createdDate
  - [ ] created_by -> 추후 세션 추가하면서 추가할 예정입니다.

- [x] 

- 회원과 게시글에 대한 연관관계를 설정한다.
   - 회원과 게시글은 1:N 관계이다.
- [x] 게시글 repository 구현한다.





## API를 구현한다.

- Post, User에 대해 다음 기능들을 구현했습니다:

- [x] 게시글 조회
   - [x] 페이징 조회 (GET "/posts")
   - [x] 단건 조회 (GET "/posts/{id}")
- [x] 게시글 작성 (POST "/posts")
- [x] 게시글 수정 (PATCH"/posts/{id}")
- [x] 게시글 삭제(DELETE"/posts/{id}")



## REST-DOCS를 이용해서 문서화한다.

- [x] 현재 Post API쪽만 구현된 상태입니다.
- `/docs/output`에서 확인 가능합니다.



-----

# 코드 설명

- 이번 프로젝트를 진행하면서 고민했던 포인트 + 왜 이렇게 짰는지 설명이 필요하다고 느낀 포인트 입니다.

### Domain

- `User`
  - **값을 바로 엔티티의 필드로 사용하지 않고, Wrapping한 객체를 만들어서 사용하려고 노력했습니다.**
  - 예시
    - `Age`
      - 같은 생일이라도 여러 요구사항에 대응하기 위해
        - 성인 인증이 필요한 경우(청소년 보호법에 의한) -> 연 나이
        - 국제적으로 사용하는 만 나이
      - 등 여러 방식으로 출력할 수 있게 만든 클래스입니다.
    - `Name`
      - 명(firstname)과 성(lastname)을 묶어, 하나의 객체로 관리합니다.
      - 필요에 따라 Locale 정보를 받아서, 해당 국가의 문화에 맞는 방식으로 성명을 출력하는 메서드를 구현했습니다.
  - 위와 같이 엔티티의 필드들을 클래스로 묶었기 때문에 `@Embeddable`과 `@Embedded` 기능을 적극 사용했습니다.

### Repository

- 게시글(Post)의 경우 작성자의 Nickname을 가지고 와야 하기 때문에 `User`와 조인이 필요합니다.
  - 조인이 필요한 경우에는`@EntityGraph` 기능 이용했습니다.(단, 현재까지 코드에서는 N + 1 문제가 발생하지는 않기 때문에 방어적인 차원에서 작업했습니다.)
  - 위와 같이 `FetchJoin` 사용한 곳에서는 `countQuery`를 별도 규정하여 성능 향상을 꾀했습니다.

### Service

- 연관관계를 맺어 저장만 해야 하는 경우에는 프록시 이용을 위하여 DataRepository의 `getById` 사용했습니다.



# PR 포인트

## PR 포인트 1

- `Name`을 객체로 분리하는 김에, 사용자의 `Locale` 정보에 따라 성/이름 순서를 달리 하여 이름을 출력하는 방식의 기능을 구현했습니다.
  - 하지만 기능을 구현했음에도 이번 프로젝트에서는 아직 사용하지 않았는데, 그 이유는
    API 구현 프로젝트이기 때문에 API 스펙을 깨트리기만 할 것 같아서입니다.
- 질문 포인트는
  - Thymeleaf 등 SSR 기술을 이용한 경우 외에 위와 같은 방식의 국제화를 서버 단에서 할 일이 있을까요?



## PR 포인트 2

- 요구사항의 Hobby를 좀 더 복잡한 것으로 만들어서 공부하고 싶어서, 계층적 범주 구조를 갖는 `Interest`로 만들어보려고 했습니다.
  - 예를 들어, `User`는 `개발>백엔드>자바>JVM`을 관심사로 체크할 수 있는 방식으로요.
  - 그런데, 카테고리를 사용하는 상황이라면 항상 해당 카테고리의 상위/하위 카테고리를 로딩해야 하기 때문에
    성능 상으로 이슈가 생길 수 밖에 없다고 생각했습니다.
  - 이렇게 자주 쓰이고, 변경이 적은 데이터라면 자체적으로 애플리케이션 내에서 `Map`으로 캐싱하고 있다가, JPA의 변경 감지처럼 Repository에서 CUD가 발생할 때 갱신해 주는 식으로 한다면 성능 최적화를 꾀할 수 있을 것 같은데,
  - 이런 방식을 사용해도 괜찮을까요?



## PR포인트 3

- JPA 사용시 Update 방식
- 이 프로젝트에서 저는 엔티티 수정을 다음과 같은 방식으로 구현했습니다.
  - `Controller`가 `UpdateForm` 데이터를 서비스로 전달한다.
  - `repository`에서 영속성 컨텍스트 안에 있는 엔티티를 `findById`로 조회한다.
  - `UpdateForm`을 조회한 엔티티에 적용한다.
    - `UpdateForm`의 `apply`메서드는 `UpdateForm`의 값들 중에서 비어 있는 값인지 아닌지 검사하고, 그걸 적용한 엔티티를 새로 만든다.
    - 새로 만든 엔티티의 값을 원본 엔티티의 `update`메서드를 이용하여 적용한다.
- 이런 복잡한 방식을 사용한 까닭은
  - `setter`를 사용하지 않기 위함이었습니다.
    - 한 번에 엔티티의 변경사항을 적용하기 위해서 완전한 값임이 검증된 엔티티를 Dto에서 생성, 전달하는 방식으로 하였습니다.
- JPA를 사용하면서 `setter`를 외부에 노출시키지 않으면서, update 기능을 깔끔하게 처리할 수 있는 방법이 있을지 궁금합니다.



## PR 포인트 4	(코드 상으로 3과 같은 위치입니다)

- `Service` 계층에서, `Create/Update`의 경우 `Controller`에서 `Form `데이터로 변경에 필요한 정보를 받아서 처리하고 있습니다.
- 그런데 `Controller`의 `Form` 데이터는 변경이 잦기 때문에, 이렇게 되면 `Service` 레이어가 함께 지저분해질 것이라고 생각했습니다.
- 그래서 버전별로 관리되는 `Form`들을 구현체로 만들고, 그것들을 묶어 주는 인터페이스를 생성해서, 서비스가 바라보게 했는데, 이런 방식으로 분리하는 것이 괜찮을 지 궁금합니다.
  - 저 같은 경우에는 이 방식이 깔끔한 해결을 해줄 것이라고 생각했는데,
  - 그냥 각 인자를 보내는 식으로 서비스 메서드 시그니처를 여럿 만들거나
  - 필요에 따라 서비스 클래스 자체를 여럿으로 분리하는 방식도 가능할 것 같아서요.
- 멘토님의 의견이 궁금합니다.



## PR 포인트 5

- `RestControllerAdvice`의 적용 범위를 위해, Marker 인터페이스를 사용해 보았습니다.
  - API의 경우 버저닝을 할 일이 종종 생길 수 있다고 생각하여, 버전이 바뀌어도, 동일한 방식의 예외 처리가 필요할 때를 대비해서 마커 인터페이스로 컨트롤러를 한 번 감싸 주었습니다.
  - 지나치게 인터페이스를 사용한 것은 아닌지 고민이 되었습니다.



## PR 포인트 6

- mockMvc를 사용한 테스트 중에 `content()`를 검사하려고 했습니다만 `json`으로 변환하는 과정에서 `lastModifiedDate`와 같은 값의 밀리세컨드 단위에서 오차가 계속 발생했습니다.
- 5시간동안 해결하려고 했는데, 도무지 해답이 보이지 않아서 멘토님께 여쭤봅니다.
- `expect`의 값으로 준 데이터는 `ObjectMapper`에 의해 JSON 변환되면서 마지막 자리수가 손상되는 한편, MockMvc쪽에서 내보내는 데이터는 손상이 없는 것 같은데
  - Postman으로 테스트 할 때는 `objectMapper` 쪽과 같이 마지막 자리수가 잘려서 응답이 전달됩니다.
  - 때문에 MockMvc를 사용해서 발생하는 문제인 것 같은데, 혹시 어떤 부분을 공부하면 해결이 가능할지 궁금합니다.



## PR 포인트 7

- REST API 설계할 때 카카오 API 명세를 많이 참고했는데, 의문이 들었던 부분이 있었습니다.
- Response로 ID 값을 (그것도 회원번호를) 전달하던데, 이런 방식으로 전달해도 되나..? 싶어서 고민이 되었습니다. [링크](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info)
  - DB의 ID 값은 문자 그대로 식별자기에, 사용자 개인을 특정할 수 있는 "개인정보"의 정의에 맞지 않나요?
  - 실무에서 이런 값을 Response로 넘겨줄 때 어떻게 처리하시는지 궁금합니다.

- 같은 맥락으로, 개인 별 페이지로 접근할 때 `users/{userId}`형태와 `users/me`형태 중에 무엇이 나을지 고민됩니다.
  - 일단은 `{userId}` 형태로 URI를 설계했습니다: 그게 자원의 위치를 명확하게 보여줄 수 있다고 생각했기 때문입니다.
  - 하지만 ID 값이 공개된다는 점에서, 그리고 어차피 자신의 개인정보만 볼 수 있다는 점에서 `users/me` 형태의 URL 설계가 좋아보이기도 합니다.
  - 멘토님이 실무에서 사용하시는 방식과, 의견이 궁금합니다!



-----

### 아직 추가적으로 구현하거나, 다듬을만한 상황이 많은데, 빠르게 추가해보도록 하겠습니다. 항상 리뷰 감사합니다.
