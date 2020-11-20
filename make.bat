@echo off
cd /d %~dp0
cd src
dir /s /B *.java > ../sources.txt
cd ..
javac -cp lib\spigot-api.jar -cp lib\spigot-1.16.4.jar -sourcepath src -d build @sources.txt
del sources.txt
call build\build.bat