
cd ..
set CURRENT_DIR=%cd%

set CLASSPATH=%CURRENT_DIR%;%CURRENT_DIR%/smileBatch/WEB-INF/classes;%CURRENT_DIR%/smileBatch/WEB-INF/lib/*

java -server -Xms512m -Xmx512m -XX:PermSize=256m -XX:MaxPermSize=256m -classpath "%CLASSPATH%" %1

echo off
