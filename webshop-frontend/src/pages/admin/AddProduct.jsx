import {useEffect, useRef, useState} from "react";
import {Button} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";

function AddProduct() {
    const nameRef = useRef();
    const priceRef = useRef();
    const imgSrcRef = useRef();
    const descriptionRef = useRef();
    const stockRef = useRef();
    const activeRef = useRef();
    const categoryRef = useRef();
    const navigation = useNavigate();
    const [errorMessage, setErrorMessage] = useState("");
    const [categories, setCategories] = useState([]);
    const [isAllOk, setAllOk] = useState(false);

    const authData = JSON.parse(sessionStorage.getItem("authData"));
    const expiration = new Date(authData.expiration);
    let token;
    if (expiration > new Date()) {
        token = authData.token;
    } else {
        sessionStorage.removeItem("authData");
    }

    useEffect(() => {
        fetch("http://localhost:8080/category", {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }).then(res => res.json())
            .then(body => setCategories(body));
    }, [token]);

    function addNewProduct() {
        const newProduct = {
            name: nameRef.current.value,
            price: priceRef.current.value,
            imgSrc: imgSrcRef.current.value,
            description: descriptionRef.current.value,
            stock: stockRef.current.value,
            active: activeRef.current.checked,
            category: {id: categoryRef.current.value},
        }

        fetch("http://localhost:8080/products",
            {
                method: "POST",
                body: JSON.stringify(newProduct),
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                    // "Authorization": "Bearer " + token
                }
            }
        ).then(res => {
            if (res.status === 201) {
                setErrorMessage("");
                navigation("/admin/halda-tooted")
            } else {
                console.log(res);
                setErrorMessage("Nõutud väljad on täitmata");
                throw Error();
            }
        })
    }

    const [selectedCategories, setSubcategories] = useState([]);

    function parentCategoryChanged(event) {
        const selectedCategory = event.target.value;
        const subcategories = JSON.parse(selectedCategory).subcategories;
        setSubcategories(subcategories);
    }

    function checkIfAllOk() {
        if (nameRef.current.value !== "" &&
            priceRef.current.value !== "" &&
            categoryRef.current.value !== "") {
            setAllOk(true);
        } else {
            setAllOk(false);
        }
    }

    return (
        <div>
            <Link to="/admin">
                <Button>Tagasi</Button>
            </Link> <br/>
            <div>{errorMessage}</div>
            <label>Nimi*</label> <br/>
            <input onChange={checkIfAllOk} ref={nameRef} type="text"/> <br/>
            <label>Hind*</label> <br/>
            <input onChange={checkIfAllOk} ref={priceRef} type="number"/> <br/>
            <label>Vali ülemkategooria*</label> <br/>
            {/* <input ref={categoryRef} type="number" /> <br /> */}

            <select onChange={parentCategoryChanged}>
                <option value="" disabled selected>Vali ülemkategooria</option>
                {categories.map(element => <option value={JSON.stringify(element)}>{element.name}</option>)}
            </select> <br/>

            {selectedCategories.length > 0 &&
                <select onChange={checkIfAllOk} ref={categoryRef}>
                    <option value="" disabled selected>Vali tootele kategooria</option>
                    {selectedCategories.map(element => <option value={element.id}>{element.name}</option>)}
                </select>} <br/>

            <label htmlFor=""></label>
            <label>Pildi aadress</label> <br/>
            <input ref={imgSrcRef} type="text"/> <br/>
            <label>Kirjeldus</label> <br/>
            <input ref={descriptionRef} type="text"/> <br/>
            <label>Laoseis</label> <br/>
            <input ref={stockRef} type="number"/> <br/>
            <label>Aktiivne</label> <br/>
            <input ref={activeRef} type="checkbox"/> <br/>
            <Button disabled={!isAllOk} onClick={addNewProduct} variant="success">Sisesta uus toode</Button>
        </div>)
}

export default AddProduct;