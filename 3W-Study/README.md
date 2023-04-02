
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

조회 작업의 테스트는 다음의 메소드들을 통해 객체 조회가 가능하다.

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

**터미널**

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