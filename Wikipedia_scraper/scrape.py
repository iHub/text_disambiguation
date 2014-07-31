import urllib2
import re
import unicodedata
from fs.osfs import OSFS
from bs4 import BeautifulSoup

"""
This module does the scraping
The user supplies the directory path to directory
containing html files

You might find it helpufl to install the following packages beforehand:
sudo pip install beautifulsoup
sudo pip install fs
"""


filename = ""

"""
Get user supplied file path
"""
def check_args():
	home_fs = OSFS('/')
	global filename
	if len(sys.argv) == 1:
		print 'Please provide path as command line argument'
		print 'usage: python scrape.py [filepath..]'
		os._exit(1)
	else:
		filename = sys.argv[1]
		if not home_fs.exists(path):
			print 'The path supplied does not exist'
			os._exit(1)

"""
Scrape
"""
def scrape_text(path):
	soup = BeautifulSoup(open(path))
	texts = soup.findAll(text=True)

	invalid = ["ul", "li","table", "script"]
	invalid_id = ["siteSub","catlinks"]
	invalid_span = ["mw-headline"]
	invalid_div = ["noprint"]

	for tag in soup.findAll(True):
		if tag.name in invalid:
			tag.replaceWith('')
		elif tag.get('id') in invalid_id:
			tag.replaceWith('')


	for div_id in invalid_div:
		for div in soup.findAll('div', div_id): div.extract()

	for span_id in invalid_span:
		for div in soup.findAll('span', span_id): div.extract()	    


	body_of_text = soup.find(attrs={'id':'bodyContent'})

	body_of_text = str(body_of_text)
	soup = BeautifulSoup(body_of_text)

	the_text = re.sub("\[.*?\]","", soup.get_text().strip()) #remove [headings]
	the_text = re.sub("\\s+"," ",the_text) #compress whitespace

	return the_text


if __name__ == "__main__":
	check_args()
	print scrape_text(filename)
