import sys, re, os, shutil

if os.path.exists("./video"):
	shutil.rmtree("./video")
os.mkdir("./video")
