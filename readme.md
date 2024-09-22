# Spring Security with Spring Boot

### 概述

練習使用Spring Security框架的簡易project。

- 實作保護電影網站 (電影相關API簡化)
  - **全會員制**電影網站
  - 會員分：一般會員、VIP(付費)會員
  - 後台人員分：admin、平台影片管理者

- 安全防護實作內容：
  - Spring Security實作會員功能
    - 認證
    - 授權
  - 安全性保護
    - CORS
    - CSRF
  - Filter紀錄使用者登入時間

### 使用技術

- Java 17, Gradle 8.10.1
- Spring Boot 3.3.3
- Spring Security
- MySQL
- Hibernate
- Spring Data JPA
- Lombok
- JUnit(僅用於基本unit test，使用 @Test 標註), H2 Database