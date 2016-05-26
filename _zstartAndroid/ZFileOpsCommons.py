import sys, re, os, shutil, random

def confirm(txt):
	ans = raw_input(txt + " (y/n) ")
	if ans == 'y':
		return True
	return False

class MyFileOps:
	def __init__(self, dest, src="."):
		self.src = src
		self.dest = dest
	def copyFile(self, fileSrc):
		shutil.copy(self.src + "/" + fileSrc, self.dest + "/" + fileSrc)
	def copyTree(self, pathSrc):
		dst = self.dest + "/" + pathSrc
		if os.path.exists(dst):
			shutil.rmtree(dst)
		shutil.copytree(self.src + "/" + pathSrc, dst)
	def copyPackage(self, pathSrc, pathDest):
		dst = self.dest + "/" + pathDest
		if os.path.exists(dst):
			shutil.rmtree(dst)
		shutil.copytree(self.src + "/" + pathSrc, dst)
	def refactor_rename(self, path, what, intoWhat):
		self.refactor_rename_inner(self.dest + "/" + path, what, intoWhat)
	def refactor_rename_inner(self, path, what, intoWhat):
		for fname in os.listdir(path):
			fullPath = path + "/" + fname
			if os.path.isdir(fullPath):
				self.refactor_rename_inner(fullPath, what, intoWhat)
			else:
				self.refactor_rename_file_inner(fullPath, what, intoWhat)
	def refactor_rename_file(self, path, what, intoWhat):
		self.refactor_rename_file_inner(self.dest + "/" + path, what, intoWhat)
	def refactor_rename_file_inner(self, path, what, intoWhat):
		fff = open(path, "r")
		text = fff.read()
		fff.close()
		text = text.replace(what, intoWhat)
		fff = open(path, "w")
		fff.write(text)
		fff.close()
		