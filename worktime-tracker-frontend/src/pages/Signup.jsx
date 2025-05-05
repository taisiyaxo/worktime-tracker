import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth }     from '@/context/AuthContext'
import Input           from '@/components/Input'
import Button          from '@/components/Button'

export default function Signup() {
  const [form, setForm] = useState({ username: '', email: '', password: '' })
  const { signup }     = useAuth()
  const nav             = useNavigate()
  const [error, setError] = useState(null)

  const handleSubmit = async e => {
    e.preventDefault()
    try {
      await signup(form)
      nav('/login')
    } catch {
      setError('Signup failed')
    }
  }

  return (
    <div className="auth-container">
      <form onSubmit={handleSubmit} className="auth-form">
        <h2>Зарегистрироваться</h2>
        {error && <p className="error-msg">{error}</p>}
        <Input label="Логин"
               value={form.username}
               onChange={e => setForm({ ...form, username: e.target.value })} />
        <Input label="Email" type="email"
               value={form.email}
               onChange={e => setForm({ ...form, email: e.target.value })} />
        <Input label="Пароль" type="password"
               value={form.password}
               onChange={e => setForm({ ...form, password: e.target.value })} />
        <Button type="submit">Зарегистрироваться</Button>
      </form>
    </div>
  )
}
