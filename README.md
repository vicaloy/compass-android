# compass-android

The solution is structured based on Clean Architecture principles, mainly focus on separation of concerns, maintainability and testability.

- Domain: Contains the business logic and the use cases of the application.
- Presentation: Contains the UI layer of the application, built with Compose and MVVM.
  Android ViewModels are utilized to manage UI-related data in a lifecycle-conscious way.
- Infrastructure: Contains room implementation, network, etc, (referring to android specific implementations).

The application implement the Repository pattern, dependency injections.

Pros of Clear Architecture:
- Separation of concerns: separates ui logic, business rules, and data operations.
- Testability: each layer can be tested independently.
- Scalability: easy to scale and maintain, making changes in one layer does not affect the other layers.
- Maintainability: reduces coupling, making it easier to maintain and extend the application.
- Reusability: the domain layer encapsulates the core business logic and use cases of the application. These are  not tied to any specific UI framework, database, or external service.

Cons of Clear Architecture:
- Complexity: the architecture can be complex for small applications and has boilerplate code.
- Over-engineering: can be overkill for small applications.