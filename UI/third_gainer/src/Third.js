import React, { useState } from 'react';
import axios from 'axios';
import './Third.css';

const ApiRequest = () => {
  const [apiKey, setApiKey] = useState('');
  const [customUrl, setCustomUrl] = useState('');
  const [method, setMethod] = useState('GET');
  const [response, setResponse] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    setResponse('');

    try {
      const res = await axios({
        method: method.toLowerCase(),
        url: customUrl,
        headers: { 'Authorization': `${apiKey}` },
      });
      setResponse(JSON.stringify(res.data, null, 2));
    } catch (err) {
      if (err.response && err.response.status === 401) {
        setError('Invalid API key. The request was unauthorized.');
      } else {
        setError('An error occurred. Please try again.');
      }
    }
  };

  return (
    <div className="container">
      <h1>API Request Page</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>API Key:</label>
          <input
            type="text"
            value={apiKey}
            onChange={(e) => setApiKey(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Custom URL:</label>
          <input
            type="text"
            value={customUrl}
            onChange={(e) => setCustomUrl(e.target.value)}
            required
          />
        </div>
        <div>
          <label>HTTP Method:</label>
          <select value={method} onChange={(e) => setMethod(e.target.value)}>
            <option value="GET">GET</option>
            <option value="POST">POST</option>
            <option value="PUT">PUT</option>
            <option value="DELETE">DELETE</option>
            <option value="PATCH">PATCH</option>
            <option value="HEAD">HEAD</option>
            <option value="OPTIONS">OPTIONS</option>
          </select>
        </div>
        <button type="submit">Send Request</button>
      </form>
      {error && <p className="error-message">{error}</p>}
      {response && (
        <div className="response">
          <h2>Response:</h2>
          <pre>{response}</pre>
        </div>
      )}
    </div>
  );
};

export default ApiRequest;
