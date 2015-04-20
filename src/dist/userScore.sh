#/bin/sh
OUTPUT=../../target/output.log
curl --data "score=1000" 'http://localhost:8081/1/score?sessionkey=123456' >> $OUTPUT
echo >> $OUTPUT
