import { createContext, useContext, useState, useEffect } from 'react'
import api from '@/api/axios'

const AuthContext = createContext()

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('token'))
  const [user, setUser]   = useState(null)

  useEffect(() => {
    if (token) {
      localStorage.setItem('token', token)
      // можно загрузить /me, если готов эндпоинт
    } else {
      localStorage.removeItem('token')
    }
  }, [token])

  const login = async (credentials) => {
    const { data } = await api.post('/auth/signin', credentials)
    setToken(data.token)
    return data
  }

  const signup = async (info) => {
    await api.post('/auth/signup', info)
  }

  const logout = () => setToken(null)

  return (
    <AuthContext.Provider value={{ token, user, login, signup, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  return useContext(AuthContext)
}
