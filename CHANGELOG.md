# Changelog

All notable changes to `Wait Up` are documented in this file.

## [1.0.0] - 2026-07-14

Initial public release.

### Added

- Independent Overworld day locks for entering the Underworld, Nether, and End.
- Early portal fallback behavior that sends players back to the Overworld spawn point instead of allowing locked dimension travel.
- Login chat summary showing the current world settings.
- Nether Quartz sacrifice day lock based on Overworld time.
- Configurable sacrifice XP values for Nether Quartz, Emerald, Diamond, and Lapis.
- Upper Underworld ore control for mithril and diamond generation in a configurable central high-altitude region.
- Optional `ManyLib` compatibility for in-game config editing.
- Optional `ITF Reborn` compatibility toggle.
- Optional `ITF Reborn` Lich Castle central protected region by absolute `x` and `z` limits.
- `ITF Reborn` Quartz restrictions for Enchant Reserver input and Absorb-related experience conversion before the configured unlock day.
- English and Chinese localization files for chat messages and ManyLib config labels.
- Project documentation in `README.md`.

### Notes

- Gameplay rules now target dedicated servers without requiring clients to install `Wait Up`.
- The mod metadata now declares `environment: "server"` in the same spirit as `ProgressLimiter`.
- ManyLib compatibility remains for config handling, but client-side in-game config UI is no longer part of this server-oriented package.
