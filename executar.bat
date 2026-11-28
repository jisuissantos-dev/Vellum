@echo off
chcp 65001 >nul
cd /d "%~dp0"

if not exist .env (
    copy .env.example .env >nul
    echo Arquivo .env criado a partir do .env.example.
)

if exist mvnw.cmd (
    call mvnw.cmd -q compile exec:java "-Dexec.mainClass=br.edu.poo.m0013.Main"
    goto :fim
)

where mvn >nul 2>nul
if errorlevel 1 (
    echo [!] Maven nao encontrado. Instale o Maven em https://maven.apache.org/install.html e tente novamente.
    goto :fim
)

call mvn -q compile exec:java "-Dexec.mainClass=br.edu.poo.m0013.Main"

:fim
pause