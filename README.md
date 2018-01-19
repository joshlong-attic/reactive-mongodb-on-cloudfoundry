# Reactive Mongodb on CloudFoundry

* Create the service instance first. On PWS, this will work: `cf cs mlab sandbox mdbdb `
* You'll need to disable Cloud Foundry's `auto-reconfiguration` and point the application to the bound service instance directly. See `manifest.yml` for the correct environment-variable specification.

