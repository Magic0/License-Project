import React, { useState } from 'react'
import { licenseApi } from '../api'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"

const LICENSE_TYPES = ['A', 'B']
const LICENSE_CATEGORIES = {
  'A': ['I', 'II-A', 'II-B', 'III-A', 'III-B', 'III-C'],
  'B': ['I', 'II-A', 'II-B', 'II-C']
}

export default function IssueLicense() {
  const [formData, setFormData] = useState({
    licenseType: '',
    licenseCategory: '',
    clientDni: '',
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSelectChange = (name: string, value: string) => {
    setFormData({ ...formData, [name]: value })
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const response = await licenseApi.post('/licenses/emitir', {
        licenseType: formData.licenseType,
        licenseCategory: formData.licenseCategory,
        client: { dni: formData.clientDni },
      })
      console.log('License issued:', response.data)
      alert('Licencia emitida exitosamente!')
    } catch (error) {
      console.error('Failed to issue license:', error)
      alert('Error al emitir la licencia. Por favor, intente de nuevo.')
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <Label htmlFor="licenseType">Tipo de Licencia</Label>
        <Select onValueChange={(value) => handleSelectChange('licenseType', value)}>
          <SelectTrigger>
            <SelectValue placeholder="Seleccione tipo de licencia" />
          </SelectTrigger>
          <SelectContent>
            {LICENSE_TYPES.map(type => (
              <SelectItem key={type} value={type}>{type}</SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
      <div>
        <Label htmlFor="licenseCategory">Categoría de Licencia</Label>
        <Select onValueChange={(value) => handleSelectChange('licenseCategory', value)}>
          <SelectTrigger>
            <SelectValue placeholder="Seleccione categoría de licencia" />
          </SelectTrigger>
          <SelectContent>
            {formData.licenseType && LICENSE_CATEGORIES[formData.licenseType as keyof typeof LICENSE_CATEGORIES].map(category => (
              <SelectItem key={category} value={category}>{category}</SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
      <div>
        <Label htmlFor="clientDni">DNI del Cliente</Label>
        <Input id="clientDni" name="clientDni" value={formData.clientDni} onChange={handleChange} required />
      </div>
      <Button type="submit">Emitir Licencia</Button>
    </form>
  )
}
