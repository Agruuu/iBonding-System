<p align="center">
 <img src="https://img.shields.io/badge/Spring%20Boot-3.4.1-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/Vue-3.2-blue.svg" alt="Downloads">
</p>

## 🐯 Introduction

* Java backend: JDK 17 + Spring Boot 3.4.1
* The backend uses Spring Boot multi-module architecture, MySQL + MyBatis Plus, Redis + Redisson
* The database can use MySQL, Oracle, PostgreSQL, SQL Server, MariaDB, domestic DM, TiDB, etc.
* Message queues can use Event, Redis, RabbitMQ, Kafka, RocketMQ, etc.
* Permission authentication uses Spring Security & Token & Redis, supports multi-terminal, multi-user authentication system, and supports SSO single sign-on.
* Support loading dynamic permission menu, button-level permission control, and Redis cache to improve performance.
* Supports SaaS multi-tenancy, can customize the permissions of each tenant, and provide transparent multi-tenant underlying encapsulation.
* Highly efficient development, using the code generator to generate Java, Vue front-end and back-end code, SQL scripts, interface documents in one click, supporting single table, tree table, master-subtable.
* Real-time communication, implemented using Spring WebSocket, built-in Token identity verification, and support for WebSocket clusters.
* Integrate SMS channels such as Alibaba Cloud and Tencent Cloud, and cloud storage services such as MinIO, Alibaba Cloud, Tencent Cloud, and Qiniu Cloud.


## 🐼 Built-in functions

The system has built-in various business functions, which can be used to quickly improve your business system:

* Common modules: system functions, infrastructure
* Business systems (on demand): AI model or any else, I didn't add.

### System Features

|  | Function               | describe                                                                                                                   |
|----|------------------------|----------------------------------------------------------------------------------------------------------------------------|
| 🚀 | User Management        | The user is the system operator. This function mainly completes the system user configuration.                             |
| ⭐ | Online users           | Active user status monitoring in the current system, support for manual offline.                                           |
| 🚀 | Role Management        | Role menu permission allocation, set roles and divide data scope permissions by organization.                              |
| 🚀 | Menu Management        | Configure system menus, operation permissions, button permission identifiers, etc. Local cache provides performance.       |
| 🚀 | Department Management  | Configure system organization (company, department, group), tree structure display supports data permissions.              |
| 🚀 | Position Management    | Configure the system user's position.                                                                                      |
| 🚀 | Tenant Management      | Configure system tenants to support multi-tenancy in SaaS scenarios.                                                       |
| 🚀 | Tenant Packages        | Configure tenant packages and customize the menus, operations, and button permissions for each tenant.                     |
| 🚀 | Dictionary Management  | Maintain some relatively fixed data that is frequently used in the system.                                                 |
| 🚀 | SMS management         | SMS channels, SMS templates, SMS logs, and connection to mainstream SMS platforms such as Alibaba Cloud and Tencent Cloud. |
| 🚀 | Email Management       | Email account, email template, email sending log, support all email platforms.                                             |
| 🚀 | Internal Message       | Message notifications within the system, providing in-station message templates and in-station message messages.           |
| 🚀 | Operation log          | System normal operation log recording and query, integrated Swagger to generate log content.                               |
| 🚀 | Login Log              | System login log record query, including login exceptions.                                                                 |
| 🚀 | Error code management  | Management of all system error codes, error prompts can be modified online without restarting the service.                 |
| 🚀 | Notice Announcement    | System notification announcement information release and maintenance.                                                      |
| 🚀 | Sensitive words        | Configure system sensitive words and support tag grouping.                                                                 |
| 🚀 | Application Management | Manage SSO single sign-on applications and support multiple OAuth2 authorization methods.                                  |
| 🚀 | Regional Management    | Displays information about provinces, cities, districts, towns, etc., and supports IP-based cities.                        |

### Infrastructure

