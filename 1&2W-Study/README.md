> 💡학교 강의를 토대로 복습한 내용입니다.
내용이 깔끔하지 않을수 있으니 양해좀 해주세요.. 🙏

![](https://velog.velcdn.com/images/jaepal/post/5688a900-c6ef-4715-94cb-422d8bbe6deb/image.png)

# <span style="color: #6DB33F">Spring Boot 🍃</span>

> 스프링 부트란 **Spring**이 가졌던 느낌을 되살리며, 최소한의 설정으로 별도의 *WAS* 없이 실행이 가능한 **자바의 웹 프레임워크**이다.


+ 스프링 부트의 장점

✅ **자체적으로 내장 웹 서버를 지원**한다.

✅ **쿠키&세션 처리, 인가(Authorization), 로그인 기능** 등을 기본적으로 지원해준다.

✅ 기존 스프링에 비해 설정이 간단하다.

___

## Spring Data JPA

> 프레임워크에서 JPA를 편리하게 사용할수 있도록 제공해주는 요소다.

💡 **JPA란?**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;➡️ 자바에서 제공하는 API이며 SQL을 직접 작성하지 않고 JPA를 통해 적절한 SQL문을 작성하여** 객체를 자동적으로 전달 및 매핑**해준다.

이 외에 이런 유용한 기능도 제공해준다.

⭐ **CRUD 처리**를 위한 인터페이스 제공
⭐ 인터페이스만 작성해도 JPA가 구현 객체를 동적으로 생성한다.

### ORM(`Object Relation Mapping`)

> 객체지향 패러다임을 관계형 데이터베이스에 보존하는 기술이다.

ORM은 객체지향과 관계형 데이터베이스가 유사한 점이 매우 많다.
+ 객체지향 언어에서의 **`클래스`는 데이터의 구조를 설계**한다.
+ 관계형 데이터 베이스는 **`클래스`는 아니지만 `Table`을 설계**한다.

![](https://velog.velcdn.com/images/jaepal/post/f03b826f-2e77-489d-aaf9-250b938f0731/image.png)

```java
class Member {
	private String id;
    private String pw;
    private String name;
}
```
이렇듯 테이블은, 컬럼을 정의하며 컬럼에 데이터 타입을 지정한다는 점에서 객체지향의 클래스와 유사한 점이 많다.

이외에도  `인스턴스` & `레코드`또한 유사한 점이 있다.
+ 클래스는 클래스에서 인스턴스를 생성하여 인스턴스에 데이터를 보관한다.
+ DB는 하나의 행(Row)에 데이터를 저장(레코드 OR 튜플)한다.

한 가지 다른 점이라면 **객체지향에서의 객체는 데이터와 행위(Method)**로 이루어져 있지만 ** DB에서는 개체(Entity)가 오직 데이터만 존재** 한다는 것이다.


ORM은 이러한 점을 기초로 하여 객체지향의 요소를 관계형 데이터베이스에 맞게 자동적으로 처리해주는 기법에서 시작된 것이다! 

> 💡즉 이를 통해 알수있는 점은 JPA(`Java Presistence API`)는 ORM의 하위 개념이라 볼수있다.

![](https://velog.velcdn.com/images/jaepal/post/60bd0ed9-2649-4772-9c0e-fcaeccc56a06/image.png)

이 중 스프링 부트는 `JPA`의 구현체 중 `Hibernate` 라는 구현체를 이용한다.
`Spring Data JPA`를 프로젝트 생성시 의존성을 추가하게 되면 유용한 API들을 제공해준다.

![](https://velog.velcdn.com/images/jaepal/post/509ec945-b46e-4f3c-b30b-372191112a56/image.png)

`Spring Data JPA`를 이용하면 다음과 같이 용이하게 DB 접근이 가능하다.
`Spring Data` → `Hibernate` → `JDBC` → `DB`

### Spring Data JPA 사용 시 필요 조건 ✅

해당 글에서는 `Maria DB`를 기준으로 설명합니다.

+ `MariaDB`와 연계하기 위해 `JDBC` 드라이버를 설정해야한다.
+ 스프링 부트 프로젝트 내에서 `MariaDB` 설정

`MariaDB`용 JDBC 드라이버는 [Maven Repository 홈페이지](https://mvnrepository.com/artifact/org.mariadb.jdbc/mariadb-java-client) 에서  찾을수 있다.

![](https://velog.velcdn.com/images/jaepal/post/6de94e05-5787-487f-a1ca-f83f5d401472/image.png)

+ `Gradle` 항목에서 관련 설정을 복사한다.
+ 설정하려는 프로젝트의 `build.gradle` 파일의 `dependencies` 항목에 추가한다.

![](https://velog.velcdn.com/images/jaepal/post/5347639a-870f-48f1-bfad-c27080c1e018/image.png)

+ `application.properties` 파일 내에 `JDBC Driver`와 연결하기 위한 내용을 추가한다.

주의 ! ⚠️ 
> MariaDB에 등록된 테이블 명 & 사용자 이름과 비밀번호를 등록해줘야한다.

```js
spring.datasource.driver-class-name = org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://localhost:3306/bootex
spring.datasource.username=bootUser
spring.datasource.password=bootUser
```
이후 프로젝트 실행 시 터미널에 정상적으로 실행 됐는지 확인한다.

![](https://velog.velcdn.com/images/jaepal/post/3a9dbfb0-c091-42b4-a98e-750772fab892/image.png)

___

## `Entity`

`Spring Data JPA`에서 개발에 필요한 것은 두 종류의 코드이다.

+ `JPA`를 통해서 관리하게 되는 객체(`Entity`)를 위한 `Entity` 클래스
+ 엔티티 객체들을 처리하는 기능을 가진 `Repository`

✅ `Repository`는 `Spring data JPA`에서 제공하는 인터페이스로 설계하는데 스프링 내부에서 자동으로 객체를 생성하고 실행하는 구조를 갖고있다.

### `Entity` 클래스 작성

`BookMarket`라는 클래스를 예시로 만들면 다음과 같다.
엔티티 클래스는 마치 `DB`의 테이블과 같은 구조로 작성한다.

```java
package springbootstudy.ch1.entity;
import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name="Table_BookMarket")
@ToString
public class BookMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;
}

```

#### `@Entity`

🟡 엔티티 클래스의 `Spring Data JPA` 에서는 **`@Entity` 라는 어노테이션이 추가돼야한다. **
🟡 `@Entity`는 **해당 클래스가 엔티티 클래스**를 위한 클래스이다.
🟡 해당 클래스의 인스턴스들이 **`JPA`로 관리되는 엔티티 객체**임을 의미한다.

또한 `@Entity`가 붙은 클래스는 옵션에 따라 자동적으로 테이블을 생성해준다.
이를 이용하여 클래스 내의 멤버 변수에 따라 컬럼들도 자동적으로 생성된다.

#### `@Table`

🟠 어노테이션의 이름 그대로 `DB`상에서 엔티티 클래스를 어떠한 테이블로 생성할 것인지 정의하는 어노테이션이다.

현재 정의한 테이블 명은 `Table_BookMarket` 이다.

#### `@Id` & `GeneratedValue`

🟢 `@Entity`가 붙은 클래스는** `PK` 에 해당하는 필드를 `@Id` 어노테이션으로 지정** 해줘야한다.

또한 `@Id` 사용자가 입력하는 값을 사용하는 경우가 아니면 **자동으로 생성되는 번호를 사용하도록 `@GeneratedValue` 어노테이션**을 사용한다.

🟢 어노테이션 괄호안에 담긴 내용인 `GenerationType.IDENTITY`의 경우 PK를 자동으로 생성해주는 부분이다.

#### `@Column`

클래스 내에 추가적인 필드가 필요한 경우에도 어노테이션을 통해 생성이 가능하다.
`nullable` , `name`, `length` 등을 이용해 속성에 다양한 값들을 부여할 수 있다.

```java
package springbootstudy.ch1.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Table_BookMarket")
@ToString
/* 어노테이션 추가 */
@Getter     // Getter 메소드를 생성한다.
@Builder    // 객체 생성할 수 있게 처리해준다.

/* Builder 사용을 위해 ArgsConstructor를 같이 처래해줘야 컴파일 에러 방지가 가능하다. */
@AllArgsConstructor
@NoArgsConstructor
public class BookMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;

    @Column(length = 50, nullable = false) // 컬럼의 크기 150까지 & NULL 값 허용하지 않음.
    private String bookName;
}

```

여기서 자동으로 필요한 테이블의 생성과 `JPA`를 이용할 때 발생하는 `SQL`의 내용을 터미널에서 확인하기 위해 `application.properties`를 변경 해줘야한다.

```python
# 내용 추가
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true
```

+ First Line : **프로젝트 실행 시 자동으로 `DDL`을 생성**할 것인지 결정하는 설정이다.
+ Second Line : 실제 **`JPA`의 구현체인 `Hibernate`가 동작하며 발생하는 `SQL`을 포맷팅**해 출력하는 설정이다.
+ Third Line : **`JPA` 처리 시 발생하는 `SQL`을 보여줄 것인지 결정**한다.

해당 설정 후 프로젝트를 실행해보면 `Hiberante`가 터미널에서 테이블을 생성한 `SQL`문을 출력된 것이 확인된다.

![](https://velog.velcdn.com/images/jaepal/post/f1463152-75e2-4ccc-a1aa-b87f083f4f8a/image.png)

**`MariaDB` 에서 테이블 확인**

![](https://velog.velcdn.com/images/jaepal/post/c9ed1fb7-bf6d-43a0-abaa-6e09467a81ee/image.png)

이처럼 `MariaDB`에서 테이블을 사용하지 않더라도 `Class`를 통해 간단하게 구현이 가능한 것을 알 수 있다!

___



## 마치며

`Java`를 유독 좋아하는 나로서 스프링 부트 공부는 정말 재밌다.
다음 챕터 에서 다뤄볼 내용은 `JPA Repository`를 이용해 `CRUD`를 구현해보려고 한다.

> + 참고 내용 
📖 코드로 배우는 스프링 부트 웹프로젝트
