## Дмитриев Артем, выпускная работа Финтех.Академии

## Описание:
- **account** - микросервис, предоставляющий работу с функционалом для банковских счетов. Реализованные эндпоинты:
  - Создание счета (**POST /api/account/create**). Тело запроса:
    ```yaml
    {
      "num": "8"
    }
    ```
  - Просмотр счета (**GET /api/account/{id}**)
  - Просмотр всех счетов (**GET /api/account/all**)
  - Пополнение счета (**POST /api/account/deposit**). Тело запроса:
    ```yaml
    {
      "num": 8,
      "amount": 100000
    }
    ```
  - Вывод со счета (**POST /api/account/withdraw**). Тело запроса:
    ```yaml
    {
      "num": 8,
      "amount": 100
    }
    ```

  Порт: 8081


- **customer_service** - микросервис, предоставляющий работу с авторизацией и аутентификацией пользователей. Реализованные эндпоинты:
    - Вход (**POST /api/auth/login**). Тело запроса:
      ```yaml
      {
        "username": "user4",
        "password": "user4"
      }
      ```
    - Регистрация (**POST /api/auth/register**). Тело запроса:
      ```yaml
      {
        "username": "user4",
        "password": "user4",
        "phoneNumber": "79009009090"
      }
      ```
    
  Порт: 8082


- **deposit** - микросервис, предоставляющий работу с банковскими вкладами и заявками на открытие банковских счетов. Реализованные эндпоинты:
    - Создание вклада (**POST /api/deposit/create**). Тело запроса:
      ```yaml
      {
        "accountNumber": 8,
        "amount": 10001,
        "refill": true,
        "withdraw": "false",
        "endDate": "2024-12-31T21:00:00.000",
        "percentPaymentDate": "2024-12-31T21:00:00.000",
        "capitalization": true
      }
      ```
    - Просмотр всех вкладов (**GET /api/deposit/all**)
    - Просмотр вклада (**GET /api/deposit/{id}**)
    - Закрытие вклада (**POST /api/deposit/close/{id}**)
    - Просмотр всех заявок (**GET /api/request/all**)
    - Просмотр заявки (**GET /api/request/{id}**)

  Порт: 8083


- **starter** - набор частоиспользуемых компонентов. В основном это классы для управления авторизацией и аутентификацией 
(генерация JWT токена, filterchain для авторизации пользователя), а так же сущности банковского счета и пользователя.


## Невыполненные условия:
- Отсутствие тестов
- Отсутствие подтверждения по номеру телефона при открытии/закрытии вклада
- Отсутствие использования миграций
- Отсутствие выплаты процентов по вкладу, снятия и пополнения со вклада
