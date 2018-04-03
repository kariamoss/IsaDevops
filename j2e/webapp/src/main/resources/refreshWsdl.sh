#!/usr/bin/env bash


wget http://localhost:8080/polyevent-backend/webservices/EventCreatorService?wsdl
wget http://localhost:8080/polyevent-backend/webservices/EventCatalogService?wsdl
wget http://localhost:8080/polyevent-backend/webservices/CoordinatorService?wsdl

mv EventCreatorService\?wsdl EventCreatorService.wsdl
mv EventCatalogService\?wsdl EventCatalogService.wsdl
mv CoordinatorService\?wsdl CoordinatorService.wsdl