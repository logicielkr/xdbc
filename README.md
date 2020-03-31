# xdbc

## 1. about

jdbc의 몇 가지 확장 기능을 제공한다.

### 1.1. xdbc의 기능

* sql 로그(log4jdbc 와 유사)
* 2개 이상의 데이타베이스 복제본을 만드는 기능 (실시간 백업)
* 현재 열려있는 데이타베이스 연결을 확인하는 기능(반납되지 않은 Connection, Statement, ResultSet 객체를 확인하는 기능)

### 1.2. 지원하는 데이타베이스

* org.apache.derby.jdbc.AutoloadedDriver
* org.sqlite.JDBC
* org.postgresql.Driver
* oracle.jdbc.driver.OracleDriver
* org.hsqldb.jdbc.JDBCDriver
* org.h2.Driver
* org.mariadb.jdbc.Driver

### 1.3. 제한

* xdbc는 단독으로 사용할 수 없고, 각각의 JDBC 드라이버가 있어야 한다.

### 1.4. 배포하는 곳

* 소스코드 : <https://github.com/logicielkr/xdbc>
* 웹사이트 : <https://xdbc.kr>

## 2. 사용방법

### 2.1. 데이타베이스에 연결하는 방법

* JDBC 드라이버 : kr.xdbc.driver.GenericDriver
* Connection URL : xdbc: 을 접두어로 붙인다.

#### 2.1.1. 프로그램적으로 연결하는 방법

```java
Class.forName("kr.xdbc.driver.GenericDriver");
Connection con = DriverManager.getConnection(
	"xdbc:jdbc:postgresql://localhost/memo", 
	"postgres", 
	"password"
);
con.close();
```

#### 2.1.2. JNDI로 연결하는 방법(예를 들어 Apache Tocmat)

```xml
<Resource name="jdbc/memo" 
	auth="Container"
	type="javax.sql.DataSource" 
	driverClassName="kr.xdbc.driver.GenericDriver"
	url="xdbc:jdbc:postgresql://localhost/memo"
	username="postgres"
	password="password" />
```

### 2.2. 로깅

* 로깅에 사용된 API : java.util.logging.Logger
* SQL 실행에 성공한 경우 : Level.FINE
* SQL 실행에 실패한 경우 : Level.SEVERE
* 제한 : PreparedStatement와 Statement 만 로깅

#### 2.2.1. 전역설정 (시스템 전체에 반영시키는 방법)

$JAVA_HOME/jre/lib/logging.properties 파일을 약간 수정한다.

```properties
.level= FINE
java.util.logging.ConsoleHandler.level = FINE
```

Apache Ant의 java task 를 사용하는 경우 다음과 같은 내용을 추가한다.

```properties
java.level = WARNING
javax.level = WARNING
sun.level = WARNING
```

#### 2.2.2. 사용자 정의 로그 설정 파일(logging.properties)

```properties
handlers= java.util.logging.ConsoleHandler
.level= FINE

java.util.logging.ConsoleHandler.level = FINE
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter

java.level = WARNING
javax.level = WARNING
sun.level = WARNING

kr.xdbc=FINE
```

#### 2.2.3. java 명령어와 함께 사용

```console
java -Djava.util.logging.config.file=logging.properties YourClassName
```

#### 2.2.4. Apache Ant에서 사용

```xml
<java classname="YourClassName" fork="true">
	<jvmarg value="-Djava.util.logging.config.file=logging.properties" />
</java>
```

#### 2.2.5. Apache Tomcat의 경우

첫 번째 줄은 ALL 혹은 FINE 로 변경한다.

```properties
java.util.logging.ConsoleHandler.level = ALL
kr.xdbc.level = FINE
```

### 2.3. 2개 이상의 데이타베이스 복제본을 만드는 기능

다음과 같이 **|** 를 구분자로 해서, 여러 데이타베이스를 지정한다.
```java
xdbc:jdbc:postgresql://192.168.2.2/memo|jdbc:postgresql://192.168.2.3/memo2
```

#### 2.3.1. 기능의 특징
* 클러스터링 같은 성격이 아니라, 단순하게 실시간으로 데이타 사본을 생성하는 기능이다.
* executeUpdate와 같은 것들은 모든 데이타베이스에 실행한다.
* ResultSet을 가져오는 것은 첫 번째 연결만 사용한다.
* 사용자 이름, 패스워드는 동일해야 한다.
* 데이타베이스의 물리적 구조가 완벽하게 동일할 필요까지는 없고, 동일한 sql이 실행될 수 있으면 된다.

### 2.4. 현재 열려있는 데이타베이스 연결을 확인하는 방법(StackTrace 확인)

