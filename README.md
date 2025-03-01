REST API Endpoints


CUSTOMERS:

GET:	/api/customers
GET: 	/api/customers/{id}
POST:	/api/customers 
PATCH: 	/api/customers/update/{id}
PUT:	/api/customers/replace/{id}
DELETE:	/api/customers/delete/{id}



LEAD

GET: 	/api/leads
GET: 	/api/leads/{id}
POST: 	/api/leads/create
PUT: 	/api/leads/replace/{id}
PATCH:	/api/leads/update/{id}
DELETE:	/api/leads/delete/{id}
GET:	/api/leads/salesagent/{salesAgentId}
GET:	/api/leads/customer/{customerId}
GET:	/api/leads/product/{productId}

PRODUCTS

GET:	/api/products
GET:	/api/products/{id}
POST:	/api/products
PUT:	/api/products/replace/{id}
PATCH:	/api/products/update/{id}
DELETE:	/api/products/delete{id}

SALES AGENT

GET: 	/api/salesagent
GET:	/api/salesagent/{id}
POST:	/api/salesagent
PUT:	/api/salesagent/replace/{id}
PATCH:	/api/salesagent/update/{id}
DELETE:	/api/salesagent/delete/{id}
