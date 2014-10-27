import os
import sys
from traverse import generate_list
from scrape import scrape_text
from random import randint

"""
[INTRODUCTION]
This is a driver that tests the traverser and scraper tools
found in traverse.py and scrape.py respectively

Tests a random selection of 5 articles

You might find it helpufl to install the following packages beforehand:
sudo pip install beautifulsoup
sudo pip install fs

Expected behavior:
-->Some wikipedia pages are not articles and result in 
'None' generated 

-->Some wikipedia pages are empty thus blank whitespace is generated

-->The usual wikipedia pages will result in extracted text being displayed

[USAGE]
Run the script in the directory that contains the extracted wikipedia 
archive [note you'll have to move traverse and scrape too]

OR

Run the script and supply the directory path the extracted wikipedia
archive ie: drive.py /Users/username/Downloads/sw/articles/


[TO_DO: Implement threading]
"""

path = ""

if len(sys.argv) == 1: 
	path = os.path.dirname(os.path.abspath(__file__))
else: path = sys.argv[1]

file_list = generate_list(path)
list_size = len(file_list)
test_cases = 5

if list_size < test_cases: test_cases = list_size

indices = [randint(0,list_size) for p in range(0,test_cases)]

for i in indices:
	path = file_list[i]
	print "*~"*20
	print path
	print "\n"*3
	print scrape_text(path)
	print "\n"*3
