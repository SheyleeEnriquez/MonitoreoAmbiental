#!/usr/bin/env bash
set -euo pipefail

NODES=(crdb-node1 crdb-node2 crdb-node3)

SQL='
CREATE DATABASE IF NOT EXISTS "sensor_db";
CREATE DATABASE IF NOT EXISTS "notifications_db";
CREATE DATABASE IF NOT EXISTS "analyzer_db";
'

for node in "${NODES[@]}"; do
  docker exec -i "$node" ./cockroach sql --insecure -e "$SQL"
done
