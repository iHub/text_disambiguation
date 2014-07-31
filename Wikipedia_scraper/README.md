# QUICK PYTHON TOOLS FOR EXTRACTING WIKIPEDIA TEXT: Quick python tools to extract wikipedia text

## Contents

Before proceeding, this README assumes you have already downloaded
and extracted the wikipedia archive. You can find archives at http://dumps.wikimedia.org/other/

The swahili archive in particular is at
http://dumps.wikimedia.org/other/static_html_dumps/current/sw/wikipedia-sw-html.tar.7z

* **Introduction** as 
    - "This bundle provides tools for quickly parsing wikipedia articles for main content"



* **Example Usage**: 
  scrape.py kenya.html (will extract main body text from text.html)

* **Getting Started**
  - prerequisites:
    do run the following terminal commands:
    ~ sudo pip install beautifulsoup
    ~ sudo pip install fs

  - running:
    -The Pièce de résistance is scrape.py
    -[Usage] scrape.py filename

  - additonal stuff:
    - driver.py can be used to get a feel of how to automate extracting text from a whole archive
    - driver.py uses traverse.py to obtain a list of all hmtl files, randomly chooses 5 files
    and runs scrape.py on them
    - [Usage] driver.py directory_containing_html
    - you can also avoid supplying arguments by runing driver within the directory containing html files 
    - I've included some sample html files in sample/ so you can immediately test: driver.py sample
    - please view the in-file comments of driver.py, scrape.py and traverse.py 

* **Design Goals**
  - It's designed to be lightweight
  - It's alpha (might be rewritten)

* **To be implemented**
  - Threading

* **Developer info**
  - saisi_delete_this_including_underscores <at> saisi_delete_this_including_underscores.me