다음의 객체가 생성되면 StackTrace를 저장했다가, close 메소드가 호출되면 이를 삭제함으로서, 일정 시점에 현재 실행중인 StackTrace를 확인할 수 있고, 이를 통해 실수로 close 메소드 호출이 누락된 코드를 찾을 수 있다. 

* java.sql.Connection
* java.sql.CallableStatement
* java.sql.PreparedStatement
* java.sql.ResultSet
* java.sql.Statement

먼저 trace 모드를 활성화시켜야 한다.

```java
kr.xdbc.trace.ConnectionManager.getInstance().start();
```

trace 모드를 비활성화시키기 위해서는 다음과 같이 한다.

```java
kr.xdbc.trace.ConnectionManager.getInstance().end();
```

다음과 같이 저장되어 있는 StackTrace를 출력할 수 있다.

```java
kr.xdbc.trace.ConnectionManager.getInstance().trace(System.out);
```

trace 메소드는 java.io.Writer나 java.io.PrintStream 파라미터를 입력받는다.

파일에 쓰고 싶은 경우 다음과 같다.

```java
java.io.PrintStream ps = new java.io.PrintStream("/var/lib/tomcat9/logs/status.out");
kr.xdbc.trace.ConnectionManager.getInstance().trace(ps);
ps.close();
```

jsp에서는 다음과 같이 할 수도 있다.

```java
kr.xdbc.trace.ConnectionManager.getInstance().trace(out);
```

## 3. 주의사항 및 알려진 문제점

### 3.1. 현재 열려있는 데이타베이스 연결을 확인하는 기능에서 알려진 문제

* Connection Pool을 사용하는 경우 Connection 객체가 반납되지 않기 때문에 StackTrace 가 출력됨.
* 이건 개선 할 방법이 없다.

### 3.2. 2개 이상의 데이타베이스에 복제본을 만드는 기능에서 알려진 문제 혹은 주의사항

* ResultSet을 반환하는 구문에서 데이타베이스 트랜젝션이 발생하는 경우, 첫 번째 연결에서만 반영된다.
* 비상대책에는 sequence를 동기화하는 등 이에 대한 고려가 있어야 한다.
* sequence 이름을 정하는 특별한 규칙이 있는 경우, 예를 들어 Graha Manager에서와 같이 {TABLE_NAME}${COLUMN_NAME}과 같은 형식으로 테이블 이름과 컬럼 이름 사이에 $를 넣는 형식으로 sequence를 만드는 경우 PostgreSQL에서는 다음과 같은 sql 구문을 실행하면 된다.

```sql
DO $$
DECLARE
	r record;
	mx integer;
BEGIN
	FOR r IN
	(
		select
		relname,
		substr(relname, 0, strpos(relname, '$')) as table_name,
		substr(relname, strpos(relname, '$')+1) as column_name
		from pg_statio_all_sequences where relname like '%$%'
	)
	LOOP
		execute 'select max(' || r.column_name || ') + 1 as mx from ' || r.table_name || ''
		into mx 
		;
		execute 'alter sequence "' || r.relname || '" restart with ' || mx
		;
	END LOOP;
END $$;
```

### 3.3. fail-over, load balancing 같은 것 지원하지 않음.

* 이건 개선할 생각이 없다.
* load balancing은 ResultSet 객체를 가져오는 것과 관련이 있는데, 응용프로그램 개발에 새롭고 중요한 제약사항을 발생시키게 된다.
* fail-over의 경우 파일시스템에 트랜젝션 이력을 남겨야 하고, 더 중요한 것은 첫 번째 서버가 죽은 경우 두 번째 서버로 자동으로 넘기는 일은 load balancing 과 관련된 이슈를 포함한다.
* 두 번째 데이타베이스는 단지 첫 번째 데이타베이스의 파일시스템이 깨지는 경우와 같이 특별한 비상상황을 위한 것이다.
* 현실적으로 운영중인 데이타베이스 중 1대가 죽는다면 서비스 중단을 감수해야 하고, 비상대책을 잘 만들어서 서비스 중단 시간을 최소화해야 한다.

### 3.4. 다른 데이타베이스에서 사용하고자 하는 경우

* kr.xdbc.driver.GenericDriver의 connect 메소드가 호출되기 전에 사용하고자 하는 JDBC 드라이버를 로딩하면 된다.
* 위와 같은 일이 번거로운 경우 kr.xdbc.driver.GenericDriver 소스코드를 약간만 수정하면 되는데, 다음 코드의 아래쪽에 비슷한 방식으로 추가하면 된다.
```java
try { Class.forName("org.h2.Driver");} catch(ClassNotFoundException e) {}
```

