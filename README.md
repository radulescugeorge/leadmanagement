REST API Endpoints


CUSTOMERS:

GET:	/api/customers
GET: 	/api/customers/{id}
POST:	/api/customers 
PATCH: 	/api/customers/{id} -- (update)
PUT:	/api/customers/{id} -- (replace)
DELETE:	/api/customers/{id}



LEAD

GET: 	/api/leads
GET: 	/api/leads/{id}
POST: 	/api/leads
PUT: 	/api/leads/{id} -- (replace)
PATCH:	/api/leads/{id} -- (update)
DELETE:	/api/leads/{id}
GET:	/api/leads/salesagent/{salesAgentId}
GET:	/api/leads/customer/{customerId}
GET:	/api/leads/product/{productId}

PRODUCTS

GET:	/api/products
GET:	/api/products/{id}
POST:	/api/products
PUT:	/api/products/{id} --(replace)
PATCH:	/api/products/{id} -- (update)
DELETE:	/api/products/{id}

SALES AGENT

GET: 	/api/salesagents
GET:	/api/salesagents/{id}
POST:	/api/salesagents
PUT:	/api/salesagents/{id} -- (replace)
PATCH:	/api/salesagents/{id} -- (update)
DELETE:	/api/salesagents/{id}
