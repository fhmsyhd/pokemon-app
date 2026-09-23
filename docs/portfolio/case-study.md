# Pokédex App — Portfolio Case Study

## Upwork-ready fields

### Project title

Pokédex App — Modern Android Discovery & Favorites

### Role

Android Developer — Jetpack Compose, Architecture, Testing, and CI

### Project description

Designed and developed a modern Pokédex app using Kotlin, Jetpack Compose, MVVM, Hilt, Retrofit, Room, Coroutines, and Flow. I redesigned the discovery and detail experiences, added pagination, name/number search, responsive loading, empty, error, and retry states, and implemented reactive offline favorites. I also added meaningful ViewModel, mapper, and use-case tests plus GitHub Actions for test, lint, and build verification. The result is a polished, maintainable Android portfolio project.

### Skills and deliverables

1. Android App Development
2. Kotlin
3. Jetpack Compose
4. MVVM
5. API Integration

## Website case study

### Overview

Pokédex App is a personal Android project for discovering Pokémon, reviewing their attributes and base stats, and maintaining a local favorites collection. I evolved the project into a portfolio-ready application with a cohesive Material 3 visual system, resilient UI states, clearer architecture boundaries, meaningful automated tests, and continuous integration.

### The challenge

The original application presented the API data but lacked a strong product hierarchy and credible failure handling. Loading obscured existing content, search and pagination state could overlap, the detail page was visually flat, and an unrelated local authentication demo distracted from the core discovery experience.

The project needed to become a focused Pokémon discovery experience while demonstrating production-minded Android practices without pretending to be a commercial product.

### My role

As the sole developer of this personal project, I worked across:

- Jetpack Compose UI and Material 3 theming.
- List, search, pagination, detail, and favorites UX.
- ViewModels and reactive UI-state handling.
- Retrofit integration with PokeAPI.
- Room-backed favorite persistence.
- Domain use cases and repository boundaries.
- Unit testing and GitHub Actions configuration.

### The solution

#### 1. Focused discovery experience

The home screen uses an adaptive Pokémon grid, name and Pokédex-number search, paginated loading, and artwork-driven card colors. Clear visual hierarchy keeps the search and content easy to scan across different screen widths.

#### 2. Explicit loading, empty, and error states

Skeleton cards establish the expected layout during initial loading. Empty search results explain what happened and provide a clear reset action. Network failures use focused retry states, while pagination failures preserve the Pokémon already on screen.

#### 3. Information-rich Pokémon details

The detail screen combines hero artwork, Pokémon number, type chips, weight and height, animated base-stat progress, and a favorite action. The artwork-derived background creates continuity between the selected list card and its detail screen.

#### 4. Reactive offline favorites

Favorites are stored with Room and exposed through Kotlin Flow. Changes made from the detail screen are reflected in the dedicated favorites tab without manual refresh or duplicated state.

#### 5. Maintainable state and verification

ViewModels depend on a focused domain use case instead of concrete data sources. Search requests cancel stale work, pagination prevents overlapping requests, and favorite operations flow through a single repository contract. Automated tests cover ViewModel behavior, mapping, and use-case delegation.

### Architecture

```text
Jetpack Compose UI
        ↓
     ViewModel
        ↓
    Use Case
        ↓
   Repository ─────→ PokeAPI
        └──────────→ Room Favorites
```

State is delivered through Kotlin Flow and StateFlow. Retrofit provides remote Pokémon data, while Room is responsible only for locally saved favorites.

### Verification

- Thirteen unit tests cover list, detail, favorites, mapping, and use-case behavior.
- The local CI-equivalent command completes unit tests, Android lint, and debug APK assembly successfully.
- A GitHub Actions workflow is configured for pushes and pull requests to `main`.
- The workflow stores test reports, lint reports, and a debug APK as artifacts.

### What I would improve next

- Add screenshot tests for critical Compose states.
- Add UI instrumentation tests for navigation and favorite persistence.
- Cache remote Pokémon pages for a richer offline discovery experience.
- Add type-based filters and favorite sorting.
- Expand TalkBack, dynamic type, and contrast validation.

## Short website card

### Title

Pokédex App

### Subtitle

A modern Jetpack Compose Pokédex with paginated discovery, reactive offline favorites, resilient UI states, and automated verification.

### Technology line

Kotlin · Jetpack Compose · MVVM · Hilt · Retrofit · Room · Coroutines · Flow

### Primary links

- Source code: https://github.com/fhmsyhd/pokemon-app
- Case study: use the full website case study above

## Demo storyboard

Recommended duration: 25–35 seconds.

1. **Discover (0–7 seconds):** Open the Pokédex and scroll through the adaptive card grid.
2. **Search (7–12 seconds):** Search for Pikachu by name or Pokédex number, then clear the query.
3. **Explore (12–22 seconds):** Open Pikachu and reveal its types, measurements, and animated base stats.
4. **Save (22–29 seconds):** Add Pikachu to favorites and open the Favorites tab.
5. **Closing frame (29–35 seconds):** Show “Kotlin · Jetpack Compose · Room · Tested with CI”.

For Upwork, use an MP4 under 60 seconds, avoid contact information inside the video, and start with a fully loaded screen rather than a splash or loading frame.

## Attribution

Pokémon data is provided by [PokeAPI](https://pokeapi.co/). Pokémon and Pokémon character names are trademarks of their respective owners. This is a personal, non-commercial engineering portfolio project and is not affiliated with or endorsed by the Pokémon rights holders.
