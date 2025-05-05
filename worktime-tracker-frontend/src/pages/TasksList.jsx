import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '@/api/axios'
import Card from '@/components/Card'
import Button from '@/components/Button'
import Modal from '@/components/Modal' 

export default function TasksList() {
  const [tasks, setTasks] = useState([])
  const [newTitle, setNewTitle] = useState('')
  const [editingTask, setEditingTask] = useState(null)
  const [editTitle, setEditTitle] = useState('')
  const [showEditModal, setShowEditModal] = useState(false)
  const nav = useNavigate()

  useEffect(() => {
    fetchTasks()
  }, [])

  const fetchTasks = async () => {
    try {
      const { data } = await api.get('/tasks')
      setTasks(data)
    } catch (error) {
      console.error('Failed to fetch tasks:', error)
    }
  }

  const addTask = async () => {
    if (!newTitle.trim()) return
    try {
      const { data } = await api.post('/tasks', { title: newTitle })
      setTasks(prev => [...prev, data])
      setNewTitle('')
    } catch (error) {
      console.error('Failed to add task:', error)
    }
  }

  const deleteTask = async (taskId) => {
    try {
      await api.delete(`/tasks/${taskId}`)
      setTasks(prev => prev.filter(t => t.id !== taskId))
    } catch (error) {
      console.error('Failed to delete task:', error)
    }
  }

  const startEdit = (task) => {
    setEditingTask(task)
    setEditTitle(task.title)
    setShowEditModal(true)
  }

  const updateTask = async () => {
    if (!editTitle.trim()) return
    try {
      const { data } = await api.put(`/tasks/${editingTask.id}`, { 
        title: editTitle,
        description: editingTask.description 
      })
      setTasks(prev => prev.map(t => t.id === data.id ? data : t))
      setShowEditModal(false)
    } catch (error) {
      console.error('Failed to update task:', error)
    }
  }

  return (
    <div className="tasks-container">
      <div className="tasks-header">
        <h2>Мои таски</h2>
        <div className="new-task">
          <input 
            className="input"
            placeholder="Новая таска"
            value={newTitle}
            onChange={e => setNewTitle(e.target.value)}
          />
          <Button onClick={addTask}>Добавить</Button>
        </div>
      </div>
      <div className="tasks-list">
        {tasks.map(t => (
          <Card key={t.id}>
            <div onClick={() => nav(`/tasks/${t.id}`)}>
              <h3>{t.title}</h3>
            </div>
            <div className="task-actions">
              <Button onClick={() => startEdit(t)} size="small">Редактировать</Button>
              <Button onClick={() => deleteTask(t.id)} size="small" variant="danger">
                Дроп
              </Button>
            </div>
          </Card>
        ))}
      </div>

      {showEditModal && (
        <Modal onClose={() => setShowEditModal(false)}>
          <h3>Редактировать таску</h3>
          <input
            className="input"
            value={editTitle}
            onChange={e => setEditTitle(e.target.value)}
          />
          <div className="modal-actions">
            <Button onClick={updateTask}>Сохранить</Button>
            <Button onClick={() => setShowEditModal(false)} variant="secondary">
              Отмена
            </Button>
          </div>
        </Modal>
      )}
    </div>
  )
}