|  | Function                 | describe                                                                                                                   |
|--|--------------------------|----------------------------------------------------------------------------------------------------------------------------|
| 🚀 | Code Generation          | Front-end and back-end code generation (Java, Vue, SQL, unit testing), support CRUD download.                              |
| 🚀 | System Interface         | Automatically generate relevant RESTful API interface documents based on Swagger.                                          |
| 🚀 | Database documentation   | Automatically generate database documents based on Screw, support export to Word, HTML, MD formats.                        |
| 🚀 | Form Building            | Drag form elements to generate corresponding HTML code, support exporting JSON and Vue files.                              |
| 🚀 | Configuration Management | Dynamically configure common parameters for the system and support SpringBoot loading.                                     |
| ⭐️ | Scheduled tasks          | Online (add, modify, delete) task scheduling including execution result log.                                               |
| 🚀 | File Services(IO)        | Supports storing files in S3 (MinIO, Alibaba Cloud, Tencent Cloud, Qiniu Cloud), local, FTP, database, etc.                | 
| 🚀 | WebSocket                | Provides WebSocket access examples, supporting one-to-one and one-to-many sending methods.                                 | 
| 🚀 | API Log                  | Includes RESTful API access log and exception log, which is convenient for troubleshooting API-related issues.             |
| 🚀 | MySQL Monitor            | Monitor the current system database connection pool status and analyze SQL to find system performance bottlenecks.         |
| 🚀 | Redis Monitor            | Monitor the usage of Redis database and manage the used Redis Key.                                                         |
| 🚀 | MQ                       | Implement message queue based on Redis, Stream provides cluster consumption, Pub/Sub provides broadcast consumption.       |
| 🚀 | Java Monitor             | Monitoring Java applications based on Spring Boot Admin.                                                                   |
| 🚀 | Link Tracking            | Connect to SkyWalking component to realize link tracking.                                                                  |
| 🚀 | Log Center               | Connect SkyWalking components to realize log center.                                                                       |
| 🚀 | Service Guarantee        | Implement distributed lock, idempotence, and current limiting functions based on Redis to meet high concurrency scenarios. |
| 🚀 | Log Service              | Lightweight log center, view the logs of remote servers.                                                                   |
| 🚀 | Unit Testing             | Implement unit testing based on JUnit + Mockito to ensure functional correctness, code quality, etc..                      |

### AI Model

Supported AI models: DeepSeek, ChatGPT, LLAMA, Gemini, Stable Diffusion, Midjourney, Suno AI.

## 🐨 Technology Stack

### Modules

| 项目                       | 说明                                                      |
|---------------------------|-----------------------------------------------------------|
| `ibonding-dependencies`   | Maven dependency version management.                      |
| `ibonding-framework`      | Java framework extension.                                 |
| `ibonding-server`         | Management backend + user APP server.                     |
| `ibonding-module-system`  | Module of system functions.                               |
| `ibonding-module-infra`   | Module of Infrastructure.                                 |
| `ibonding-module-ai`      | Module of AI, Don't add it yet, maybe it's not necessary. |

### framework

| frame                                                                                       | explain                                     | version        |
|---------------------------------------------------------------------------------------------|---------------------------------------------|----------------|
| [Spring Boot](https://spring.io/projects/spring-boot)                                       | Application Development Framework           | 3.4.1          |
| [Spring MVC](https://github.com/spring-projects/spring-framework/tree/master/spring-webmvc) | MVC Framework                               | 6.1.10         |
| [Spring Security](https://github.com/spring-projects/spring-security)                       | Spring Security framework                   | 6.3.1          |
| [MySQL](https://www.mysql.com/cn/)                                                          | Database                                    | 5.7 / 8.0+     |
| [Druid](https://github.com/alibaba/druid)                                                   | JDBC connection pool, monitoring components | 1.2.23         |
| [MyBatis Plus](https://mp.baomidou.com/)                                                    | MyBatis Toolkit                             | 3.5.7          |
| [Dynamic Datasource](https://dynamic-datasource.com/)                                       | Dynamic Data Source                         | 4.3.1          |
| [Redis](https://redis.io/)                                                                  | key-value Database                          | 5.0 / 6.0 /7.0 |
| [Redisson](https://github.com/redisson/redisson)                                            | Redis                                       | 3.32.0         |
| [Hibernate Validator](https://github.com/hibernate/hibernate-validator)                     | Parameter verification component            | 8.0.1          |
| [Flowable](https://github.com/flowable/flowable-engine)                                     | Workflow Engine                             | 7.0.0          |
| [Quartz](https://github.com/quartz-scheduler)                                               | Task Scheduling Component                   | 2.3.2          |
| [Springdoc](https://springdoc.org/)                                                         | Swagger document                            | 2.7.0          |
| [SkyWalking](https://skywalking.apache.org/)                                                | Distributed Application Tracing System      | 9.0.0          |
| [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin)                       | Spring Boot Monitoring Platform             | 3.3.2          |
| [Jackson](https://github.com/FasterXML/jackson)                                             | JSON Toolkit                                | 2.17.1         |
| [MapStruct](https://mapstruct.org/)                                                         | Java Bean Conversion                        | 1.6.3          |
| [Lombok](https://projectlombok.org/)                                                        | Eliminate verbose Java code                 | 1.18.34        |
| [JUnit](https://junit.org/junit5/)                                                          | Java Unit Testing Framework                 | 5.10.1         |
| [Mockito](https://github.com/mockito/mockito)                                               | Java Mock Framework.                        | 5.2.0          |
