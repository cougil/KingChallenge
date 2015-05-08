#!/usr/bin/env bash
OUTPUT=../../target/output.log
curl 'http://localhost:8081/1/highscorelist' >> $OUTPUT; echo
echo >> $OUTPUT
