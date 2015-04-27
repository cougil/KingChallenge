#!/usr/bin/env bash
OUTPUT=../../target/output.log
curl 'http://localhost:8081/1234567890/login' >> $OUTPUT
echo >> $OUTPUT
