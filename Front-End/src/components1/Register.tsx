import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { api, setAuthToken } from '../api'
import { useAuth } from '../AuthContext'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Checkbox } from "@/components/ui/checkbox"

const ROLES = ["ROLE_CREATE", "ROLE_READ", "ROLE_UPDATE", "ROLE_DELETE"]

export default function Register() {
  const [formData, setFormData] = useState({
    name: '',
    lastname: '',
    username: '',
    password: '',
    email: '',
  })
  const [selectedRoles, setSelectedRoles] = useState<string[]>([])
  const navigate = useNavigate()
  const { setAuth } = useAuth()

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleRoleChange = (role: string) => {
    setSelectedRoles(prev => 
      prev.includes(role) ? prev.filter(r => r !== role) : [...prev, role]
    )
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const response = await api.post('/auth/register', {
        ...formData,
        roles: selectedRoles
      })
      alert('Registro exitoso. Iniciando sesión...')
      setAuth(response.data.token, response.data.roles)
      setAuthToken(response.data.token)
      navigate('/dashboard')
    } catch (error) {
      console.error('Registration failed:', error)
      alert('El registro falló. Por favor, inténtalo de nuevo.')
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4 max-w-md mx-auto mt-8">
      <h2 className="text-2xl font-bold mb-4">Registrarse</h2>
      <div>
        <Label htmlFor="name">Nombre</Label>
        <Input id="name" name="name" value={formData.name} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="lastname">Apellido</Label>
        <Input id="lastname" name="lastname" value={formData.lastname} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="username">Usuario</Label>
        <Input id="username" name="username" value={formData.username} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="password">Contraseña</Label>
        <Input id="password" name="password" type="password" value={formData.password} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="email">Email</Label>
        <Input id="email" name="email" type="email" value={formData.email} onChange={handleChange} required />
      </div>
      <div>
        <Label>Roles</Label>
        <div className="space-y-2">
          {ROLES.map(role => (
            <div key={role} className="flex items-center">
              <Checkbox 
                id={role} 
                checked={selectedRoles.includes(role)}
                onCheckedChange={() => handleRoleChange(role)}
              />
              <Label htmlFor={role} className="ml-2">{role}</Label>
            </div>
          ))}
        </div>
      </div>
      <Button type="submit" className="w-full">Registrarse</Button>
    </form>
  )
}
