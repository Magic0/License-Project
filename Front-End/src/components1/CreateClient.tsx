import type React from "react"
import { useState } from "react"
import { clientApi } from "../api"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

export default function CreateClient() {
  const [formData, setFormData] = useState({
    dni: "",
    name: "",
    lastname: "",
    email: "",
    birthday: "",
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const response = await clientApi.post("/clients", formData)
      console.log("Client created:", response.data)
      alert("Cliente creado exitosamente!")
    } catch (error) {
      console.error("Failed to create client:", error)
      alert("Error al crear el cliente. Por favor, intente de nuevo.")
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <Label htmlFor="dni">DNI</Label>
        <Input id="dni" name="dni" value={formData.dni} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="name">Nombre</Label>
        <Input id="name" name="name" value={formData.name} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="lastname">Apellido</Label>
        <Input id="lastname" name="lastname" value={formData.lastname} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="email">Email</Label>
        <Input id="email" name="email" type="email" value={formData.email} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="birthday">Fecha de Nacimiento</Label>
        <Input id="birthday" name="birthday" type="date" value={formData.birthday} onChange={handleChange} required />
      </div>
      <Button type="submit">Crear Cliente</Button>
    </form>
  )
}

