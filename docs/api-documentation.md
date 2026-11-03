\# ONESTOP – API Documentation



\## 1. Overview



This document describes the proposed API structure for the ONESTOP Smart Local Services Platform.



The frontend communicates with the Java backend through HTTP requests handled by Jakarta Servlets.



The backend processes requests, coordinates business logic and interacts with the MySQL database through JDBC and DAO classes.



\*\*Important:\*\* The endpoints listed below are proposed contracts. They must be reviewed and finalised by the team before implementation.



\## 2. API Base Path



Proposed base path:



`/ONESTOP/api`



The final deployment context path may change depending on the deployment configuration.



\## 3. API Communication



\* Protocol: HTTP/HTTPS

\* Data format: JSON

\* Frontend communication: JavaScript `fetch()`

\* Backend technology: Jakarta Servlets

\* Request methods: GET, POST, PUT, DELETE, as appropriate.



\## 4. Authentication APIs



\*\*Owner:\*\* Member 2 – Java Backend Lead



| Method | Proposed Endpoint | Purpose                         |

| ------ | ----------------- | ------------------------------- |

| POST   | `/auth/register`  | Register a new user             |

| POST   | `/auth/login`     | Authenticate a user             |

| POST   | `/auth/logout`    | End the current session         |

| GET    | `/auth/session`   | Check the current login session |



Related files:



\* AuthServlet.java

\* AuthService.java

\* UserDAO.java

\* User.java



\## 5. Service APIs



\*\*Backend Owner:\*\* Member 2

\*\*Database Support:\*\* Member 3



| Method | Proposed Endpoint | Purpose                     |

| ------ | ----------------- | --------------------------- |

| GET    | `/services`       | Retrieve available services |

| GET    | `/services/{id}`  | Retrieve a specific service |

| POST   | `/services`       | Create a service            |

| PUT    | `/services/{id}`  | Update a service            |

| DELETE | `/services/{id}`  | Remove a service            |



Related files:



\* ServiceServlet.java

\* Service.java

\* ServiceDAO.java



\## 6. Booking APIs



\*\*Backend Owner:\*\* Member 2

\*\*Database Support:\*\* Member 3



| Method | Proposed Endpoint | Purpose                                      |

| ------ | ----------------- | -------------------------------------------- |

| POST   | `/bookings`       | Create a booking                             |

| GET    | `/bookings/{id}`  | Retrieve booking details                     |

| GET    | `/bookings`       | Retrieve bookings for the authenticated user |

| PUT    | `/bookings/{id}`  | Update booking details or status             |

| DELETE | `/bookings/{id}`  | Cancel a booking, subject to business rules  |



Related files:



\* BookingServlet.java

\* Booking.java

\* BookingDAO.java

\* AvailabilityDAO.java



\## 7. Quotation APIs



\*\*Backend Owner:\*\* Member 2

\*\*Database Support:\*\* Member 3



| Method | Proposed Endpoint  | Purpose                                                   |

| ------ | ------------------ | --------------------------------------------------------- |

| POST   | `/quotations`      | Create a quotation                                        |

| GET    | `/quotations/{id}` | Retrieve quotation details                                |

| GET    | `/quotations`      | Retrieve relevant quotations                              |

| PUT    | `/quotations/{id}` | Update quotation information                              |

| DELETE | `/quotations/{id}` | Remove or withdraw a quotation, subject to business rules |



Related files:



\* QuotationServlet.java

\* Quotation.java

\* QuotationDAO.java



\## 8. Review APIs



\*\*Backend Owner:\*\* Member 2

\*\*Database Support:\*\* Member 3



| Method | Proposed Endpoint | Purpose                                         |

| ------ | ----------------- | ----------------------------------------------- |

| POST   | `/reviews`        | Submit a review                                 |

| GET    | `/reviews`        | Retrieve reviews                                |

| GET    | `/reviews/{id}`   | Retrieve a specific review                      |

| PUT    | `/reviews/{id}`   | Update a review                                 |

| DELETE | `/reviews/{id}`   | Delete a review, subject to authorisation rules |



Related files:



\* ReviewServlet.java

\* Review.java

\* ReviewDAO.java



\## 9. AI APIs



\*\*Backend Integration:\*\* Member 2

\*\*AI Business Logic:\*\* Member 4



| Method | Proposed Endpoint     | Purpose                                       |

| ------ | --------------------- | --------------------------------------------- |

| POST   | `/ai/recommendations` | Retrieve personalised service recommendations |

| GET    | `/ai/search`          | Perform intelligent service search            |

| POST   | `/ai/price-guidance`  | Retrieve estimated service price guidance     |



Related files:



\* RecommendationServlet.java

\* SearchServlet.java

\* PriceGuidanceServlet.java

\* RecommendationService.java

\* SmartSearchService.java

\* PriceGuidanceService.java



\## 10. Admin APIs



\*\*Backend Integration:\*\* Member 2

\*\*Admin Business Logic:\*\* Member 4



| Method | Proposed Endpoint           | Purpose                                       |

| ------ | --------------------------- | --------------------------------------------- |

| GET    | `/admin/dashboard`          | Retrieve administrative dashboard information |

| GET    | `/admin/verifications`      | Retrieve pending verification requests        |

| PUT    | `/admin/verifications/{id}` | Update a verification decision                |

| GET    | `/admin/complaints`         | Retrieve complaints                           |

| PUT    | `/admin/complaints/{id}`    | Update complaint status                       |



Related files:



\* AdminServlet.java

\* VerificationServlet.java

\* ComplaintServlet.java

\* AdminService.java

\* VerificationService.java

\* ComplaintService.java



\## 11. Proposed Response Format



Successful response example:



```json

{

&#x20; "success": true,

&#x20; "message": "Request completed successfully",

&#x20; "data": {}

}

```



Error response example:



```json

{

&#x20; "success": false,

&#x20; "message": "An error occurred",

&#x20; "data": null

}

```



These are proposed response formats and may be refined during implementation.



\## 12. Authentication and Authorisation



The following rules are proposed:



\* Registration and login endpoints are publicly accessible.

\* Protected endpoints require an authenticated session.

\* Administrative endpoints require appropriate administrative privileges.

\* Users should only access or modify resources they are authorised to manage.

\* Sensitive information must not be exposed in API responses.



\## 13. API Development Guidelines



1\. Use appropriate HTTP methods.

2\. Validate all incoming request parameters.

3\. Use consistent JSON response structures.

4\. Return suitable HTTP status codes.

5\. Handle exceptions safely.

6\. Enforce authentication and authorisation.

7\. Use prepared statements for database operations.

8\. Coordinate endpoint changes with the frontend developer.



\## 14. Servlet Mapping Strategy



The proposed API base mapping is:



`/api/\*`



Individual Servlet mappings will be configured through annotations or web.xml.



The team must agree on the final mappings before implementation.



\## 15. Current Status



\*\*API Status:\*\* Proposed endpoints documented.



\*\*Pending:\*\*



\* Final endpoint approval.

\* Request and response field definitions.

\* Authentication rules.

\* Servlet mapping confirmation.

\* Frontend and backend integration testing.



