# Developing broadcastmail-common

## Prerequisites

- Java 25
- Maven 3.9+
- GitHub Packages token with `read:packages` and `write:packages`

## Building locally

```bash
./mvnw clean install
```

Installs the library to your local Maven repository. Other services pick it up via their local `.m2` cache.

## Publishing to GitHub Packages

```bash
./mvnw deploy
```

Requires `GITHUB_TOKEN` with `write:packages` set in environment or `~/.m2/settings.xml`.

## Making changes

Changes to entities or repositories affect all three services — api, worker, webhooks. Always:

1. Update common, bump version in `pom.xml`
2. Publish to GitHub Packages
3. Update version in all dependent services
4. Run CI across all repos to catch breakage early

## Database schema

See [DATABASE.md](https://github.com/broadcastmail/.github/blob/main/DATABASE.md).
