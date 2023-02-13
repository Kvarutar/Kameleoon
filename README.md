# Kameleoon
In this project i use jwt token for authorization mechanism

API roots:
-register: /api/auth/register (JSON must contain name, email and password)
-authentication: /api/auth/authentication (JSON must contain email and password)
-add new quote: /api/quote/new
-get all quotes: /api/quote/all
-update quote: /api/quote/{id}/update
-delete quote: /api/quote/{id}/delete

Without authentication you can access this roots: register, authentication and all quotes
