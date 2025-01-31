import { useState, useEffect } from "react"
import { licenseApi } from "../api"
import { Button } from "@/components/ui/button"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"

interface License {
  idLicense: string
  issueDate: string
  expirationDate: string
  isActive: boolean
  licenseType: string
  licenseCategory: string
  client: {
    name: string
    lastname: string
    email: string
    birthday: string
    dni: string
  }
}

export default function ClientList() {
  const [licenses, setLicenses] = useState<License[]>([])
  const [filterType, setFilterType] = useState<"category" | "type">("category")
  const [filterValue, setFilterValue] = useState("")

  const fetchLicenses = async () => {
    try {
      let response
      if (filterType === "category") {
        response = await licenseApi.get(`/licenses/by-category?licenseCategory=${filterValue}`)
      } else {
        response = await licenseApi.get(`/licenses/by-type?licenseType=${filterValue}`)
      }
      setLicenses(response.data)
    } catch (error) {
      console.error("Failed to fetch licenses:", error)
      alert("Error al obtener las licencias. Por favor, intente de nuevo.")
    }
  }

  useEffect(() => {
    if (filterValue) {
      fetchLicenses()
    }
  }, [filterValue, filterType, fetchLicenses]) // Added fetchLicenses to dependencies

  return (
    <div className="space-y-4">
      <div className="flex space-x-4">
        <Select onValueChange={(value) => setFilterType(value as "category" | "type")}>
          <SelectTrigger>
            <SelectValue placeholder="Filtrar por" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="category">Categoría</SelectItem>
            <SelectItem value="type">Tipo</SelectItem>
          </SelectContent>
        </Select>
        <Select onValueChange={setFilterValue}>
          <SelectTrigger>
            <SelectValue placeholder="Seleccione valor" />
          </SelectTrigger>
          <SelectContent>
            {filterType === "category" ? (
              <>
                <SelectItem value="I-C">I-C</SelectItem>
                <SelectItem value="II-C">II-C</SelectItem>
                <SelectItem value="III-C">III-C</SelectItem>
              </>
            ) : (
              <>
                <SelectItem value="A">A</SelectItem>
                <SelectItem value="B">B</SelectItem>
              </>
            )}
          </SelectContent>
        </Select>
        <Button onClick={fetchLicenses}>Buscar Licencias</Button>
      </div>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Nombre</TableHead>
            <TableHead>DNI</TableHead>
            <TableHead>Tipo de Licencia</TableHead>
            <TableHead>Categoría de Licencia</TableHead>
            <TableHead>Fecha de Expiración</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {licenses.map((license) => (
            <TableRow key={license.idLicense}>
              <TableCell>{`${license.client.name} ${license.client.lastname}`}</TableCell>
              <TableCell>{license.client.dni}</TableCell>
              <TableCell>{license.licenseType}</TableCell>
              <TableCell>{license.licenseCategory}</TableCell>
              <TableCell>{new Date(license.expirationDate).toLocaleDateString()}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  )
}

