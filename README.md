# loyalty-axon-demo
loyalty service impl. with usage of event store, CSQR, event buses from Axon (switched to in-memory impl.)

notes:
- REST api(create account, create transaction, get history, get avail points, get pending points, evaluatePoints)
- both events bus and event store configured to as "in-memory"
- DB could be configured to used Axon Server, MongoDB, JPA as events storage by maven
- integration tests for some REST API operation
- app splited to CSQR (command vs query) query side is sourced by Axon server (when axon server impl. is provided, no in-memory)
