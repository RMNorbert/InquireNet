#!/bin/bash
DATABASE_URL="jdbc:postgresql://localhost:5432/InquireNet"
DATABASE_USER="postgres"
DATABASE_PASSWORD="postgres"
LOCATIONS="filesystem:./src/main/resources/db/"
FLYWAY_CMD="flyway -url=$DATABASE_URL -user=$DATABASE_USER -password=$DATABASE_PASSWORD -locations=$LOCATIONS migrate"

$FLYWAY_CMD
