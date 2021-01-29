# hubspot-java-client

Working with the Salesforce API can be a **pain**. Although we're old school and can appreciate some of the benefits of strongly-typed SOAP contracts, it's easy to wind up with a solution that's pretty brittle and not resilient.

salesforce-java-tools is the backbone of all our Salesforce integrations. In particular, <a href="https://github.com/impactupgrade/salesforce-java-tools/blob/master/src/main/java/com/impactupgrade/integration/sfdc/SFDCPartnerAPIClient.java">SFDCPartnerAPIClient</a> supports all the bells-and-whistles and hardening we required: mapping between Partner API and Enterprise API objects, batched actions, retries, session/connection caching, and DRY helper methods.

## Impact Upgrade

Nonprofits often struggle with 1) constant distractions, 2) a tangled mess of data and tools that hold them back, and 3) really big ideas on the backburner. [Impact Upgrade](https://www.impactupgrade.com) is a software and consulting company, solely focused on closing these gaps. We upgrade your impact and get you back to your mission!

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
