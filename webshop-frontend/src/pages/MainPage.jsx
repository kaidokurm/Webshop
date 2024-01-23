import {useEffect, useState} from "react"; // use algusega funktsioonid on Reacti HOOK-id
import {Button} from "react-bootstrap";

function MainPage() {
    const baseUrl = "http://localhost:8080";
    const [products, setProducts] = useState([]);

    useEffect(() => {
        fetch(baseUrl + "/products").then(res => res.json())
            .then(body => setProducts(body));
    }, []);

    function addToCart(product) {
        let cartProducts = [];
        if (localStorage.getItem("cartProducts") !== null) {

            cartProducts = JSON.parse(localStorage.getItem("cartProducts"));
        }
        cartProducts.push(product);
        localStorage.setItem("cartProducts", JSON.stringify(cartProducts));

    }

    return (
        <div>
            {products.filter(element => element.active).map(element =>
                <div>
                    <div>{element.name}</div>
                    <div>{element.price} €</div>
                    <div><img className="product-img" src={element.imgSrc} alt=""/></div>
                    <div>
                        <Button onClick={() => addToCart(element)}>Lisa ostukorvi</Button>
                    </div>
                </div>)}
        </div>)
}

export default MainPage;