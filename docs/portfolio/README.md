# Pokédex App Portfolio Pack

This folder contains copy-ready materials and production guidance for presenting Pokédex App on Upwork and a personal portfolio website.

## Files

| File | Purpose |
| --- | --- |
| `upwork-portfolio-copy.md` | Upwork title, role, description, skills, and media captions |
| `case-study.md` | Full website case study, architecture, verification, and demo storyboard |
| `media-production-guide.md` | Required screenshots, recording guidance, and final asset specifications |
| `../screenshots/01-pokedex-home.png` | Clean Pokédex discovery screen |
| `../screenshots/02-pokedex-search.png` | Search result for Charizard |
| `../screenshots/03-pokemon-detail.png` | Charizard detail and base-stat screen |
| `../screenshots/04-pokemon-favorites.png` | Saved Pokémon collection screen |
| `assets/pokemon-app-cover-1000x750.png` | Primary Upwork portfolio cover |
| `assets/pokemon-app-flow-1000x750.png` | Discovery, search, and detail product flow |
| `assets/pokemon-app-engineering-1000x750.png` | Architecture and offline-favorites overview |
| `assets/pokemon-app-demo.mp4` | Product walkthrough screen recording |
| `tools/generate_composites.swift` | Reproducible generator for the three composite images |

The portfolio package now includes four source screenshots, three 1000×750 composite images, and a 2.5 MB product walkthrough video captured from the latest application build.

## Recommended Upwork order

1. `pokemon-app-cover-1000x750.png`
2. Overview text from `upwork-portfolio-copy.md`
3. `pokemon-app-demo.mp4`
4. `pokemon-app-flow-1000x750.png`
5. Discovery or detail caption from `upwork-portfolio-copy.md`
6. `pokemon-app-engineering-1000x750.png`
7. Engineering caption from `upwork-portfolio-copy.md`

If Upwork offers the video as a cover, use it only when the opening frame clearly shows the finished application. Otherwise, keep the dedicated cover image first.

## Recommended website order

1. Use `pokemon-app-website-hero-1600x900.png` as the project hero.
2. Embed `pokemon-app-demo.mp4` after the overview.
3. Present the Challenge, Solution, and Architecture sections from `case-study.md`.
4. Place the real Discovery, Detail, and Favorites screenshots beside their solution sections.
5. Finish with Verification, What I would improve next, and the source-code link.

## Accessibility text

- **Cover:** Pokédex App Android case study showing discovery, Pokémon detail, and favorites screens.
- **Product flow:** Three Pokédex App screens illustrating discovery, detail exploration, and saving a favorite.
- **Engineering:** Pokédex App screen beside an architecture diagram connecting Compose, ViewModel, use cases, PokeAPI, and Room.
- **Website hero:** Pokédex App portfolio hero showing three polished Android screens.

## Publishing checklist

- Use the exact field values from `upwork-portfolio-copy.md`.
- Use only screenshots from the current build.
- Confirm the source-code repository remains public.
- Preview every upload to ensure device frames and text are not cropped.
- Keep the demo MP4 below the platform size and duration limits shown during upload.
- Do not include contact information inside Upwork media.
- Keep the PokeAPI and trademark attribution in the website case study.
