export default function Button({ 
    children, 
    onClick, 
    type = 'button', 
    size = 'normal',
    variant = 'primary'
  }) {
    const sizeClass = size === 'small' ? 'btn-small' : ''
    const variantClass = variant === 'secondary' ? 'btn-secondary' : 
                        variant === 'danger' ? 'btn-danger' : ''
    
    return (
      <button 
        className={`btn ${sizeClass} ${variantClass}`}
        onClick={onClick} 
        type={type}
      >
        {children}
      </button>
    )
  }