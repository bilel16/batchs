rem cleanup SMILEBATCH directory content then remove SMILEBATCH folder itself
if not exist SMILEBATCH goto build
echo Cleaning old content of SMILEBATCH folder and subfolders
del /q SMILEBATCH/*.* 
for /f "Tokens=*" %%G in ('dir SMILEBATCH/B') do rd /s /q "SMILEBATCH/%%G"
rd SMILEBATCH

:build
echo Will initialize the required directories
md SMILEBATCH
md SMILEBATCH\smileBatch
md SMILEBATCH\config
md SMILEBATCH\jasper
md SMILEBATCH\images
md SMILEBATCH\runtime

rem echo Build SMILE-BATCH project from the source...
call BUILD_PROJECT /I
ROBOCOPY "target/smileBatch-1.0.0" "SMILEBATCH/smileBatch" /E
ROBOCOPY "config" "SMILEBATCH/config" /E
ROBOCOPY "jasper" "SMILEBATCH/jasper" /E
ROBOCOPY "images" "SMILEBATCH/images" /E
ROBOCOPY "runtime" "SMILEBATCH/runtime" /E