import { useEffect, useState } from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';
import { Link, Route, Routes, useNavigate } from 'react-router-dom';
import './App.css';
import AddProduct from './pages/admin/AddProduct';
import Admin from './pages/admin/Admin';
import Cart from './pages/Cart';
import EditProduct from './pages/admin/EditProduct';
import Login from './pages/Login';
import MainPage from './pages/MainPage';
import MaintainProducts from './pages/admin/MaintainProducts';
import Orders from './pages/Orders';
import Signup from './pages/Signup';
import AddAdmin from './pages/admin/AddAdmin';
import MaintainAdmins from './pages/admin/MaintainAdmins';


function App() {
  const [token, setToken] = useState(null);
  const navigate = useNavigate();
  // useContext();

  useEffect(() => {
    if (sessionStorage.getItem("authData")) {
      const authData = JSON.parse(sessionStorage.getItem("authData"));
      const expiration = new Date(authData.expiration);
      if (expiration > new Date()) {
        // token = authData.token;
        setToken(authData.token);
      } else {
        sessionStorage.removeItem("authData");
      }
    }
  },[]);
  

  function logout() {
    sessionStorage.removeItem("authData");
    setToken(null);
    navigate("/");
  }

  return (
    <div className="App">
        <Navbar bg="dark" variant="dark">
          <Container>
          <Navbar.Brand as={Link} to="/">Pood</Navbar.Brand>
          <Nav className="me-auto">
 { token && <Nav.Link as={Link} to="/admin">Adminstraatori vaatesse</Nav.Link>}
 { token && <Nav.Link as={Link} to="/tellimused">Vaata oma tellimusi</Nav.Link>}
            <Nav.Link as={Link} to="/ostukorv">Ostukorvi</Nav.Link>
 { !token && <Nav.Link as={Link} to="/logi-sisse">Logi sisse</Nav.Link>}
 { !token && <Nav.Link as={Link} to="/registreeru">Registreeru</Nav.Link>}
 { token && <Nav.Link onClick={() => logout()}>Logi välja</Nav.Link>}
          </Nav>
          </Container>
        </Navbar>
        <Routes>
          {/* localhost:3000/ --> Avaleht*/}
          <Route path='' exact element={ <MainPage /> } />
          <Route path='ostukorv' exact element={ <Cart /> } />
          <Route path='logi-sisse' exact element={ <Login /> } />
          <Route path='registreeru' exact element={ <Signup /> } />
      { token && 
          <Route> 
            <Route path='tellimused' exact element={ <Orders /> } />
            <Route path='admin' exact element={ <Admin /> } />
            <Route path='admin/lisa-toode' exact element={ <AddProduct /> } />
            <Route path='admin/halda-tooted' exact element={ <MaintainProducts /> } />
            <Route path='admin/lisa-admin' exact element={ <AddAdmin /> } />
            <Route path='admin/halda-admine' exact element={ < MaintainAdmins /> } />
            <Route path='admin/muuda-toode/:id' exact element={ <EditProduct /> } />
          </Route>
          }
      { !token && <Route path='admin/*' exact element={ <Login /> } />}
          <Route path="*" exact element={<div>404 Not Found</div>} />
        </Routes>
    </div>
  );
}

export default App;
