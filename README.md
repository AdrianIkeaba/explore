# Explore. - Country Info App

## Overview
**Explore.** is a Kotlin-based Android application that fetches and displays detailed information about countries worldwide, including population, capital, and more. It is built using **Jetpack Compose**, **Kotlin Coroutines**, and follows **MVVM architecture**.

## Features
- View country details such as name, capital, population, and currency.
- Search for countries by name.
- Dark mode support.

## Project Setup

### Prerequisites
Ensure you have the following installed:
- Android Studio **Giraffe (2022.3.1) or newer**
- Kotlin **2.0+**
- JDK **11+**
- Internet connection (for API requests)

### Clone the Repository
```bash
git clone https://github.com/AdrianIkeaba/explore
cd explore
```

### API Key Configuration
This app fetches country data from an external API, requiring an **API key**. Follow these steps to get an API key:

1. Visit [Restful Countries API](https://restfulcountries.com/api-documentation/) for documentaton.
2. Navigate [Here](https://restfulcountries.com/request-access-token) to generate your API Key.
3. Add the API key to the 'CountriesApi.kt' file. Replace **[API KEY]** with your generated API Key.
   ```
   private const val AUTH_TOKEN = "Bearer [API KEY]"
   ```

### Running the App

#### Using Android Studio
1. Open Android Studio and **select the project**.
2. **Sync Gradle** to load dependencies.
3. Connect a **physical device** or start an **emulator**.
4. Click **Run** ▶️ to build and deploy the app.

## Contribution Guidelines

### Pull Requests
1. Fork the repository.
2. Create a new feature branch:
   ```bash
   git checkout -b feature/new-feature
   ```
3. Commit your changes and push to your fork.
   ```bash
   git commit -m "Add new feature"
   git push origin feature/new-feature
   ```
4. Open a **Pull Request** on GitHub.

## License
This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

## Contact
For questions, reach out to [adrianikeaba@gmail.com] or open an issue on GitHub!

