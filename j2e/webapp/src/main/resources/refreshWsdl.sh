#!/usr/bin/env bash


wget http://localhost:8080/j2e-interface-1.0-SNAPSHOT/webservices/EventCreatorService?wsdl
wget http://localhost:8080/j2e-interface-1.0-SNAPSHOT/webservices/EventCatalogService?wsdl
wget http://localhost:8080/j2e-interface-1.0-SNAPSHOT/webservices/CoordinatorService?wsdl

mv EventCreatorService\?wsdl EventCreatorService.wsdl
mv EventCatalogService\?wsdl EventCatalogService.wsdl
mv CoordinatorService\?wsdl CoordinatorService.wsdl