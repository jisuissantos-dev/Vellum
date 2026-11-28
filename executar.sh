#!/bin/sh
# Executa o Vellum no Linux e no macOS.

cd "$(dirname "$0")" || exit 1

if [ ! -f .env ]; then
  cp .env.example .env
  echo "Arquivo .env criado a partir do .env.example."
fi

if [ -f mvnw ]; then
  sh ./mvnw -q compile exec:java "-Dexec.mainClass=br.edu.poo.m0013.Main"
elif command -v mvn >/dev/null 2>&1; then
  mvn -q compile exec:java "-Dexec.mainClass=br.edu.poo.m0013.Main"
else
  echo "[!] Maven nao encontrado. Instale o Maven em https://maven.apache.org/install.html e tente novamente."
  exit 1
fi