#!/usr/bin/env bash

wget http://localhost:8080/polyevent-backend/EventServiceService?wsdl
mv EventServiceService\?wsdl EventService.wsdl
