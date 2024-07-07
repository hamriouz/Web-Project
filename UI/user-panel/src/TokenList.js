import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';
import './TokenList.css';

const TokenList = () => {
  const [tokens, setTokens] = useState([]);
  const [newTokenName, setNewTokenName] = useState('');
  const [expirationDate, setExpirationDate] = useState('');
  const [createdToken, setCreatedToken] = useState(null);
  const [error, setError] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize] = useState(2);
  const [totalPages, setTotalPages] = useState(20);

  const options = { 
    weekday: 'long', 
    year: 'numeric', 
    month: 'long', 
    day: 'numeric', 
    hour: 'numeric', 
    minute: 'numeric', 
    second: 'numeric', 
    timeZoneName: 'short' 
  };

  const navigate = useNavigate();
  const token = Cookies.get("Authorization");

  useEffect(() => {
    if (!token) {
      navigate("/");
      return;
    }

    const fetchTokens = async () => {
      try {
        const response = await axios({
          method: 'get',
          url: 'http://localhost:8181/api/web/user/api-tokens/'+Cookies.get("Username"),
          headers: { 'Content-Type': 'application/json', 'Authorization': token },
          params: {
            "page": currentPage,
            "size": pageSize
          },
        });
        setTokens(response.data.tokens || []);
        setTotalPages(response.data.totalPageSize);
      } catch (error) {
        setError('Error fetching tokens');
      }
    };

    fetchTokens();
  }, [token, navigate, currentPage, pageSize]);

  const handleCreateToken = async (e) => {
    e.preventDefault();
    if (!token) {
      navigate("/");
      return;
    }

    try {
      const response = await axios({
        method: 'post',
        url: 'http://localhost:8181/api/web/user/api-tokens',
        headers: { 'Content-Type': 'application/json', 'Authorization': token },
        data: { 
          name: newTokenName,
          expireDate: expirationDate
        }
      });

      setCreatedToken(response.data);
      setNewTokenName('');
      setExpirationDate('');
      setError('');
    } catch (error) {
      setError('Error creating token');
    }
  };

  const handleRevokeToken = async (tokenName) => {
    if (!token) {
      navigate("/");
      return;
    }

    try {
      await axios({
        method: 'delete',
        url: `http://localhost:8181/api/web/user/api-tokens/`+tokenName,
        headers: { 'Content-Type': 'application/json', 'Authorization': token },
      });
      setTokens(tokens.filter(token => token.name !== tokenName));
    } catch (error) {
      setError('Error revoking token');
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 1);
    }

  };

  const handleLogout = () => {
    Cookies.remove("Authorization");
    navigate("/");
  };

  return (
    <div className="table-container">
      <h2>API Tokens</h2>
      <button className="logout-button" onClick={handleLogout}>Logout</button>
      <form className="new-token-section" onSubmit={handleCreateToken}>
        <label htmlFor="newTokenName">New Token Name</label>
        <input
          id="newTokenName"
          type="text"
          value={newTokenName}
          onChange={(e) => setNewTokenName(e.target.value)}
          placeholder="New Token Name"
          required
        />
        <label htmlFor="expirationDate">Expiration Date</label>
        <input
          id="expirationDate"
          type="date"
          value={expirationDate}
          onChange={(e) => setExpirationDate(e.target.value)}
          placeholder="Expiration Date"
          required
        />
        <button type="submit">Create Token</button>
      </form>
      {createdToken && (
        <div className="created-token">
          <h3>Newly Created Token</h3>
          <p><strong>Name:</strong> {createdToken.name}</p>
          <p><strong>Token:</strong> {createdToken.token}</p>
          <p><strong>Expiration Date:</strong> {new Date(createdToken.expireDate).toLocaleString('en-US', options)}</p>
        </div>
      )}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Name</th>
            <th>Token</th>
            <th>Expiration Date</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {tokens.map((token, index) => (
            <tr key={index}>
              <td>{index + 1 + currentPage * pageSize}</td>
              <td>{token.name}</td>
              <td>{token.token}</td>
              <td>{new Date(token.expireDate).toLocaleString('en-US', options)}</td>
              <td>
                <button className="revoke-button" onClick={() => handleRevokeToken(token.name)}>Revoke</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="paging-buttons">
        <button onClick={handlePreviousPage} disabled={currentPage === 0}>Previous</button>
        <span> Page {currentPage + 1} of {totalPages} </span>
        <button onClick={handleNextPage} disabled={currentPage === totalPages - 1}>Next</button>
      </div>
    </div>
  );
};

export default TokenList;
