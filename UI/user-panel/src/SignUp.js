import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './SignUp.css';

const Signup = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [signupSuccess, setSignupSuccess] = useState(false);
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const response = await axios({
        method: 'post',
        url: 'http://localhost:8181/api/web/users/register',
        headers: {'Content-Type': 'application/json'}, 
        data: {
          "name": username,
          "password": password
        }
      });

      if (response.status === 200) {
        setSignupSuccess(true);
        setError('');
        setUsername('');
        setPassword('');
      } else {
        setSignupSuccess(false);
        setError('Signup failed. Please try again.');
      }
    } catch (error) {
      console.error('Signup error:', error);
      setSignupSuccess(false);
      if(error.response){
        setError('User with the given username already exists!');
      }else{
        setError('Signup failed. Please try again.');
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleGoToLogin = () => {
    navigate('/login');
  };

  return (
    <div className="container">
      <h1>User Panel</h1>
      <h2>Register</h2>
      {!signupSuccess ? (
        <div>
          <form onSubmit={handleSignup}>
            <input
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <button type="submit" disabled={isLoading}>{isLoading ? 'Signing up...' : 'Signup'}</button>
          </form>
          {error && <p className="error-message">{error}</p>}
        </div>
      ) : (
        <p className="success-message">Signup successful!</p>
      )}
      <button onClick={handleGoToLogin} className="login-button">Go to Login</button>
    </div>
  );
};

export default Signup;
