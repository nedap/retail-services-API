Go
 - JSON serializatie van tijd
 - JSON serializatie van lege objecten gaat niet goed. Dit omdat de code welke gegenereerd is met 2.2.2-SNAPSHOT
    gebruik maakt van object references ipv pointers --> er bestaat geen null waarde.
    Zie: https://github.com/swagger-api/swagger-codegen/pull/4504/files
    2.2.2 --> niet gefixt.
    master (2.3.0) --> ook niet
