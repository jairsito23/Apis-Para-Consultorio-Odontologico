# Script para iniciar todos los microservicios y Caddy al mismo tiempo

Write-Host "====================================================" -ForegroundColor Cyan
Write-Host "  Iniciando Microservicios Odontología + Caddy Gateway" -ForegroundColor Cyan
Write-Host "====================================================" -ForegroundColor Cyan

Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$PSScriptRoot\auth-service'; .\mvnw spring-boot:run"
Write-Host "[1/6] Lanzando Auth Service en puerto 8081..." -ForegroundColor Green

Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$PSScriptRoot\paciente-service'; .\mvnw spring-boot:run"
Write-Host "[2/6] Lanzando Paciente Service en puerto 8082..." -ForegroundColor Green

Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$PSScriptRoot\odontologo-service'; .\mvnw spring-boot:run"
Write-Host "[3/6] Lanzando Odontologo Service en puerto 8083..." -ForegroundColor Green

Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$PSScriptRoot\cita-service'; .\mvnw spring-boot:run"
Write-Host "[4/6] Lanzando Cita Service en puerto 8084..." -ForegroundColor Green

Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$PSScriptRoot\historial-service'; .\mvnw spring-boot:run"
Write-Host "[5/6] Lanzando Historial Service en puerto 8085..." -ForegroundColor Green

Start-Sleep -Seconds 3

Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$PSScriptRoot'; .\caddy.exe run"
Write-Host "[6/6] Lanzando Caddy Gateway en puerto 8000..." -ForegroundColor Yellow

Write-Host "----------------------------------------------------" -ForegroundColor Cyan
Write-Host "¡Listo! Todos los 5 microservicios y Caddy se están iniciando." -ForegroundColor White
Write-Host "Acceso centralizado via Caddy: http://localhost:8000" -ForegroundColor Yellow
