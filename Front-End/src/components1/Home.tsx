import React from 'react'
import { Link } from 'react-router-dom'
import { Button } from "@/components/ui/button"

export default function Home() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <h1 className="text-3xl font-bold mb-8">Bienvenido al Sistema de Gestión de Licencias</h1>
      <div className="space-x-4">
        <Button asChild>
          <Link to="/login">Iniciar Sesión</Link>
        </Button>
        <Button asChild variant="outline">
          <Link to="/register">Registrarse</Link>
        </Button>
      </div>
    </div>
  )
}
