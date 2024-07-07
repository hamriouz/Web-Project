import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import './UserList.css';

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(2);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchUsers(page, size);
  }, [page, size]);

  const fetchUsers = async (page, size) => {
    try {
      const token = Cookies.get("Authorization");
      const response = await axios({
        method: 'get',
        url: 'http://localhost:8181/api/web/admin/users',
        headers: { 'Authorization': token, 'Content-Type': 'application/json' },
        params: {
          "page": page,
          "size": size
        }
      });
      const data = response.data;
      console.log(data);
      setUsers(data.users);
      setTotalPages(data.totalPageSize);
    } catch (error) {
      console.error('Error fetching users', error);
    }
  };

  const handleToggleActive = async (username, currentStatus) => {
    try {
      const token = Cookies.get("Authorization");
      await axios({
        method: 'put',
        url: `http://localhost:8181/api/web/admin/users`,
        headers: { 'Authorization': token, 'Content-Type': 'application/json' },
        params: {
          "active": !currentStatus,
          "username": username
        }
      });
      setUsers(users.map(user => 
        user.name === username ? { ...user, active: !currentStatus } : user
      ));
    } catch (error) {
      console.error('Error toggling user active status', error);
    }
  };

  const handlePreviousPage = () => {
    if (page > 0) {
      setPage(page - 1);
    }
  };

  const handleNextPage = () => {
    if (page < totalPages - 1) {
      setPage(page + 1);
    }
  };

  const formatDate = (dateString) => {
    console.log(dateString);
    if (!dateString) return 'N/A';
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const date = new Date(dateString.replace(' ', 'T'));
    return date.toLocaleDateString(undefined, options);
  };

  return (
    <div className="container">
      <h2>User List</h2>
      <table>
        <thead>
          <tr>
            <th>Username</th>
            <th>Type</th>
            <th>Date of Register</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.name}>
              <td>{user.name}</td>
              <td>{user.type}</td>
              <td>{formatDate(user.createdDate)}</td>
              <td>
                <button
                  className={user.active ? 'deactivate-button' : 'activate-button'}
                  onClick={() => handleToggleActive(user.name, user.active)}
                >
                  {user.active ? 'Deactivate' : 'Activate'}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pagination">
        <button onClick={handlePreviousPage} disabled={page === 0}>Previous</button>
        <span> Page {page + 1} of {totalPages} </span>
        <button onClick={handleNextPage} disabled={page === totalPages - 1}>Next</button>
      </div>
    </div>
  );
};

export default UserList;
