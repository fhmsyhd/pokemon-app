# Portfolio Media Production Guide

Use real screenshots and a real recording from the latest application build. Do not recreate or modify the UI in a way that suggests features the app does not contain.

## Required captures

Capture these four screens using the same emulator or device:

1. **Discovery:** Pokédex grid with at least six fully loaded cards.
2. **Search:** A useful result such as Pikachu; avoid an open keyboard in the final screenshot.
3. **Detail:** Pokémon artwork, type chips, measurements, and several base stats visible.
4. **Favorites:** Two to four saved Pokémon so the grid looks intentional.

Optional fifth capture:

- **Empty or error state:** Use only in the case study, not as the main cover.

## Capture settings

- Use portrait orientation.
- Prefer a 1080 × 2400 emulator or another modern Android phone ratio.
- Use the same system theme, font scale, and navigation mode in every capture.
- Wait until artwork and data have finished loading.
- Hide notifications, debug overlays, touch indicators, and personal information.
- Keep the status bar only if it is consistent across all screenshots.

Save the original files in `docs/screenshots/` using these names:

```text
01-pokedex-home.png
02-pokedex-search.png
03-pokemon-detail.png
04-pokemon-favorites.png
```

## Final portfolio assets to produce

After the screenshots are available, create:

| Asset | Size | Purpose |
| --- | ---: | --- |
| `pokemon-app-cover-1000x750.png` | 1000 × 750 | Primary Upwork cover |
| `pokemon-app-flow-1000x750.png` | 1000 × 750 | Discover, Explore, Save journey |
| `pokemon-app-engineering-1000x750.png` | 1000 × 750 | Architecture and quality overview |
| `pokemon-app-website-hero-1600x900.png` | 1600 × 900 | Personal website hero |
| `pokemon-app-demo.mp4` | Under 60 seconds | Upwork and website demo |

## Cover direction

- Use the Discovery, Detail, and Favorites screens.
- Make the Detail screen the largest device frame.
- Use a restrained red, navy, and off-white background matching the app theme.
- Suggested headline: **Modern Android Pokédex**.
- Suggested subline: **Jetpack Compose · Offline Favorites · Tested with CI**.
- Do not include email, phone number, LinkedIn, or other contact information.

## Video recording checklist

- Target duration: 25–35 seconds.
- Record at a stable frame rate with no visible taps unless helpful.
- Follow the storyboard in `case-study.md`.
- Start and end on a stable, fully rendered frame.
- Remove long waits and failed interactions.
- Export as MP4 and verify the file plays before upload.
