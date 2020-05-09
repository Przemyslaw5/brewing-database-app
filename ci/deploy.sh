#!/bin/bash
docker-compose -f projects/przemek/brewing_database_app/docker-compose.yml pull
docker-compose -f projects/przemek/brewing_database_app/docker-compose.yml up -d