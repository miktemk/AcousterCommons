Acouster Commons
================

These are the core Acouster libraries on top of which you can write finished apps.

 - **_zstartAndroid**	- running `ZNewProject.py` will create 2 new project folders
  - Java libabry (Android independent, so you can test it out quickly on desktop. See `ztest.DesktopTests` below)
  - Android project that uses the above library and is fitted with some basic activities
 - **org.acouster.android** - basic activities and other Android-specific utilities
 - **org.acouster.game3d** - my own crappy 3D library. Supports wireframes and billboards. (never used it, had a plan to do Starglider similar to my 3D Pascal game) (Android-independent)
 - **org.acouster.simplexml** - Acouster-friendly wrapper for SimpleXML librabry. (XML data file parsing and binding) (Android-independent)
 - **org.acouster.zdesktop** - Android-unrelated utilities for desktop tests (Android-independent)
 - **org.acouster** - core packages. Interfaces for rendering and input. Many utilities (Android-independent)
 - **zterminal.Testo** - a sample Android app!
 - **ztest.DesktopTests** - Java desktop app where you can test-run your projects (Android-independent)

As you can see some packages are Android-independent. This is so you can quickly test your game as a stand-alone Java application.
