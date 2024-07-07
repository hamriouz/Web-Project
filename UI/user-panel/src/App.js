import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from './Layout';
import Signup from './SignUp';
import Login from './Login';
import TokenList from './TokenList';


const App = () => {
  return (
    <BrowserRouter>
    <Routes>
    <Route path="/" element={<Layout />} />
      <Route path="/signup" element={<Signup />} />
      <Route path="/login" element={<Login />} />
      <Route path="/user-panel" element={<TokenList />} />
    </Routes>
    </BrowserRouter>
  );
};

export default App;
