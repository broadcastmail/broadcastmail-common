# broadcastmail-common

Shared domain library for BroadcastMail. Contains JPA entities, repositories, and domain models used across all three services.

## Contents

- JPA entities — `Account`, `Campaign`, `CampaignRecipient`, `Connection`, `OutboxEntry`, and more
- Spring Data repositories
- Domain enums — `CampaignStatus`, `Plan`, `PlanFeature`
- Plan enforcement — `PlanStrategy`, `FreePlanStrategy`, `ProPlanStrategy`
- Shared exceptions — `PlanLimitExceededException`

## Usage

Published to GitHub Packages. Add to `pom.xml`:

```xml
<dependency>
    <groupId>com.broadcastmail</groupId>
    <artifactId>broadcastmail-common</artifactId>
    <version>1.1-SNAPSHOT</version>
</dependency>
```

See [DEVELOPERS.md](DEVELOPERS.md) for local development setup.

## Contributing

See [CONTRIBUTING.md](https://github.com/broadcastmail/.github/blob/main/CONTRIBUTING.md).

## License

MIT
