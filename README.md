# 🦸‍♂️ Emoji List Android App

This Android app fetches a list of emojis from a REST API, displays them using Jetpack Compose, and stores them in a Room Database for offline access. The app uses Retrofit 2 for network requests, Hilt for dependency injection, Room for local storage, and MVVM architecture for state management.

- 🔗 URL: https://emojihub.yurace.pro/api/all

## 🚀 Features

- Emojis List from API:
Fetches emojis from a public REST API using Retrofit 2 and displays them in a list using Jetpack Compose.

- Room Database Integration:
Emojis are stored locally in a Room database for offline access.

- MVVM Architecture:
Follows the Model-View-ViewModel (MVVM) pattern to manage UI-related data in a lifecycle-aware way.

- Hilt Dependency Injection:
Uses Hilt for dependency injection, ensuring a clean and maintainable codebase.

## 🛠️ Technologies Used

- Kotlin: Primary language for Android development.
- Jetpack Compose: For building modern, declarative UIs.
- Retrofit 2: For API calls to fetch emojis.
- Room: For local storage and caching of emojis.
- Hilt: For dependency injection to manage class dependencies.
- ViewModels: To manage UI-related data in a lifecycle-conscious manner. Model-View-ViewModel architecture for separation of concerns.

## 📂 Project Structure

The solution is structured based on Clean Architecture principles, mainly focus on separation of concerns, maintainability and testability.

- Domain: Contains the business logic and the use cases of the application.
- Presentation: Contains the UI layer of the application, built with Compose and MVVM.
  Android ViewModels are utilized to manage UI-related data in a lifecycle-conscious way.
- Infrastructure: Contains room implementation, network, etc, (referring to android specific implementations).

The application implement the Repository pattern, dependency injections.

### 🔍 Pros of Clean Architecture
- Separation of concerns: separates ui logic, business rules, and data operations.
- Testability: each layer can be tested independently.
- Scalability: easy to scale and maintain, making changes in one layer does not affect the other layers.
- Maintainability: reduces coupling, making it easier to maintain and extend the application.
- Reusability: the domain layer encapsulates the core business logic and use cases of the application. These are  not tied to any specific UI framework, database, or external service.

### ⚠️ Cons of Clean Architecture
- Complexity: the architecture can be complex for small applications and has boilerplate code.
- Over-engineering: can be overkill for small applications.
