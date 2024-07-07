import React, { useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import './Login.css';

const Login = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios({
        method: 'post',
        url: 'http://localhost:8181/api/web/users/login',
        headers: { 'Content-Type': 'application/json' }, 
        data: {
          "name": username,
          "password": password
        }
      });
  
      if (response.data.token) {
        const expirationTime = new Date().getTime() + response.data.expiresIn;
        Cookies.set('Authorization', "Bearer " + response.data.token, { expires: new Date(expirationTime) });
        onLogin();
      } else {
        setError('Invalid credentials');
      }
    } catch (error) {
      console.error(error);
      setError('Error logging in. Please try again.');
    }
  };
  
  return (
    <div className="container">
      <h1>Admin Panel</h1>  
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Username:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
      {error && <p className="error-message">{error}</p>}
    </div>
  );
};

export default Login;
