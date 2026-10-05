# Bomb Tracker (Fabric mod)

A client-side Fabric mod for Minecraft 1.21.11 that detects **bomb announcements** on [Wynncraft](https://wynncraft.com) and reports them to a backend API.

On Wynncraft, players can throw "bombs" that give a whole server a temporary bonus (double XP, extra loot, and so on). Players with the Champion rank see announcements for bombs thrown on every server. This mod reads those announcements so that everyone else can find out where the active bombs are.

## The Bomb Tracker system

```
bombtracker (mod)  ──POST /bombs──▶  bombtracker-backend  ◀──GET /bombs/active──  bombtracker-bot
 reads game chat                      stores bombs                                  /bombs in Discord
```

| Repo | Role | Stack |
|---|---|---|
| **bombtracker** (this repo) | Detects bombs in game chat | Java, Fabric |
| [bombtracker-backend](https://github.com/amengdev/bombtracker-backend) | Stores bombs, serves active ones | Java, Spring Boot, PostgreSQL |
| [bombtracker-bot](https://github.com/amengdev/bombtracker-bot) | Shows active bombs in Discord | Python, discord.py |

## Features

- Listens to system chat messages through the Fabric API (`ClientReceiveMessageEvents.GAME`)
- Parses bomb announcements into player, bomb type, and server
- Reports each bomb to the backend over HTTP

## Setup

Requirements: Minecraft 1.21.11, Fabric Loader, Fabric API. Building requires JDK 25.

```bash
./gradlew build
```

Copy the jar from `build/libs/` (not the `-sources` jar) into your `mods` folder along with Fabric API.

On first launch the mod creates `config/bombtracker.properties`. Set the URL of a running [bombtracker-backend](https://github.com/amengdev/bombtracker-backend):

```properties
backend_url=http://localhost:8080
```

Leave it blank to disable reporting.

## Running tests

```bash
./gradlew test
```

## Disclaimer

This is a fan project and is not affiliated with Wynncraft.
