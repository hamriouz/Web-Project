import React from 'react';
import { Outlet, Link } from "react-router-dom";
import './Layout.css';

const Layout = () => {
  return (
    <>
      <nav>
        <ul>
          <li>
            <Link to="/signup">SignUp</Link>
          </li>
          <li>
            <Link to="/login">Login</Link>
          </li>
        </ul>
      </nav>

      <main>
        <h1>Web Project</h1>
        <h2>User Panel</h2>
        <Outlet />
      </main>

      <footer>
        <p>AmirMahdi Kousheshi - Hamraz Arafati</p>
      </footer>
    </>
  )
};

export default Layout;
