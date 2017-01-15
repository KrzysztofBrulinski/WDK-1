#!/bin/bash

sudo apt-get install hashdeep -y
sudo apt-get install ssdeep -y
echo " " > hashe.txt
for file1 in "$@"
do
	echo "md5" >> hashe.txt
	md5sum $file1 >> hashe.txt
	echo "sha256" >> hashe.txt
	sha256sum $file1 >> hashe.txt
	echo "ssdeep" >> hashe.txt
	ssdeep $file1 >> hashe.txt
	echo "md5deep" >> hashe.txt
	hashdeep $file1 >> hashe.txt
done
