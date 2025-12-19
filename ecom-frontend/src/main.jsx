/**
 * main.jsx
 * Entry point of the React application.
 * Wraps the entire app with Redux Provider to give all components access to the global state.
 */

// React core library
import { StrictMode } from 'react'
// React DOM for rendering the app to the browser
import { createRoot } from 'react-dom/client'
import './index.css'
// Main App component with all routes
import App from './App.jsx'
// Redux Provider to connect Redux store to React components
import { Provider } from 'react-redux'
// Centralized Redux store that holds all app state (auth, cart, products, etc.)
import store from './store/reducers/store.js'

// Render the app inside the 'root' div in index.html
createRoot(document.getElementById('root')).render(
  // Provider makes Redux store available to all components
  <Provider store={store}>
        <App />
  </Provider>,
)
