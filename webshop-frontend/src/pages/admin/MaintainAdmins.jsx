import { useEffect, useState } from "react"; 
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";

function MaintainAdmins() {
  const baseUrl = "http://localhost:8080";
  const [admins, setAdmins] = useState([]); 
  const [superAdmins, setSuperAdmins] = useState([]); 

  const authData = JSON.parse(sessionStorage.getItem("authData"));
  const expiration = new Date(authData.expiration);
  let token;
  if (expiration > new Date()) {
    token = authData.token;
  } else {
    sessionStorage.removeItem("authData");
  }


  useEffect(()=>{ 
    getAdmins();
  },[]); 

  function getAdmins() {
    fetch(baseUrl + "/get-admins", {
      headers: {
        "Authorization": `Bearer ${token}`
      }
    }).then(res => res.json()) 
    .then(body => setAdmins(body));
  }

  useEffect(()=>{ 
    getSuperAdmins();
  },[]); 

  function getSuperAdmins() {
    fetch(baseUrl + "/get-super-admins", {
      headers: {
        "Authorization": `Bearer ${token}`
      }
    }).then(res => res.json()) 
    .then(body => setSuperAdmins(body));
  }

  if (token === null) {
    return (<div>Sul ei ole õigust seda lehte vaadata</div>)
  }

  function deleteAdmin(adminClicked) {
    fetch(baseUrl + "/delete-role/" + adminClicked.personCode, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${token}`
      }
    }).then(() => {
      getAdmins();
      getSuperAdmins();
    })

  }

  return (
    <div>
       <Link to="/admin">
        <Button>Tagasi</Button>
      </Link>
      <table className="table table-hover table-bordered">
          <tr>
            <th>Isikukood</th>
            <th>Nimi</th>
            <th>Email</th>
            <th>Roll</th>
          </tr>
        { admins.map(element => 
          <tr>
            <td>{element.personCode}</td>
            <td>{element.firstName + element.lastName}</td>
            <td>{element.email}</td>
            <td>Admin</td>
            <td>
              <Button onClick={() => deleteAdmin(element)}>Võta admin õigused ära</Button>
            </td>
          </tr>) } 
        { superAdmins.map(element => 
        <tr>
          <td>{element.personCode}</td>
          <td>{element.firstName + element.lastName}</td>
          <td>{element.email}</td>
          <td>Superadmin</td>
          <td>
            <Button onClick={() => deleteAdmin(element)}>Võta admin õigused ära</Button>
          </td>
        </tr>) } 
      </table>  
    </div>)
}

export default MaintainAdmins;