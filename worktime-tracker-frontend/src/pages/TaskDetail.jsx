import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import api from '@/api/axios'
import Button from '@/components/Button'
import Modal from '@/components/Modal'

export default function TaskDetail() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [task, setTask] = useState(null)
  const [entries, setEntries] = useState([])
  const [comment, setComment] = useState('')
  const [editingEntry, setEditingEntry] = useState(null)
  const [editComment, setEditComment] = useState('')
  const [showEditModal, setShowEditModal] = useState(false)

  useEffect(() => {
    fetchTask()
    fetchEntries()
  }, [id])

  const fetchTask = async () => {
    try {
      const { data } = await api.get(`/tasks/${id}`)
      setTask(data)
    } catch (error) {
      console.error('Failed to fetch task:', error)
    }
  }

  const fetchEntries = async () => {
    try {
      const { data } = await api.get(`/time-entries/task/${id}`)
      setEntries(data)
    } catch (error) {
      console.error('Failed to fetch entries:', error)
    }
  }

  const addEntry = async () => {
    const now = new Date().toISOString()
    try {
      const { data } = await api.post(`/time-entries/task/${id}`, {
        startTime: now, 
        comment
      })
      setEntries(prev => [...prev, data])
      setComment('')
    } catch (error) {
      console.error('Failed to add entry:', error)
    }
  }

  const deleteEntry = async (entryId) => {
    try {
      await api.delete(`/time-entries/${entryId}`)
      setEntries(prev => prev.filter(e => e.id !== entryId))
    } catch (error) {
      console.error('Failed to delete entry:', error)
    }
  }

  const startEditEntry = (entry) => {
    setEditingEntry(entry)
    setEditComment(entry.comment)
    setShowEditModal(true)
  }

  const updateEntry = async () => {
    try {
      const { data } = await api.put(`/time-entries/${editingEntry.id}`, {
        comment: editComment,
        startTime: editingEntry.startTime,
        endTime: editingEntry.endTime
      })
      setEntries(prev => prev.map(e => e.id === data.id ? data : e))
      setShowEditModal(false)
    } catch (error) {
      console.error('Failed to update entry:', error)
    }
  }

  const stopEntry = async (entryId) => {
    try {
      const endTime = new Date().toISOString()
      const { data } = await api.put(`/time-entries/${entryId}`, {
        endTime
      })
      setEntries(prev => prev.map(e => e.id === data.id ? data : e))
    } catch (error) {
      console.error('Failed to stop entry:', error)
    }
  }

  if (!task) return <p>Загрузка...</p>

  return (
    <div className="detail-container">
      <h2>Таска: {task.title}</h2>
      <p>{task.description}</p>

      <div className="entries">
        <h3>Время</h3>
        {entries.map(e => (
          <div key={e.id} className="entry-card">
            <div>
              <span>{new Date(e.startTime).toLocaleString()}</span>
              {e.endTime && <span> - {new Date(e.endTime).toLocaleTimeString()}</span>}
              <p>{e.comment}</p>
            </div>
            <div className="entry-actions">
              {!e.endTime && (
                <Button onClick={() => stopEntry(e.id)} size="small">Стоп</Button>
              )}
              <Button onClick={() => startEditEntry(e)} size="small">Редактировать</Button>
              <Button onClick={() => deleteEntry(e.id)} size="small" variant="danger">
                Удалить
              </Button>
            </div>
          </div>
        ))}
      </div>

      <div className="new-entry">
        <textarea 
          className="input"
          placeholder="Описание"
          value={comment}
          onChange={e => setComment(e.target.value)}
        />
        <Button onClick={addEntry}>Добавить время</Button>
      </div>

      
      {showEditModal && (
        <Modal onClose={() => setShowEditModal(false)}>
          <h3>Редактировать время</h3>
          <textarea
            className="input"
            value={editComment}
            onChange={e => setEditComment(e.target.value)}
          />
          <div className="modal-actions">
            <Button onClick={updateEntry}>Сохранить</Button>
            <Button onClick={() => setShowEditModal(false)} variant="secondary">
              Отмена
            </Button>
          </div>
        </Modal>
      )}
    </div>
  )
}