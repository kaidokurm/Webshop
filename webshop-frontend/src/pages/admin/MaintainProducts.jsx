import {useEffect, useState} from "react"; // use algusega funktsioonid on Reacti HOOK-id
import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";

function MaintainProducts() {
    const baseUrl = "http://localhost:8080";
    const [products, setProducts] = useState([]);

    const authData = JSON.parse(sessionStorage.getItem("authData"));
    const expiration = new Date(authData.expiration);
    let token;
    if (expiration > new Date()) {
        token = authData.token;
    } else {
        sessionStorage.removeItem("authData");
    }


    useEffect(() => {
        fetch(baseUrl + "/products").then(res => res.json())
            .then(body => setProducts(body));
    }, []);

    if (token === null) {
        return (<div>Sul ei ole õigust seda lehte vaadata</div>)
    }

    function decreaseQuantity(productClicked) {
        fetch(baseUrl + "/decrease-stock", {
            method: "POST",
            body: JSON.stringify(productClicked),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }).then(res => res.json())
            .then(body => setProducts(body));
    }

    function increaseQuantity(productClicked) {
        fetch(baseUrl + "/increase-stock", {
            method: "POST",
            body: JSON.stringify(productClicked),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        }).then(res => res.json())
            .then(body => setProducts(body));
    }

    function deleteProduct(productClicked) {
        fetch(baseUrl + "/products/" + productClicked.id, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + token
            }
        }).then(res => res.json())
            .then(body => setProducts(body));
    }

    return (
        <div>
            <Link to="/admin">
                <Button>Tagasi</Button>
            </Link>
            <table className="table table-hover table-bordered">
                <tr>
                    <th>Nimi</th>
                    <th>Hind</th>
                    <th>Kirjeldus</th>
                    <th>Kogus</th>
                    <th>Pilt</th>
                    <th>Tegevused</th>
                </tr>
                {products.map(element =>
                    <tr>
                        <td>{element.name}</td>
                        <td>{element.price} €</td>
                        <td>{element.description}</td>
                        <td>{element.stock} tk</td>
                        <td><img className="product-img" src={element.imgSrc} alt=""/></td>
                        <td>
                            <Button onClick={() => decreaseQuantity(element)} variant="danger">-</Button>
                            <Button onClick={() => increaseQuantity(element)} variant="success">+</Button>
                            <Link to={"/admin/muuda-toode/" + element.id}>
                                <button>Muuda</button>
                            </Link>
                            <Button onClick={() => deleteProduct(element)}>X</Button>
                        </td>
                    </tr>)}
            </table>
        </div>)
}

export default MaintainProducts;