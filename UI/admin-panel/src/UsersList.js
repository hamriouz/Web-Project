import React, { useState } from 'react';

const UserList = () => {
  const [users, setUsers] = useState([
    { id: 1, username: 'Dutchman', registered: '2024-01-01', isActive: true },
    { id: 2, username: 'Hamraz', registered: '2024-02-01', isActive: false },
  ]);

  const toggleActiveStatus = (id) => {
    setUsers(users.map(user =>
      user.id === id ? { ...user, isActive: !user.isActive } : user
    ));
  };

  return (
    <div>
      <h2>User List</h2>
      <table>
        <thead>
          <tr>
            <th>Username</th>
            <th>Registration Date</th>
            <th>Active Status</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.id}>
              <td>{user.username}</td>
              <td>{user.registered}</td>
              <td>
                <button onClick={() => toggleActiveStatus(user.id)}>
                  {user.isActive ? 'Deactivate' : 'Activate'}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserList;
