import {Button} from "react-bootstrap";
import {useEffect, useRef, useState} from "react";
import {Link, useParams} from "react-router-dom";

function EditProduct() {
    const nameRef = useRef();
    const priceRef = useRef();
    const imgSrcRef = useRef();
    const descriptionRef = useRef();
    const stockRef = useRef();
    const activeRef = useRef();
    const {id} = useParams();
    const baseUrl = "http://localhost:8080";
    const [product, setProduct] = useState();

    useEffect(() => {
        fetch(baseUrl + "/products/" + id).then(res => res.json())
            .then(body => setProduct(body));
    }, [id]);

    function editProduct() {
        const updatedProduct = {
            id: id,
            name: nameRef.current.value,
            price: priceRef.current.value,
            imgSrc: imgSrcRef.current.value,
            description: descriptionRef.current.value,
            stock: stockRef.current.value,
            active: activeRef.current.checked,
            category: product.category
        }

        const authData = JSON.parse(sessionStorage.getItem("authData"));
        const expiration = new Date(authData.expiration);
        let token;
        if (expiration > new Date()) {
            token = authData.token;
        } else {
            sessionStorage.removeItem("authData");
        }

        fetch("http://localhost:8080/products",
            {
                method: "PUT",
                body: JSON.stringify(updatedProduct),
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            }
        )
    }

    return (
        <div>
            <Link to="/admin">
                <Button>Tagasi</Button>
            </Link> <br/>
            {product && <div>
                <label>Nimi</label> <br/>
                <input ref={nameRef} defaultValue={product.name} type="text"/> <br/>
                <label>Hind</label> <br/>
                <input ref={priceRef} defaultValue={product.price} type="number"/> <br/>
                <label>Pildi aadress</label> <br/>
                <input ref={imgSrcRef} defaultValue={product.imgSrc} type="text"/> <br/>
                <label>Kirjeldus</label> <br/>
                <input ref={descriptionRef} defaultValue={product.description} type="text"/> <br/>
                <label>Laoseis</label> <br/>
                <input ref={stockRef} defaultValue={product.stock} type="number"/> <br/>
                <label>Aktiivne</label> <br/>
                <input ref={activeRef} defaultChecked={product.active} type="checkbox"/> <br/>
                <Button onClick={editProduct} variant="success">Muuda toode</Button>
            </div>}
        </div>)
}

export default EditProduct;