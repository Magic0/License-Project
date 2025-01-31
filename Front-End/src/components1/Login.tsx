import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { api, setAuthToken } from '../api'
import { useAuth } from '../AuthContext'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

export default function Login() {
  const [formData, setFormData] = useState({ username: '', password: '' })
  const { setAuth } = useAuth()
  const navigate = useNavigate()

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const response = await api.post('/auth/authenticate', formData)
      setAuth(response.data.token, response.data.roles)
      setAuthToken(response.data.token)
      navigate('/dashboard')
    } catch (error) {
      console.error('Login failed:', error)
      alert('Login failed. Please try again.')
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4 max-w-md mx-auto mt-8">
      <h2 className="text-2xl font-bold mb-4">Iniciar Sesión</h2>
      <div>
        <Label htmlFor="username">Usuario</Label>
        <Input id="username" name="username" value={formData.username} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="password">Contraseña</Label>
        <Input id="password" name="password" type="password" value={formData.password} onChange={handleChange} required />
      </div>
      <Button type="submit" className="w-full">Iniciar Sesión</Button>
    </form>
  )
}
