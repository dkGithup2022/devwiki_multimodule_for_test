
## 데브위키 멀티모듈 개선 시나리오


### 멀티 모듈 적용 목표 

 - 프로젝트를 이루는 각 서브 프로젝에서의  도메인 , client 모듈을 공통사용 할 수 있게 한다.

 - 필요한 부분만 가져갈 수 있도록 설계한다.
   - 도메인을 가져갈 때, db 커넥션에 대한 설정은 안가져가도 된다 .
   

### 개선된 멀티모듈 분류 

- client 
- core-infra 
-  - 도메인 별 분류 없음 
- core-domain 
-  - pure java 유지 
- - 도메인 별 분류 없음 , 패키지로만 분류
- core-domain-service 
- -   각 앱에서 사용할 서비스 의 모음
- -   도메인 별 분류 없음 , 패키지로만 분류 
- app
-  - 프로젝트에 속하는 배포 될 각 서브 프로젝트들 .


### AS-IS 의 문제점 (이전 커밋)

1. 각 도메인 별 별도의 domain, infra를 가짐.
    - 아직 도메인간 분리가 성숙하지 않았음 ( 변경의 여지가 있다)
    - 너무 많은 모듈, 직관적이지 않음 


2. 도메인 모듈 내에 의존관계가 의도한 것보다 많음 .
   - domain 모듈에 service 어노테이션, transactional 어노테이션이 포함되어서 이제 사실상 의존성에 대한 분리가 안된것처럼 보인다

   - 따라서, 이후 깔끔한 재사용이 안될수도 있다.


### 해야하는 것 

1. domain, infra, domain-service 로 모듈을 전체적으로 분리, 각 도메인에 대한 분리는 패키지로만 한다.


2. 도메인 래밸 로직과 어플리케이션 래밸 로직의 분리.



## 구현 시나리오

 article, comment 에 대한 단순한 crud 기능을 멀티모듈로 올려 본다.
 
모듈화의 기본적인 구분는 아래에서 부터 

1. 외부 인터페이스에 대한 client 
    - redis , feign 등의 접속 설정이 포함된다.
    - rdms 도 여기서 가능하면 정의 . ( ConditionalOnMissingBean )
   

2. jpa 에 대한 infra 레이어
   - rdms-infra 
   - jpa 에 대한 설정과 jpa repository, entity 정의가 포함됨
   

3. 도메인 객체에 대한 pure java ( layer 의 관점은 아니다. )
    - 도메인 객체와 도메인 행위에 대한 인터페이스 정의 


4. (2),(3) 을 사용하는 해당 서비스에 대한 서비스 모듈
   - web-domain-service 
   - 이 단계에서 infra 를 의존받아서 jpa 설정을 끌어오면 실제 그 서버에서 커넥션이 생성됨.
   - domain 객체만 필요한 다른 서버는 이거 말고 도메인 모듈만 가져 쓰면 된다. 

5. app 을 실행하는 모듈 
   - (4) 와 (5) 는 합쳐져도 된다는 주의긴 함 . 
   - 다만 api 형식이 bff 의 형식을 띈다면, 비즈니스 로직과 presentation 되는 것이 분리가 되는게 좋을 것 같으니, 이런 이제 주된 crud 에대한 api 에 대해서만 (4),(5) 를 분리하는게 낫지 않을까 싶다 .

따라서, (4),(5) 의 분리는 선택인것 같다. 



### 요구사항 


#### 공통 - enum

- 아티클의 타입은 "Tech" 밖에 없다
- (비기능적) 이 타입은 이 프로젝트 내 모든 서브프로젝트에서 조회 가능해야 한다. 


#### 공통 - rdms 
- localhost 3306 의 devwiki 데이터베이스와 통신한다.
- (비기능적) 모든 서브 프로젝트에서 공통으로 적용할 정책을 별도 모듈에서 관리한다 .

#### 아티클 - 도메인

- 아티클의 생성에서 title, content 는 null 이 아니게 한다.
- (비기능적) validation 함수를 람다의 리스트로 받을 수 있게하여 유연하게 관리한다.
- 적절한 생성 함수를 제공한다. 
- 가능한 불변의 상태를 유지한다.  

#### 아티클 - 어플리케이션

- id 를 이용해 조회가능해야 한다.
- 생성에는 작성자, 제목, 내용을 넣는다.
- 아티클을 지울 수 있어야 한다.
- 작성자의 id 를 입력받아서, 수정이 가능해야한다. 
  - 수정 시, 유효하지 않은 객체를 받으면 에러를 반환한다.

#### 댓글 - 도메인
- 댓글의 작성자, 내용, 글 id 는 null 을 허용하지 않는다. 
- 생성 함수를 제공한다.


#### 댓글 - 어플리케이션 

- 댓글의 생성, 조회 를 제공해야한다.
- 글 id 를 입력받아 댓글의 리스트를 제공할 수 있어야 한다. 



## 구현 

### 의존성 

#### client-mysql 공통  의존성 


```gradle
plugins {
    id 'org.springframework.boot'
    id 'java-test-fixtures'
}

apply plugin: "java-library"

description("client-mysql module")

bootJar { enabled = false }
jar { enabled = true }

dependencies {
    implementation(project(":devwiki-common"))
    api ("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("com.h2database:h2")
    implementation ("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // query dsl
    api ("com.querydsl:querydsl-core")
    api ("com.querydsl:querydsl-jpa")
    annotationProcessor("com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jpa")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
}

```


#### core-infra-rdms 의존성 

```java

plugins {
    id 'org.springframework.boot'
    id 'java-test-fixtures'
}

apply plugin: "java-library"

description("core-infra-rdms module")

bootJar { enabled = false }
jar { enabled = true }

dependencies {
    implementation(project(":devwiki-common"))
    implementation(project(":client:client-mysql"))
    api ("org.springframework.boot:spring-boot-starter-data-jpa")

}


```

#### core-domain

```
plugins {
    id 'org.springframework.boot'
    id 'java-test-fixtures'
}

apply plugin: "java-library"

description("core-domain module")

bootJar { enabled = false }
jar { enabled = true }

dependencies {
    implementation(project(":devwiki-common"))
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}

```

#### core-domain-service 


```
plugins {
    id 'java'
}

group = 'com.dk0124'
version = 'unspecified'

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":devwiki-common"))
    implementation(project(":core-domain"))
    implementation(project(":core-infra-rdms"))

    implementation('org.springframework:spring-context')
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}

test {
    useJUnitPlatform()
}

```

#### app-web-api

```
description("app-search-api module")

dependencies {

    implementation(project(":core-domain"))
    implementation(project(":core-domain-service"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

}
```