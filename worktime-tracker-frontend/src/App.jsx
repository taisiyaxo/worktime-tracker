import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Login      from '@/pages/Login'
import Signup     from '@/pages/Signup'
import TasksList  from '@/pages/TasksList'
import TaskDetail from '@/pages/TaskDetail'
import { AuthProvider, useAuth } from '@/context/AuthContext'
import Navbar     from '@/components/Navbar'

function PrivateRoute({ children }) {
  const { token } = useAuth()
  return token ? children : <Navigate to="/login" replace />
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar />
        <div className="app-container">
          <Routes>
            <Route path="/login"  element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/tasks"  element={<PrivateRoute><TasksList /></PrivateRoute>} />
            <Route path="/tasks/:id" element={<PrivateRoute><TaskDetail /></PrivateRoute>} />
            <Route path="*" element={<Navigate to="/tasks" replace />} />
          </Routes>
        </div>
      </BrowserRouter>
    </AuthProvider>
  )
}
