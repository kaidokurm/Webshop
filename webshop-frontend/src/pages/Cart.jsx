import { useState } from "react";

function Cart() {
  const [cartProducts, setCartProducts] = useState(getCartProducts());

  function getCartProducts() {
    if (localStorage.getItem("cartProducts")) {
      return JSON.parse(localStorage.getItem("cartProducts"));
    } else {
      return [];
    }
  }

  function deleteFromCart(index) {
    cartProducts.splice(index,1);
    localStorage.setItem("cartProducts",JSON.stringify(cartProducts));
    setCartProducts(cartProducts.slice());
  }

  function calculateSumOfCart() {
    let sumOfCart = 0;
    cartProducts.forEach(element => sumOfCart += Number(element.price))
    return sumOfCart;
  }

  function onPay() {
    const authData = JSON.parse(sessionStorage.getItem("authData"));
    const expiration = new Date(authData.expiration);
    let token;
    if (expiration > new Date()) {
      token = authData.token;
    } else {
      sessionStorage.removeItem("authData");
    }

    fetch("http://localhost:8080/payment", {
      method: "POST",
      body: JSON.stringify(cartProducts),
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      }
    }).then(res => res.json())
    .then(body => window.location.href = body.url );
  }

  return (
  <div>{cartProducts.map((element, index) => 
    <div>
      <div>{element.name}</div>
      <div>{element.price}</div>
      <button onClick={() => deleteFromCart(index)}>X</button>
    </div>
    )}
     {  
     cartProducts.length > 0 &&
      <div>
        <div>Kokku: {calculateSumOfCart() } €</div>
        <button onClick={onPay}>Maksma</button>
      </div>
     }
     {  
     cartProducts.length === 0 &&
      <div>
        Ostukorv on tühi
      </div>
     }
    </div>)
}

export default Cart;