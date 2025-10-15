#!/usr/bin/env bash

# wait-for-it.sh: Wait for a service to become available
# Usage: ./wait-for-it.sh host:port [-t timeout]

set -e

hostport=$1
timeout=${2:-15}

host=$(echo "$hostport" | cut -d: -f1)
port=$(echo "$hostport" | cut -d: -f2)

for i in $(seq 1 $timeout); do
  nc -z "$host" "$port" && exit 0
  echo "Waiting for $host:$port..."
  sleep 1
done

echo "Timeout waiting for $host:$port" >&2
exit 1