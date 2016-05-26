CONFIG_FILENAME = "ZNewProjectConf.txt"

import sys, re, os, shutil
import ZFileOpsCommons
from configobj import ConfigObj
config = ConfigObj(CONFIG_FILENAME)

projName = config["name"]
packagePart = config["packagePart"]
destFolder = "../" + projName

if not os.path.exists(destFolder):
	os.mkdir(destFolder)
else:
	flag = ZFileOpsCommons.confirm("Directory " + destFolder + " exists. Overwrite?")
	if not flag:
		print "no changes were made"
		sys.exit(0)

ops = ZFileOpsCommons.MyFileOps(destFolder)
ops.copyFile(".classpath")
ops.copyFile(".project")
ops.copyFile("AndroidManifest.xml")
ops.copyFile("proguard.cfg")
ops.copyFile("project.properties")
ops.copyFile("zz_androidAutoDpi.py")
ops.copyFile("ZZZ_androidAutoDpi.bat")
ops.copyTree("assets")
ops.copyTree("res")
ops.copyPackage("src/org/acouster/proj", "src/org/acouster/" + packagePart)
ops.copyPackage("src/org/acouster/android/proj", "src/org/acouster/android/" + packagePart)
ops.refactor_rename("src", "package org.acouster.proj;", "package org.acouster." + packagePart + ";")
ops.refactor_rename("src", "package org.acouster.proj.bubbles;", "package org.acouster." + packagePart + ".bubbles;")
ops.refactor_rename("src", "package org.acouster.android.proj;", "package org.acouster.android." + packagePart + ";")
ops.refactor_rename("src", "import org.acouster.proj.", "import org.acouster." + packagePart + ".")
ops.refactor_rename("src", "import org.acouster.android.proj.", "import org.acouster.android." + packagePart + ".")
ops.refactor_rename_file(".project", "<name>_zstartAndroid</name>", "<name>" + projName + "</name>")
ops.refactor_rename_file("AndroidManifest.xml", 'package="org.acouster.android.proj"', 'package="org.acouster.android.' + packagePart + '"')
ops.refactor_rename_file("ZZZ_androidAutoDpi.bat", '_zstartAndroid', projName)
ops.refactor_rename_file("res/values/strings.xml", '<string name="app_name">proj</string>', '<string name="app_name">' + projName + '</string>')


#org.acouster.android.proj
#org.acouster.android.


