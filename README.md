# webshop-backend-examinationproject
My examination project for the back-end course of novi university

ENDPOINTS WEBSHOP END EXAMINATION PROJECT WILLIAM MEESTER

*****PRODUCT*******

@POST
post product
http://localhost:8080/product

{
	"productName": "String",  <--- there can be only one productName!
	"category": "String",
	"sellingPrice": double,
	"retailPrice": double

}

@GET
get product by name
http://localhost:8080/product/naam/{productName}

get product by category
http://localhost:8080/product/category/{category}

get product in price range
http://localhost:8080/product/{minimumprice}/{maximumprice}

@PUT
http://localhost:8080/product/change/{id}/{type}

for type u can fill in what value of what property-type you want to change.
Let's say you want to change the category of the product with id=1 you type.
http://localhost:8080/product/change/1/category

{
	"category": "another category"
}

@DELETE
http://localhost:8080/{id}
deletes a product
__________________________________________________________________________________

*****CUSTOMER*****
@POST
http://localhost:8080/customer

{
	"username": "String",   <-- if null the user will be identified as a guest
	"password": "String",   <-- if null the user will be identified as a guest
	"emailAddress": "String",
	"firstName": "String",
	"lastName": "String",
	"streetName": "String",
	"houseNumber": int,
	"additianalToHouseNumber": "String",    <--- can be null
	"city": "String",
	"zipcode": "String"
}

@GET
still to be made!
___________________________________________________________________________________

*****SHOPPINGCART*****
if (customer == null) { make the shoppingCart in the front-end you can post this shoppingCart then to the order entity }

@POST
http://localhost:8080/customerId

{
*The body is empty! This shoppingCart will be created if the user makes an account*
}

@PUT
http://localhost:8080/id={shoppingCartId}/productId={productId}

{
 	"amountOfOrderedProducts": int
}
______________________________________________________________________________________
*****ORDERS*****
@POST
http://localhost:8080/order/customer={customerId}
this posts an order in the database. No body is required.
The shoppingcart inside of the customer entity will be added to the order.
Also with the current order date and the ordertime in mili seconds will be added.

@POST
http://localhost:8080/order/guest={customerId}

[
  {
    "productList": [
      {
		"id": Long
      },
	{
		"id": Long
	},
	{
		product3
	}
    ],
  }
] 

You basically post the shoppingCart from the front-end instead of the back-end 
this makes sure that users don't have to be logged in to purchase items.
If guests would be able to use a shoppingcart in the backend the app would be vulnerable for a ddos attacks.


@GET
http://localhost:8080/order/get-by-id/{id}
gets the order by orderId


@GET
http://localhost:8080/order/processed-status={processed}
With this endpoint you get a list of orders that still need to be processed or orders that are already processed.
This makes it easy to see which orders still need to be prepared and send and which ones are already finished.
The {processed} parameter can be filled with a boolean arguement.

http://localhost:8080/order/processed-status=true
Gets all already finished orders

http://localhost:8080/order/processed-status=false
Gets all orders that aren't prepared yet.

@GET
http://localhost:8080/order/{firstName}/{lastName}/{zipcode}/{houseNumber}/
Gets orders by address

@GET
http://localhost:8080/order/{firstName}/{lastName}/{zipcode}/{houseNumber}/{additionalHouseNumber}
Gets orders by address if there is an addiationalHouseNumber like 2A or B.

@PutMapping
http://localhost:8080/change-processed-status={processed}/id={id}
This changes the processed status! At {processed} fill in true or false.

_____________________________________________________________________________________________________
*****RETURNCART*****
This class makes it possible to return ordered products within the first 30 days after confirming the order.

@POST
http://localhost:8080/returncart/{orderId}
This creates a returnCart! this return-cart can only be created if the order was confirmed within the last 30 days!

@POST
http://localhost:8080/returncart/id={returnCartId}/productid={productId}
{
	"amountOfReturningProducts": int
}

This endpoint adds products to the return cart! This can only be products that are in the order list (and if their are enough products in the order list
you cant return more products then you ordered ofcourse).
When you add products the amountOfOrderedProducts goes down by the amount of products the user wants to return.
Add all products a user want to return from the front-end!

@GET
http://localhost:8080/returncart/{firstName}/{lastName}/{zipcode}/{houseNumber}/
gets return carts by address.

@GET
http://localhost:8080/returncart/{firstName}/{lastName}/{zipcode}/{houseNumber}/{additionalHouseNumber}
gets return carts by address if there is an additional house number!

@GET
http://localhost:8080/returncart/processed-status={processed}
This works the same as the endpoint in the order controller.

@GET
http://localhost:8080/change-processed-status={processed}/id={id}
This endpoint works the same as the one in the order controller.

_______________________________________________________________________________________________________________
*****USER***** going to make 2 seperate controllers ADMIN and EMPLOYEE later!

@POST
http://localhost:8080/user/create-employee
{
	"username": String,
	"password": String,
	"emailaddress": String,
	"firstName": String,
	"lastName": String
} this endpoint creates an employee account (only an admin will be able to do this.


@POST
http://localhost:8080/user/create-admin
{
	"username": String,
	"password": String,
	"emailaddress": String
} this endpoint creates an admin account but is only for test purposes. When the app is finished I will implement
the admin acc directly in the database! Their can also be only 1 admin account which the owners of a store will use.


@PUT THIS ENDPOINT IS NOT READY YET!

http://localhost:8080/divide-shoppingcarts-employees
This endpoint will divide all still to be processed orders to all employees so they can prepare them.
Let's say the shop has 200 orders and 10 workers. This endpoint makes sure that every worker will get 20 orders in their
unProcessedOrderList.
They will be able to change the processed status and when they do that the order will be send back to the admins completed Orders List.
And will be added to the workers completed Order list.
