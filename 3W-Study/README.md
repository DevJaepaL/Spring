# 3주차 정리

> 💡학교 강의를 토대로 복습한 내용입니다.
내용이 깔끔하지 않을수 있으니 양해좀 해주세요.. 🙏

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

**결과**

![](https://velog.velcdn.com/images/jaepal/post/e62da2ea-a3c2-4add-8b0e-cbb8f050dbc7/image.png)

해당 클래스가 동적 프록시라는 방식으로 만들어져 출력된 것을 확인할 수 있다.

___

### Insert 테스트

등록 테스트는 한꺼번에 여러 개의 엔티티 객체를 생성하여 저장하도록 작성해봤다.

