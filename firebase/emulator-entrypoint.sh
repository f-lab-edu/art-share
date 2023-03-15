#/bin/bash
firebase emulators:start --only auth -P art-share --import=/local_db --export-on-exit -c /firebase.json
