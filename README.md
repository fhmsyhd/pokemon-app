# Pokemon Compose App

[![Android CI](https://github.com/fhmsyhd/pokemon-app/actions/workflows/android-ci.yml/badge.svg)](https://github.com/fhmsyhd/pokemon-app/actions/workflows/android-ci.yml)

A modern Android application to explore Pokémon data, built entirely with **Jetpack Compose**, **MVVM architecture**, and **Hilt dependency injection**.

This app demonstrates:
- **Clean architecture principles**
- Local favorites persisted with Room Database
- Bottom navigation (Pokémon & Favorites)
- Animated loaders using Lottie
- Lazy grid Pokémon list with pagination
- Detail screen with favorite controls

---

## Features

- Home screen with bottom navigation
- Pokémon list and locally persisted favorites
- Search and paginate Pokémon
- Detail view with dominant color extraction
- Add or remove favorites from the detail screen
- Dedicated empty and loading states
- Modern Compose UI components
- Hilt for DI
- Room and Flow for reactive local data

---

## Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Navigation Compose**
- **Hilt**
- **Room**
- **Coroutines / Flow**
- **Lottie Animations**
- **Coil Image Loading**
- **Material3**

---

## Testing and CI

The test suite covers:

- Pokémon list loading, retry, search, and pagination behavior
- Pokémon detail success and error states
- Adding and removing local favorites
- API, domain, and Room entity mapping
- Use case delegation

GitHub Actions runs unit tests, Android lint, and a debug build for every push and pull request to `main`. Test reports, lint reports, and the debug APK are uploaded as workflow artifacts.

```bash
./gradlew testDebugUnitTest lintDebug assembleDebug
```

---

## Acknowledgements

- [PokeAPI](https://pokeapi.co/) - Pokémon data provider
- [Lottie](https://airbnb.io/lottie/#/) - Animations
- [Coil](https://coil-kt.github.io/coil/compose/) - Image loading

---
