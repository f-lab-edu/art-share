#!/bin/bash
firebase emulators:start --only auth -P art-share --import=/local_db --export-on-exit -c /firebase/firebase.json &

while ! nc -z localhost 9099; do
  sleep 1
done

curl -X POST \
  "http://localhost:9099/identitytoolkit.googleapis.com/v1/accounts:signUp?key=" \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer owner' \
  -d '{
    "email": "user@test.com",
    "password": "pass1234",
    "returnSecureToken": true
  }'

while true; do sleep 1; done