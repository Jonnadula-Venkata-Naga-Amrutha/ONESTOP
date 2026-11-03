\# ONESTOP – System Architecture



\## 1. Architecture Overview



ONESTOP follows a layered web application architecture that separates the frontend, backend processing, database operations and intelligent services.



The system uses Java Servlets to handle HTTP requests, JDBC for database connectivity and MySQL for persistent data storage.



\## 2. Technology Stack



| Layer                | Technology                  |

| -------------------- | --------------------------- |

| Presentation Layer   | HTML, CSS, JavaScript       |

| Application Layer    | Core Java, Jakarta Servlets |

| Business Logic Layer | Java Service Classes        |

| Data Access Layer    | JDBC, DAO Classes           |

| Database Layer       | MySQL                       |

| Application Server   | Apache Tomcat 10.1          |



\## 3. High-Level Architecture



```text

&#x20;             USERS

&#x20;               |

&#x20;               v

&#x20;      FRONTEND (Member 1)

&#x20;       HTML + CSS + JS

&#x20;               |

&#x20;               | HTTP Requests

&#x20;               v

&#x20;      JAVA BACKEND (Member 2)

&#x20;         Jakarta Servlets

&#x20;               |

&#x20;      +--------+---------+

&#x20;      |                  |

&#x20;      v                  v

&#x20;BUSINESS LOGIC       AI SERVICES

&#x20;      |                (Member 4)

&#x20;      |                  |

&#x20;      +--------+---------+

&#x20;               |

&#x20;               v

&#x20;         DAO LAYER

&#x20;         (Member 3)

&#x20;               |

&#x20;               | JDBC

&#x20;               v

&#x20;          MYSQL DATABASE

&#x20;            (Member 3)



&#x20;      ADMIN INTERFACE

&#x20;         (Member 1)

&#x20;               |

&#x20;               v

&#x20;      ADMIN SERVLETS

&#x20;         (Member 2)

&#x20;               |

&#x20;               v

&#x20;      ADMIN SERVICES

&#x20;         (Member 4)

&#x20;               |

&#x20;               v

&#x20;         MYSQL DATABASE

```



\*This is the proposed logical architecture. Exact interfaces and dependencies will be finalised collaboratively.\*



\## 4. Architectural Layers



\### 4.1 Presentation Layer – Member 1



Technologies:



\* HTML

\* CSS

\* JavaScript



Responsibilities:



\* Display the user interface.

\* Collect user input.

\* Display services and search results.

\* Handle booking and quotation interactions.

\* Display recommendations and price guidance.

\* Provide administrative interface pages.

\* Communicate with backend endpoints using HTTP requests.



Location:



`frontend/`



\### 4.2 Controller Layer – Member 2



Technology:



\* Jakarta Servlets



Responsibilities:



\* Receive HTTP requests.

\* Process incoming parameters.

\* Perform request validation.

\* Coordinate application services.

\* Return appropriate HTTP responses.

\* Manage authentication and session-related requests.



Location:



`backend/src/main/java/com/onestop/controller/`



\### 4.3 Business Logic Layer



Responsibilities:



\* Implement application-specific operations.

\* Coordinate business rules.

\* Separate business processing from HTTP request handling.

\* Coordinate authentication, AI and administrative functionality.



Proposed locations:



\* `service/`

\* `ai/`

\* `admin/`



Ownership:



\* Member 2: Authentication service.

\* Member 4: AI and administrative services.



\### 4.4 Data Access Layer – Member 3



Technology:



\* JDBC



Responsibilities:



\* Execute database operations.

\* Retrieve and store application data.

\* Handle CRUD operations.

\* Separate SQL operations from business logic.



Location:



`backend/src/main/java/com/onestop/dao/`



\### 4.5 Database Layer – Member 3



Technology:



\* MySQL



Responsibilities:



\* Store user information.

\* Maintain service records.

\* Store booking and quotation information.

\* Maintain reviews and ratings.

\* Support administrative operations.



Location:



`database/`



\## 5. Request and Response Flow



The proposed request flow is:



1\. A user interacts with the frontend.

2\. JavaScript sends an HTTP request to the appropriate Servlet.

3\. The Servlet validates and processes the request.

4\. The relevant business service performs the required operation.

5\. If database access is required, the service or controller coordinates with the appropriate DAO.

6\. The DAO executes SQL operations using JDBC.

7\. MySQL returns the requested result.

8\. The backend sends an HTTP response, preferably in JSON format for frontend API requests.

9\. JavaScript updates the user interface.



\## 6. AI Integration



The AI module provides intelligent features such as:



\* Service recommendations.

\* Smart search.

\* Price guidance.



The backend will coordinate requests to the relevant AI service classes.



Member 4 will define the internal logic and required interfaces in coordination with Member 2.



\## 7. Authentication and Security



The proposed security responsibilities include:



\* User registration and login.

\* Password hashing.

\* Session management.

\* Authentication checks.

\* Input validation.

\* Role-based access control.

\* Protection of sensitive configuration information.



Member 2 coordinates backend security implementation.



\## 8. Frontend and Backend Integration



The frontend communicates with the backend through HTTP endpoints.



The frontend will use JavaScript `fetch()` to send requests and process responses.



The API paths, request formats, response formats and authentication requirements will be documented in `api-documentation.md`.



\## 9. Deployment Structure



The project currently maintains separate top-level `frontend/` and `backend/` directories.



Apache Tomcat serves the deployed web application from its web application directory.



Therefore, the team must agree on a frontend deployment strategy before integration. The frontend files may be packaged or copied into the backend web application's deployment directory during deployment.



The exact deployment procedure will be documented after the team finalises its build and deployment workflow.



\## 10. Team Integration Strategy



| Member   | Module     | Integration Responsibility                            |

| -------- | ---------- | ----------------------------------------------------- |

| Member 1 | Frontend   | Connect UI with backend APIs                          |

| Member 2 | Backend    | Coordinate Servlet endpoints and overall integration  |

| Member 3 | Database   | Provide models, DAOs and database operations          |

| Member 4 | AI \& Admin | Provide intelligent features and administrative logic |



\## 11. Architecture Finalisation



Before full implementation, the team must collectively finalise:



1\. Database schema and relationships.

2\. Java model attributes.

3\. DAO method signatures.

4\. Servlet endpoint mappings.

5\. API request and response formats.

6\. Authentication and session rules.

7\. AI service interfaces.

8\. Frontend deployment strategy.



\## 12. Current Status



\*\*Architecture Status:\*\* Proposed architecture documented.



\*\*Next Stage:\*\* Team review and agreement on technical interfaces before implementation.



