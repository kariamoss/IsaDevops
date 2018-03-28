#!/usr/bin/env bash

wget http://localhost:8080/polyevent-backend/webservices/EventService?wsdl
wget http://localhost:8080/polyevent-backend/webservices/EventCatalogService?wsdl

mv EventService\?wsdl EventService.wsdl
mv EventCatalogService\?wsdl EventCatalogService.wsdl
