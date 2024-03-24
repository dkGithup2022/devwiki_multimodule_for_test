

### mysql 정책에 대한 공통적인 내용 관리 .

mysql connection 에 대한 정책을 공통으로 관리하는 모듈입니다.
진행 중, hibernate logging 이나 db configuration 에 대한 정보를 여기서 관리해 주세요 



</br>

spring 관련 의존성이 전파됩니다. pure java 를 쓰는 의존성에선 해당 모듈을 사용하지 마세요.

</br>

#### 주의 : 전파되는 spring  의존성

- api ( 전파되는 의존성 )
-  spring boot , jpq, query dsl 의존성은 api 로 선언함.
  - > 메모 : 해당 속성에 의해 이 모듈이 실효가 없어지면 각 infra layer 로 설정을 내려야함



</BR>

- 아래는 3/24 기준 의존성 설정
```

dependencies {
    implementation(project(":devwiki-common"))
    // 전파되어도 좋은 의존성 
    api ("org.springframework.boot:spring-boot-starter")
    api ("org.springframework.boot:spring-boot-starter-data-jpa")
    api ("org.springframework.boot:spring-boot-starter-validation")
    api ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    runtimeOnly("com.h2database:h2")
    implementation ("com.mysql:mysql-connector-j")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(project(":devwiki-common"))

    // query dsl 
    api ("com.querydsl:querydsl-core")
    api ("com.querydsl:querydsl-jpa")
    annotationProcessor("com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jpa")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
}

```