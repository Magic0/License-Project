import { useAuth } from "../AuthContext";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import CreateClient from "./CreateClient";
import IssueLicense from "./IssueLicense";
import UpdateLicense from "./UpdateLicense";
import ClientList from "./ClientList";

export default function Dashboard() {
  const { logout, roles } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <div className="flex flex-col min-h-screen">
      {/* Navbar */}
      <header className="bg-gray-900 text-white p-4 flex justify-between items-center shadow-md">
        <h1 className="text-2xl font-bold">Dashboard</h1>
        <Button variant="destructive" onClick={handleLogout}>
          Cerrar Sesión
        </Button>
      </header>

      {/* Contenido */}
      <main className="container mx-auto p-6 flex flex-col gap-6">
        <Tabs defaultValue="createClient" className="w-full">
          <TabsList className="grid w-full grid-cols-4 bg-gray-200 rounded-md">
            <TabsTrigger value="createClient">Crear Cliente</TabsTrigger>
            <TabsTrigger value="issueLicense">Emitir Licencia</TabsTrigger>
            <TabsTrigger value="updateLicense">Actualizar Licencia</TabsTrigger>
            <TabsTrigger value="clientList">Lista de Clientes</TabsTrigger>
          </TabsList>

          <div className="border border-gray-300 p-4 rounded-lg shadow-sm bg-white">
            <TabsContent value="createClient">
              <CreateClient />
            </TabsContent>
            <TabsContent value="issueLicense">
              <IssueLicense />
            </TabsContent>
            <TabsContent value="updateLicense">
              <UpdateLicense />
            </TabsContent>
            <TabsContent value="clientList">
              <ClientList />
            </TabsContent>
          </div>
        </Tabs>
      </main>

      {/* Footer */}
      <footer className="bg-gray-900 text-white text-center p-3 mt-auto">
        © {new Date().getFullYear()} Dashboard - Todos los derechos reservados
      </footer>
    </div>
  );
}
