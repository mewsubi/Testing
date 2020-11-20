@echo off
cd /d %~dp0
cd .
set unescaped=%~dp0
set escaped=%unescaped:\=\\%
dir /s /B *.class > sources.txt
powershell -Command "(gc sources.txt) -replace '%escaped%', '' | Out-File -encoding ASCII sources.txt"
jar cmf testing.mf ../Testing.jar plugin.yml @sources.txt
cd ..