import React, { useState, useEffect } from 'react';
import Login from './Login';
import UserList from './UserList';
import axios from 'axios';
import './App.css';
import Cookies from 'js-cookie';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = Cookies.get("Authorization");
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  return (
    <div className="App">
      {
      !isLoggedIn ? (<Login onLogin={() => setIsLoggedIn(true)} />) : (<UserList />)
      }
    </div>
  );
}

export default App;