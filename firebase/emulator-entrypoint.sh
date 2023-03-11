#/bin/bash
gcloud components install firebase-tools -q && firebase init emulators &&
firebase emulators:start --only auth -P art-share --import=/local_db --export-on-exit -c /firebase.json
