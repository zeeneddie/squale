@echo off

set INSTALL_DIR=%1%

REM remove unix files
del %INSTALL_DIR%\*.sh
del %INSTALL_DIR%\database\*.sh
del %INSTALL_DIR%\server\bin\*.sh
del %INSTALL_DIR%\squale.home\Squalix\bin\*.sh