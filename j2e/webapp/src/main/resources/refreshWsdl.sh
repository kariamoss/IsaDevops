#!/usr/bin/env bash

wget http://localhost:8080/polyevent-backend/webservices/EventService?wsdl
mv EventService\?wsdl EventService.wsdl
