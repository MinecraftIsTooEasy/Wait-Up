# Wait Up

`Wait Up` is a MITE/FML mod that delays dimension access and some related progression actions until configurable Overworld days.

## Main Features

- Independently lock entry to the Underworld, Nether, and End by Overworld day.
- If a locked destination portal is built early, it behaves like a normal runegate and returns the player to the Overworld spawn point.
- Show players a login summary of the current world settings.
- Limit Nether Quartz sacrifice before a configured day.
- Configure sacrifice XP for Nether Quartz, Emerald, Diamond, and Lapis.
- Adjust mithril and diamond ore frequency in the upper central region of the Underworld.
- Optional compatibility with `ManyLib` for in-game config editing.
- Optional compatibility with `ITF Reborn` for Lich Castle generation limits and Quartz-related experience restrictions.

## Installation

Dedicated server installation is enough for the gameplay rules to work.

Client installation is not required for multiplayer servers.

Current packaging follows the same server-only direction used by `ProgressLimiter`:

- `wait_up` is declared with `environment: "server"`
- gameplay prompts are sent as plain server chat text
- ManyLib compatibility remains on the config-handling side, but this mod no longer depends on a client-side `Wait Up` install to join the server

If you want a future build focused on single-player or in-game client config UI, that should be treated as a separate packaging target.

## Required and Optional Dependencies

Required:

- `FishModLoader >= 3.4.2`
- `Rusted Iron Core >= 1.5.0`

Optional:

- `ManyLib`
- `ITF Reborn`

## Config File

The mod saves its config as `wait_up.json`.

Main keys:

- `underworldMinDay`
- `netherMinDay`
- `endMinDay`
- `netherQuartzSacrificeMinDay`
- `netherQuartzSacrificeXp`
- `emeraldSacrificeXp`
- `diamondSacrificeXp`
- `lapisSacrificeXp`
- `underworldHighOreMaxAbsXZ`
- `underworldHighMithrilMultiplier`
- `underworldHighDiamondMultiplier`
- `itfrbCompatEnabled`
- `itfrbLichCastleMinDistance`

Rule of thumb:

- Set a day or distance value to `0` to disable that restriction.
- Ore multipliers use decimal values from `0.0` to `1.0`.
- The Underworld ore adjustment only applies when `y > 120` and both `|x|` and `|z|` are within the configured absolute-value range.

## Notes About Language Files

This project intentionally keeps language entries in both:

- `assets/minecraft/lang`
- `assets/wait_up/lang`

Reason:

- gameplay rule prompts are now sent as server-side plain text, so clients can read them even without the mod installed
- these duplicated entries are harmless for the current server-oriented package and make future client-oriented builds easier to maintain

Keep both copies in sync when updating text.
