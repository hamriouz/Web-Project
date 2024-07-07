import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const navigate = useNavigate();
  
  useEffect(() => {
    const token = Cookies.get("Authorization");
    if (token) {
      navigate("/user-panel");
    } else{
      const username_cookie = Cookies.get("Username");
      if (username_cookie){
      Cookies.remove("Username");
      }
    }
  }, [navigate]);

  const onLogin = () => {
    navigate("/user-panel");
  };

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
      console.log("hahaha");
  
      if (response.data.token) {
        const expirationTime = new Date().getTime() + response.data.expiresIn;
        Cookies.set('Authorization', "Bearer " + response.data.token, { expires: new Date(expirationTime) });
        Cookies.set('Username', username);
        onLogin();
      } else {
        setError('Invalid credentials');
      }
    } catch (error) {
      console.log("ya hossein");
      console.log(error);
      if (error.response){
        if (error.response.status==401){
          setError('The given username or password is incorrect');
        }
      }
      else{
        setError('Error logging in. Please try again.');
      }
    }
  };
  

  return (
    <div className="container">
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
