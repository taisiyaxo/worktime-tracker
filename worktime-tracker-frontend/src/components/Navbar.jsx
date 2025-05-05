import { Link } from 'react-router-dom'
import { useAuth } from '@/context/AuthContext'

export default function Navbar() {
  const { token, logout } = useAuth()
  return (
    <nav className="navbar">
      <Link to="/tasks" className="nav-logo">Трекер задач</Link>
      <div className="nav-links">
        {token
          ? <button onClick={logout} className="btn-link">Выйти</button>
          : <>
              <Link to="/login"  className="btn-link">Войти</Link>
              <Link to="/signup" className="btn-link">Зарегистрироваться</Link>
            </>}
      </div>
    </nav>
  )
}
