# hubspot-java-client

A work-in-progress Java wrapper around the HubSpot API, including a mix of V1 and V3 endpoints.

## Impact Upgrade

[Impact Upgrade](https://www.impactupgrade.com) is a tech and operations consulting company that solely partners with nonprofits and for-good businesses. We make the complex simple, focusing on back-office operations and making big ideas a reality. Don't fight a multiple-front battle! You know your mission. We know tech.

## Usage

Add the following Maven dependency:

```xml
<dependency>
    <groupId>com.impactupgrade.integration</groupId>
    <artifactId>hubspot-java-client</artifactId>
    <version>1.0.0.Final</version>
</dependency>
```

Code example:

TODO

## How to Deploy a Snapshot

1. Add the following to ~/.m2/settings.xml
```xml
<server>
  <id>ossrh</id>
  <username>USERNAME</username>
  <password>PASSWORD</password>
</server>
```
2. mvn clean deploy

## How to Deploy a Release

1. Add the following to ~/.m2/settings.xml
```xml
<server>
  <id>ossrh</id>
  <username>USERNAME</username>
  <password>PASSWORD</password>
</server>
```
2. mvn versions:set -DnewVersion=1.2.3.Final
3. git add .
4. git commit -m "1.2.3.Final release"
5. git tag 1.2.3.Final
6. mvn clean deploy -P release
7. mvn versions:set -DnewVersion=1.2.4-SNAPSHOT
8. git add .
9. git commit -m "1.2.4-SNAPSHOT"
10. git push origin master 1.2.3.Final

## License

Licensed under the Apache License, Version 2.0. See LICENSE-2.0.txt for more information.
