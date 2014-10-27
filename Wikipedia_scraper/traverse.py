import sys
import os
from fs.osfs import OSFS

"""
This module finds all hmtl files in the supplied directory


You might find it helpufl to install the following packages beforehand:
sudo pip install beautifulsoup
sudo pip install fs

"""

home_fs = OSFS('/')


"""
Get user supplied file path
"""
def check_path(path):
	global home_fs
	if not home_fs.exists(path):
		print 'The path supplied does not exist'
		os._exit(1)
	elif not home_fs.isdir(path):
		print 'Please provide a directory not a file path'
		os._exit(1)
	else:
		home_fs = OSFS(path)

"""
Walk directory
"""
def generate_list(path):
	check_path(path)
	the_list = []
	#traverse the unzipped folder containing wikipedia entries
	for f in home_fs.walkfiles(wildcard='*.html'):
		if home_fs.isfile(f):
			
			if '~' in f: continue #avoid other special files
			#encode ascii because implicit str converstion raises errors
			the_list.append(home_fs.getsyspath(f).encode('utf-8').strip())
	return the_list
			

if __name__ == "__main__":
	#get user supplied path if any
	if len(sys.argv) == 1:
		print 'Defaulting to default path + ',
		'\n ~ on *nix' + '\n#c:\users\<login name> on Windows'
		path = '~'
	else:
		print str(generate_list(sys.argv[1]))