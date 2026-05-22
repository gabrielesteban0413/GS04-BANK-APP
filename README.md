# GS04-BANK-APP - Aplicación Bancaria con Android + Spring Boot + Oracle

## Estructura del proyecto
- `android/`: Código fuente de la aplicación Android (ya clonado)
- `backend/`: Backend en Spring Boot con Oracle
- `database/`: Scripts SQL para Oracle
- `docs/evidencias/`: Capturas y video de la demostración

## Requisitos previos
- Java 17
- Maven
- Oracle Database XE 21c (instalado y corriendo)
- SQL Developer (opcional)

## Instalación y ejecución

### 1. Base de datos Oracle
- Ejecuta el script `database/oracle_schema.sql` en tu instancia Oracle (con usuario BANKAPP)
- Asegúrate de tener el usuario BANKAPP con los permisos adecuados

### 2. Backend
- Abre una terminal en la carpeta `backend`
- Ejecuta `mvn clean install`
- Ejecuta `mvn spring-boot:run` o usa el script `run_backend.bat`
- El backend quedará disponible en `http://localhost:8080`

### 3. Aplicación Android
- Abre el proyecto en Android Studio (carpeta `android`)
- Configura la URL base en los archivos de red para apuntar a `http://10.0.2.2:8080` (emulador) o a tu IP local (dispositivo físico)
- Compila y ejecuta

## Credenciales de prueba (después de inicializar datos con DataInitializer)
- Admin: admin@bank.com / 123456
- User: user@bank.com / 123456

## Documentación adicional
Ver el código fuente y los comentarios en cada módulo.







GS04-BANK-APP_FULL/
│
├── backend/                                 # Backend en Java Spring Boot
│   ├── pom.xml                              # Dependencias Maven
│   ├── mvnw, mvnw.cmd, .mvn/                # Maven wrapper (opcional)
│   └── src/
│       └── main/
│           ├── java/com/bankapp/
│           │   ├── BankBackendApplication.java
│           │   ├── config/
│           │   │   ├── PasswordEncoderConfig.java
│           │   │   ├── RoleInterceptor.java
│           │   │   └── WebConfig.java
│           │   ├── controller/
│           │   │   ├── AuthController.java
│           │   │   ├── TransactionController.java
│           │   │   └── AdminController.java
│           │   ├── dto/
│           │   │   ├── LoginRequest.java
│           │   │   ├── LoginResponse.java
│           │   │   ├── TransferRequest.java
│           │   │   ├── UserCreateRequest.java
│           │   │   ├── UserUpdateRequest.java
│           │   │   └── UserResponse.java
│           │   ├── entity/
│           │   │   ├── User.java
│           │   │   └── Transaction.java
│           │   ├── repository/
│           │   │   ├── UserRepository.java
│           │   │   └── TransactionRepository.java
│           │   ├── service/
│           │   │   ├── AuthService.java
│           │   │   ├── TransactionService.java
│           │   │   ├── AdminService.java
│           │   │   └── DataInitializer.java
│           │   └── exception/
│           │       └── GlobalExceptionHandler.java
│           └── resources/
│               ├── application.properties
│               └── application-dev.properties (opcional)
│
├── android/                                 # App Android (Kotlin)
│   ├── build.gradle.kts (project level)
│   ├── settings.gradle.kts
│   ├── app/
│   │   ├── build.gradle.kts (module level)
│   │   ├── proguard-rules.pro
│   │   └── src/
│   │       ├── main/
│   │       │   ├── java/com/gs04/bankapp/
│   │       │   │   ├── BankAppApplication.kt
│   │       │   │   ├── di/                       # Dagger Hilt modules
│   │       │   │   │   ├── NetworkModule.kt
│   │       │   │   │   ├── DatabaseModule.kt
│   │       │   │   │   └── RepositoryModule.kt
│   │       │   │   ├── data/
│   │       │   │   │   ├── local/                # Room entities, DAOs
│   │       │   │   │   │   ├── entity/
│   │       │   │   │   │   ├── dao/
│   │       │   │   │   │   └── BankDatabase.kt
│   │       │   │   │   ├── remote/               # Retrofit services, DTOs
│   │       │   │   │   │   ├── dto/
│   │       │   │   │   │   ├── ApiService.kt
│   │       │   │   │   │   └── NetworkClient.kt
│   │       │   │   │   └── repository/
│   │       │   │   │       ├── AuthRepositoryImpl.kt
│   │       │   │   │       ├── TransactionRepositoryImpl.kt
│   │       │   │   │       └── AdminRepositoryImpl.kt
│   │       │   │   ├── domain/                   # Use cases, models
│   │       │   │   │   ├── model/
│   │       │   │   │   ├── repository/           # interfaces
│   │       │   │   │   └── usecase/
│   │       │   │   │       ├── LoginUseCase.kt
│   │       │   │   │       ├── TransferUseCase.kt
│   │       │   │   │       └── GetUsersUseCase.kt
│   │       │   │   ├── presentation/
│   │       │   │   │   ├── common/               # Base classes, utils
│   │       │   │   │   ├── theme/                # Styles, colors
│   │       │   │   │   └── ui/
│   │       │   │   │       ├── auth/
│   │       │   │   │       │   ├── LoginActivity.kt
│   │       │   │   │       │   └── LoginViewModel.kt
│   │       │   │   │       ├── dashboard/
│   │       │   │   │       │   ├── DashboardActivity.kt
│   │       │   │   │       │   └── DashboardViewModel.kt
│   │       │   │   │       ├── transfer/
│   │       │   │   │       │   ├── TransferActivity.kt
│   │       │   │   │       │   └── TransferViewModel.kt
│   │       │   │   │       ├── admin/
│   │       │   │   │       │   ├── AdminUsersActivity.kt
│   │       │   │   │       │   ├── AdminUsersViewModel.kt
│   │       │   │   │       │   └── UserAdapter.kt
│   │       │   │   │       └── profile/
│   │       │   │   │           ├── ProfileActivity.kt
│   │       │   │   │           └── ProfileViewModel.kt
│   │       │   │   └── utils/
│   │       │   │       ├── UserManager.kt
│   │       │   │       ├── SessionManager.kt
│   │       │   │       └── ValidationUtils.kt
│   │       │   └── res/                           # layouts, drawables, values
│   │       │       ├── layout/
│   │       │       ├── drawable/
│   │       │       ├── values/
│   │       │       └── ...
│   │       └── test/ y androidTest/                # Tests unitarios e instrumentados
│   └── gradle/ (wrapper)
│
├── database/                                     # Scripts de Oracle
│   ├── 01_create_tables.sql
│   ├── 02_insert_demo_data.sql
│   └── 03_triggers_indexes.sql (opcional)
│
└── evidencias/                                   # Capturas y video
    ├── capturas/
    │   ├── db_oracle_tables.png
    │   ├── backend_spring_logs.png
    │   ├── postman_login.png
    │   ├── postman_transfer.png
    │   ├── postman_admin_crud.png
    │   ├── android_login.png
    │   ├── android_dashboard_user.png
    │   ├── android_dashboard_admin.png
    │   ├── android_transfer_form.png
    │   ├── android_admin_user_list.png
    │   ├── android_admin_create_user.png
    │   ├── android_admin_edit_user.png
    │   └── android_admin_delete_user.png
    └── video_demo.mp4
