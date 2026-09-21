# Script para detener todos los microservicios y Caddy

Write-Host "Deteniendo todos los microservicios de Java y Caddy..." -ForegroundColor Yellow

Get-Process -Name "java" -ErrorAction SilentlyContinue | Stop-Process -Force
Get-Process -Name "caddy" -ErrorAction SilentlyContinue | Stop-Process -Force

Write-Host "¡Todos los servicios han sido detenidos!" -ForegroundColor Green
