
> 💡 [1장 복습](https://velog.io/@jaepal/Spring-Boot-) 이후의 내용입니다.

![](https://velog.velcdn.com/images/jaepal/post/5688a900-c6ef-4715-94cb-422d8bbe6deb/image.png)

## <span style="color: #6DB33F">Jpa Repository 🍃</span>

[1장 복습](https://velog.io/@jaepal/Spring-Boot-)에서 언급한 Spring Data JPA는 JPA의 구현체인 Hibernate를 이용하기 위한 API들을 제공한다. 이 중 많은 개발자가 사용하는 것이 **JpaRepository** 라는 인터페이스이다.

![](https://velog.velcdn.com/images/jaepal/post/257de383-ba7d-4ec6-8e62-a313c5969a4b/image.png)

Spring Data JPA에서는 여러 타입의 인터페이스 기능을 통해 JPA 작업을 간단하게 처리할 수 있도록 지원해준다.

✅ CRUD 기능
✅ 페이징 처리
✅ 정렬 기능

이렇게 **좋은 기능들을 인터페이스의 메소드들을 호출하는 형태**로 있으며 원하는 기능에 따라 상속으로 기능들을 제공해주고 있다.

이렇듯 JpaRepository는 상속하는 인터페이스를 선언만 해주는 것으로 구현이 끝난다.

**BookRepository Interface**

```java
package springbootstudy.ch1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootstudy.ch1.entity.BookMarket;

/* JpaRepository 인터페이스를 상속 */
public interface BookRepository extends JpaRepository<BookMarket, Long> {
}
```

이처럼 JpaRepository를 사용할 경우 엔티티의 정보와 @Id의 타입을 지정한다.
이렇게 되면 **스프링이 내부적으로 인터페이스 타입에 맞는 객체를 생성**하여 스프링의 빈(Bean)으로 등록이 된다!

___

## 간단한 CRUD 구현

위에서 구현한 인터페이스를 통해 SQL문 없이 아래의 메소드들을 통해 CRUD 작업이 가능하다.

+ Insert : `save(Entity)`
+ Select : `findById(Key)` , `getOne(Key)`
+ Update : `save(Entity)`
+ Delete : `DeleteById(Key)` , `Delete(Entity)`

> ⚠️ : 여기서 `Insert`와 `Updata` 작업시 사용하는 메소드가 `save()`로 동일한데, 
이는 J**PA 구현체가 메모리에서 객체를 비교한 후 값이 없을 경우 `Insert`, 존재할 경우 `Update`를 동작**시키는 방식이다.

**테스트 코드인** `BookRepoTest.java`

```java
package springbootstudy.ch1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/* 테스트 전에 의존성 주입 확인 */
@SpringBootTest
public class BookRepoTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void testClass() {
        // 인터페이스 타입의 실제 객체 확인
        System.out.println(bookRepository.getClass().getName());
    }
}

```

✅ 테스트 전에 BookRepository의 의존성 주입이 문제가 없는지 확인하는 것이 좋다.

** 결과 **

![](https://velog.velcdn.com/images/jaepal/post/e62da2ea-a3c2-4add-8b0e-cbb8f050dbc7/image.png)

해당 클래스가 동적 프록시라는 방식으로 만들어져 출력된 것을 확인할 수 있다.

___

### `Insert` 테스트

> `Insert` 작업은 다음과 같이 제공되는 메소드를 통해 사용 가능하다.
+ `save()`

등록 테스트는 한꺼번에 여러 개의 엔티티 객체를 생성하여 저장하도록 작성해봤다.

```java
package springbootstudy.ch1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springbootstudy.ch1.entity.BookMarket;
import java.util.stream.IntStream;

@SpringBootTest
public class BookRepoTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,50).forEach(i -> {
            BookMarket bookMarket = BookMarket.builder().bookName("책 샘플 번호 : " + i).build();
            bookRepository.save(bookMarket);
        });
    }
}

```

`testInsert()` 메소드 내부 내용은, `BookMarket` 객체를 생성해주고 인터페이스 상속 받은 객체 `bookRepository` 를 통해 50개의 데이터를 넣어줬다.

✅ 현재 `BookMarket` 객체의`bookName`은 조건이 **Not Null** 이기 때문에 데이터를 반드시 넣어줘야 한다.

**데이터베이스 내부 확인 결과**

![](https://velog.velcdn.com/images/jaepal/post/6e26d14f-d7fa-4557-9494-27693881fc02/image.png)

___

### `SELECT` 테스트

>조회 작업의 테스트는 다음의 메소드들을 통해 객체 조회가 가능하다.
+ `findById()`
+ `getOne()`

서로 동작 방식이 조금씩 다르다.

`findByID()`
```java
    @Test
    public void selectTest() {
        int bookNumber = 180; // 임시 조회 번호
        Optional<BookMarket> result = bookRepository.findById(bookNumber);
        System.out.println("=========== Hibernate 체크 ===========");
        System.out.println(result);
    }
}
```

`findById`의 경우 `Optional` 타입으로 변환되기 때문에 데이터가 존재하는지 체크하는 형태로 작성하게 돼서 아래와 같은 로그가 터미널에 기록된다.

**결과**

![](https://velog.velcdn.com/images/jaepal/post/afea0830-7822-4480-9ce2-18edd6f6d6ee/image.png)

`getOne()` 메소드의 경우 `Transactional` 어노테이션이 추가적으로 필요하다.

`getOne()`
```java
  @Transactional
    @Test
    public void selectGetOneTest() {
        int bookNumber = 190; // 임시 조회 번호
        BookMarket bookMarket = bookRepository.getOne(bookNumber);
        System.out.println("=========== Hibernate 체크 ===========");
        System.out.println(bookMarket);
    }
```



![](https://velog.velcdn.com/images/jaepal/post/66c511ca-e3fb-4c8b-a9f6-2678c85869c0/image.png)

`getOne`의 경우 리턴 값은 파라미터 객체이지만, SQL을 바로 실행하지 않는다.
위의 터미널 결과처럼 출력이 먼저 되고 객체를 사용하는 순간에 SQL문이 실행된다.

___

### `UPDATE` 테스트

> `Update`는 `Insert`와 동일하게 `save()` 메소드를 이용해 처리한다.

메소드 작동 시 내부에서는 해당 **`Entity`의 값이 일치하는지 확인 후 
`Insert` 또는 `Update` 처리**한다.

```java
    @Test
    public void updateTest() {
        /* 멤버 번호 50 업데이트
        *  책 이름 업데이트 : 나는 고양이로소이다 */
        BookMarket bookMarket = BookMarket.builder().memberNo(200).bookName("나는 고양이로소이다").build();
        System.out.println(bookRepository.save(bookMarket));
    }
```

**결과**

![](https://velog.velcdn.com/images/jaepal/post/ad105245-511a-424f-b2b0-3a1de7748003/image.png)


![](https://velog.velcdn.com/images/jaepal/post/bef7ef75-d63c-4457-903f-8733b33313f4/image.png)

터미널 내부에서 확인할 수 있듯이JPA가 지정한 객체가 존재하는 지
`Select`로 검색 후 `Update` 또는 `Insert` 작업할 지 정한다.

___

### `DELETE` 테스트

> `삭제`또한 동일하게 없애야 할 객체가 존재한 지 확인 후 작업을 실행한다.
+ `deleteById()`

**코드**
```java
    @Test
    public void deleteTest() {
        // 번호 51 - 200번 까지의 데이터 삭제.
        int deleteMemberNum;
        for(deleteMemberNum = 51; deleteMemberNum < 201; deleteMemberNum++) {
            bookRepository.deleteById(deleteMemberNum);
        }
    }
```

** 결과 **

```
Hibernate: 
    select
        b1_0.member_no,
        b1_0.book_name 
    from
        table_book_market b1_0 
    where
        b1_0.member_no=?
Hibernate: 
    delete 
    from
        table_book_market 
    where
        member_no=?
```

터미널 내부 모습이다. `select` 작업 후 `delete`가 실행 된 것을 확인할 수 있다.

![](https://velog.velcdn.com/images/jaepal/post/d42ab32f-be34-4d96-8194-10d634ba29b7/image.png)

데이터가 말끔히 사라졌다!

___

## `Paging` 처리

> + **페이징(Paging)** : 많은 데이터들 중 필요한 데이터들만을 보여주는 처리 방식이다.

`Spring Data JPA`에서는 페이징 처리 및 정렬을 `findAll()` 메소드를 통해 작업한다.

⚠️ **주의할 사항 !**
+ 메소드의 파라미터 값이 `Pageable` 이기 때문에 반드시 파라미터 타입을 `Pageable`로 지정해줘야 한다.

### Pageable Interface

페이지 처리를 위해서는 `Pageable` 인터페이스가 반드시 필요하다.

+ 용도 : 페이지 처리에 필요한 정보를 전달하는 타입이다.
+ 특징 : 객체 생성을 `new` 가 아닌  **`static` 타입 `of()` 메소드를 통해 처리**한다.
+ 라이브러리 : `org.springframework.data.domain.Page`

```java
of(int page, int size)
- 0부터 시작하는 페이지 번호와 개수

of(int page, int size, Sort.Direction direction, String props) 
- 0부터 시작하는 페이지 번호와 개수 & 정렬 방향 및 정렬 기준 필드

of(int page, int size, Sort sort)
- 페이지 번호와 개수 & 정렬 정보
```

이를 참고해서 테스트를 해봤다.

**테스트 코드**

```java
 @Test
    public void PagingTest() {
        Pageable pageable = PageRequest.of(0,10);
        Page<BookMarket> pageResult = bookRepository.findAll(pageable);
        System.out.println("페이지 처리 결과 : " + pageResult);
        System.out.println("총 페이지 개수 : " + pageResult.getTotalPages());
        System.out.println("전체 개수 : " + pageResult.getTotalElements()); // 페이지 처리된 데이터의 개수
        System.out.println("현재 페이지의 번호 : " + pageResult.getNumber());
        System.out.println("한 페이지의 데이터 개수 : " + pageResult.getSize());
        System.out.println("다음 페이지의 존재 여부(T/F) : " + pageResult.hasNext());
        System.out.println("현재 페이지의 첫 번째 여부(T/F) : " + pageResult.isFirst());
    }
```

**결과**

```
페이지 처리 결과 : Page 1 of 6 containing 
springbootstudy.ch1.entity.BookMarket instances

총 페이지 개수 : 6
전체 개수 : 51
현재 페이지의 번호 : 0
한 페이지의 데이터 개수 : 10
다음 페이지의 존재 여부(T/F) : true
현재 페이지의 첫 번째 여부(T/F) : true
```

현재 데이터가 51개이고, 10개의 데이터씩 페이징 처리를 하여 6개의 페이지가 존재한다.
`getContent()` 메소드를 통해 페이지 내의 데이터를 볼 수 있다.

```java
for(BookMarket bm : pageResult.getContent()) {
	System.out.println(bm);
}
```

```
BookMarket(memberNo=202, bookName=책 샘플 번호 : 202)
BookMarket(memberNo=203, bookName=책 샘플 번호 : 203)
BookMarket(memberNo=204, bookName=책 샘플 번호 : 204)
BookMarket(memberNo=205, bookName=책 샘플 번호 : 205)
BookMarket(memberNo=206, bookName=책 샘플 번호 : 206)
BookMarket(memberNo=207, bookName=책 샘플 번호 : 207)
BookMarket(memberNo=208, bookName=책 샘플 번호 : 208)
BookMarket(memberNo=209, bookName=책 샘플 번호 : 209)
BookMarket(memberNo=210, bookName=책 샘플 번호 : 210)
BookMarket(memberNo=211, bookName=책 샘플 번호 : 211)
```

___

## `Query Methods`

> 💡 **쿼리 메소드**
메소드의 이름 자체가 쿼리의 구문으로서 처리되는 기능을 갖고 있는 메소드

### 쿼리 메소드

쿼리 메소드는 사용하는 키워드에 따라 파라미터의 개수가 결정된다.
파라미터 타입 지정 시 중요한 사항은 다음과 같다.
+ `Select` : `List` 타입이나 `Array`등의 배열을 사용할 수 있다.
+ `Pageable` : 무조건 `Page<E>` 타입으로 지정해줘야한다.

** 테스트 **

먼저 `JPARepository`를 상속받은 인터페이스에 쿼리 메소드를 작성한다.

```java
package springbootstudy.ch1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootstudy.ch1.entity.BookMarket;

import java.util.List;

public interface BookRepository extends JpaRepository<BookMarket, Integer> {
    List<BookMarket> findByMemberNoBetweenOrderByMemberNoDesc(Integer from, Integer to);
}
```

메소드 이름의 가독성이 낮지만.. 해석해보면 `memberNo`를 기준으로 `Between` 구문을 사용하여 `OrderBy`를 적용하고 내림차순으로 정렬한 질의문이라고 볼 수있다.

```java
    @Test
    public void queryMethodsTest() {
        List<BookMarket> list = bookRepository.findByMemberNoBetweenOrderByMemberNoDesc(210,220);

        for(BookMarket bm : list) {
            System.out.println("쿼리 메소드 적용 결과 : " + bm);
        }
    }
```

```
Hibernate: 
    select
        b1_0.member_no,
        b1_0.book_name 
    from
        table_book_market b1_0 
    where
        b1_0.member_no between ? and ? 
    order by
        b1_0.member_no desc
        
쿼리 메소드 적용 결과 : BookMarket(memberNo=220, bookName=책 샘플 번호 : 220)
쿼리 메소드 적용 결과 : BookMarket(memberNo=219, bookName=책 샘플 번호 : 219)
쿼리 메소드 적용 결과 : BookMarket(memberNo=218, bookName=책 샘플 번호 : 218)
쿼리 메소드 적용 결과 : BookMarket(memberNo=217, bookName=책 샘플 번호 : 217)
쿼리 메소드 적용 결과 : BookMarket(memberNo=216, bookName=책 샘플 번호 : 216)
쿼리 메소드 적용 결과 : BookMarket(memberNo=215, bookName=책 샘플 번호 : 215)
쿼리 메소드 적용 결과 : BookMarket(memberNo=214, bookName=책 샘플 번호 : 214)
쿼리 메소드 적용 결과 : BookMarket(memberNo=213, bookName=책 샘플 번호 : 213)
쿼리 메소드 적용 결과 : BookMarket(memberNo=212, bookName=책 샘플 번호 : 212)
쿼리 메소드 적용 결과 : BookMarket(memberNo=211, bookName=책 샘플 번호 : 211)
쿼리 메소드 적용 결과 : BookMarket(memberNo=210, bookName=책 샘플 번호 : 210)
```

### `Pageable` 활용

방금과 같이 `OrderBy`등을 사용하면 쿼리 메소드의 이름이 길어지기 때문에 `Pageable`을 이용해 정렬 처리를 하면 간단하게 생성이 가능하다.

**테스트**

```java
Page<BookMarket> findByMemberNoBetween(Integer from, Integer to, Pageable pageable);
```

```java
    @Test
    public void pageableQueryMethodsTest() {
        /* 오름차순 정렬 */
        Pageable pageable = PageRequest.of(0,10, Sort.by("memberNo").ascending());
        Page<BookMarket> result = bookRepository.findByMemberNoBetween(240,250,pageable);
        result.get().forEach(bookMarket -> System.out.println(bookMarket));
    }
```

**결과**

```
Hibernate: 
    select
        count(b1_0.member_no) 
    from
        table_book_market b1_0 
    where
        b1_0.member_no between ? and ?
        
BookMarket(memberNo=240, bookName=책 샘플 번호 : 240)
BookMarket(memberNo=241, bookName=책 샘플 번호 : 241)
BookMarket(memberNo=242, bookName=책 샘플 번호 : 242)
BookMarket(memberNo=243, bookName=책 샘플 번호 : 243)
BookMarket(memberNo=244, bookName=책 샘플 번호 : 244)
BookMarket(memberNo=245, bookName=책 샘플 번호 : 245)
BookMarket(memberNo=246, bookName=책 샘플 번호 : 246)
BookMarket(memberNo=247, bookName=책 샘플 번호 : 247)
BookMarket(memberNo=248, bookName=책 샘플 번호 : 248)
BookMarket(memberNo=249, bookName=책 샘플 번호 : 249)
```

### `deleteBy`

> 쿼리 메소드를 `deleteBy`로 시작하여 작성하면 **조건에 맞는 데이터 삭제 처리가 가능**하다.

**테스트**

```java
public interface BookRepository extends JpaRepository<BookMarket, Integer> {
	void deleteBookMarketByMemberNoLessThan(Integer number);
}
```

```java
@Commit
@Transactional
@Test
public void deleteQueryMethodsTest() {
	/* 멤버 번호가 230 이하인 데이터 삭제. */
	bookRepository.deleteBookMarketByMemberNoLessThan(230);
}
```

**결과**

![](https://velog.velcdn.com/images/jaepal/post/047cb79c-2867-4ebb-814a-62ab4affb372/image.png)

+ `Commit` 어노테이션 : 최종 결과를 커밋하기 위해 사용하는 어노테이션
+ `Transactional` 어노테이션 : `select` 문으로 엔티티 객체들을 가져오는 작업 & 엔티티를 삭제하는 작업을 같이 하기 위한 어노테이션

___

## `Query` 어노테이션

> 💡`@Query`
`SQL`과 유사한 엔티티 클래스의 정보를 이용하여 쿼리를 작성하는 기능을 갖고 있는 어노테이션

쿼리 메소드는 복잡한 조건을 처리해야 할 경우 메소드 이름이 길어지는 등,
불편할 경우가 많기 때문에 쿼리 어노테이션을 이용할 수 있다.

쿼리 어노테이션을 통해 다음과 같은 작업이 가능하다.

✅ ** 필요한 데이터만 선별적으로 추출 ** 하는 작업 기능
✅ 데이터베이스에 맞는 순수한 **`SQL(Native SQL)` 작성 기능**
✅ `Insert` , `Update` , `Delete` 와 같은 **`DML` 처리 기능**

객체지향 쿼리는 테이블 대신 `Entity` 클래스를 이용하며 클래스 멤버 변수를 이용해 작성한다.

```java
@Query("select m fro BookMarket m order by m.meberNo desc")
List<BookMarket> getListDesc();
```

### 파라미터 바인딩

쿼리 어노테이션의 경우 `SQL`문과 유사하기 때문에 `where`문과 같은 파라미터 처리를 사용할 때가 많다. 이 때 작성 방식은 다음과 같다.

+ '?1' , '?2' & 1부터 시작하는 `파라미터` 순서를 이용한 방식
+ `':xxx'` 와 같이 `':파라미터 이름'`을 사용하는 방식
+ `:#{}` 을 이용하는 자바 빈 스타일의 작성 방식

두 번째 방식을 이용한다면 다음과 같이 작성한다.

```java
@Transactional
@Modifying
@Query("update BookMarket m set m.bookName = :bookName where m.memberNo = :memberNo")
int updateBookName(@Param("memberNo") int memberNo, @Param("bookName") String bookName);
```

여기서 파라미터 전달을 여러 개 해야한다면 `:#` 을 이용해 객체 사용이 가능하다.
엔티티 객체 자체를 파라미터로 전달해주면 된다.
```java
@Transactional
@Modifying
@Query("update BookMarket m set m.bookName = :#{#param.bookName} where m.memberNo = :#{#param.memberNo}")
int updateBookName(@Param("param") BookMarket bookMarket); // 파라미터를 엔티티 객체 자체로 적용한다.
```

### `Native SQL`

> 💡 **Native SQL**은 쿼리 어노테이션의 강력한 기능중 하나다.
 **데이터 베이스의 `SQL` 구문을 그대로 사용할 수 있는 기능**을 제공한다.

```java
/* Native SQL */
@Query(value = "select * from BookMarket where memberNo > 50" , nativeQuery = true)
List<Object[]> nativeSQLSelect();
```

___

## 마치며

스프링 부트는 개발자가 좋아할만한 요소는 다 넣은듯 하다! 🤔
특히 귀찮게 왔다갔다 할 일 없이 스프링 부트 내부에서도 DB 컨트롤이 가능한게 큰 장점이다.

> + **참고 내용 **
📖 코드로 배우는 스프링 부트 웹프로젝트