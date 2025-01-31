import React, { createContext, useState, useContext } from 'react';

interface AuthContextType {
  token: string | null;
  roles: string[];
  setAuth: (token: string, roles: string[]) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem("token"));
  const [roles, setRoles] = useState<string[]>(() => JSON.parse(localStorage.getItem("roles") || "[]"));

  const setAuth = (newToken: string, newRoles: string[]) => {
    if (newToken) {
      localStorage.setItem("token", newToken);
      localStorage.setItem("roles", JSON.stringify(newRoles));
    } else {
      localStorage.removeItem("token");
      localStorage.removeItem("roles");
    }

    setToken(newToken);
    setRoles(newRoles);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("roles");
    setToken(null);
    setRoles([]);
  };

  return (
    <AuthContext.Provider value={{ token, roles, setAuth, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
