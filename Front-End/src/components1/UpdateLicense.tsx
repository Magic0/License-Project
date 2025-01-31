import type React from "react"
import { useState } from "react"
import { licenseApi } from "../api"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"

export default function UpdateLicense() {
  const [formData, setFormData] = useState({
    licenseId: "",
    licenseType: "",
    licenseCategory: "",
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
      const response = await licenseApi.patch(`/licenses/${formData.licenseId}`, {
        licenseType: formData.licenseType,
        licenseCategory: formData.licenseCategory,
      })
      console.log("License updated:", response.data)
      alert("License updated successfully!")
    } catch (error) {
      console.error("Failed to update license:", error)
      alert("Failed to update license. Please try again.")
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <Label htmlFor="licenseId">License ID</Label>
        <Input id="licenseId" name="licenseId" value={formData.licenseId} onChange={handleChange} required />
      </div>
      <div>
        <Label htmlFor="licenseType">License Type</Label>
        <Select onValueChange={(value) => handleSelectChange("licenseType", value)}>
          <SelectTrigger>
            <SelectValue placeholder="Select license type" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="A">A</SelectItem>
            <SelectItem value="B">B</SelectItem>
            <SelectItem value="C">C</SelectItem>
          </SelectContent>
        </Select>
      </div>
      <div>
        <Label htmlFor="licenseCategory">License Category</Label>
        <Select onValueChange={(value) => handleSelectChange("licenseCategory", value)}>
          <SelectTrigger>
            <SelectValue placeholder="Select license category" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="I-C">I-C</SelectItem>
            <SelectItem value="II-C">II-C</SelectItem>
            <SelectItem value="III-C">III-C</SelectItem>
          </SelectContent>
        </Select>
      </div>
      <Button type="submit">Update License</Button>
    </form>
  )
}

