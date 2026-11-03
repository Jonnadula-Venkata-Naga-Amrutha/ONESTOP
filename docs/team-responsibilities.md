\# ONESTOP – Team Responsibilities



\## Team Overview



ONESTOP is a four-member project. Each member is assigned a specific development area to ensure organised implementation, independent progress and smooth integration.



\---



\## Member 1 – Frontend \& UI/UX Developer



\*\*Primary Role:\*\* Frontend Development



\*\*Responsibilities:\*\*



\* Design and develop the user interface.

\* Implement responsive layouts and interactive components.

\* Connect frontend pages with backend APIs.

\* Develop the customer-facing pages.

\* Develop admin dashboard user-interface pages.



\*\*Assigned Files:\*\*



```text

frontend/

├── index.html

├── styles.css

├── app.js

├── components/

├── assets/

└── admin/

&#x20;   ├── dashboard.html

&#x20;   ├── verification.html

&#x20;   └── complaints.html

```



\---



\## Member 2 – Java Backend Lead



\*\*Primary Role:\*\* Backend Development and Integration



\*\*Responsibilities:\*\*



\* Develop Java Servlets and backend controllers.

\* Implement user authentication and session management.

\* Implement backend request handling and validation.

\* Develop authentication-related services and utilities.

\* Configure JDBC database connectivity.

\* Integrate frontend, database and AI modules.

\* Coordinate backend architecture and API integration.



\*\*Assigned Files:\*\*



```text

backend/src/main/java/com/onestop/

├── controller/

│   ├── AuthServlet.java

│   ├── ServiceServlet.java

│   ├── BookingServlet.java

│   ├── QuotationServlet.java

│   ├── ReviewServlet.java

│   ├── RecommendationServlet.java

│   ├── SearchServlet.java

│   ├── PriceGuidanceServlet.java

│   ├── AdminServlet.java

│   ├── VerificationServlet.java

│   └── ComplaintServlet.java

├── service/

│   └── AuthService.java

├── config/

│   └── DBConnection.java

└── util/

&#x20;   └── PasswordUtil.java

```



\---



\## Member 3 – Database \& Booking Developer



\*\*Primary Role:\*\* Database Design and Data Management



\*\*Responsibilities:\*\*



\* Design and maintain the MySQL database.

\* Prepare database schema and initial data.

\* Develop Java model classes.

\* Implement Data Access Objects (DAOs).

\* Handle database operations related to services, bookings, quotations and reviews.

\* Coordinate database integration with the backend.



\*\*Assigned Files:\*\*



```text

backend/src/main/java/com/onestop/

├── model/

│   ├── User.java

│   ├── Service.java

│   ├── Booking.java

│   ├── Quotation.java

│   └── Review.java

└── dao/

&#x20;   ├── UserDAO.java

&#x20;   ├── ServiceDAO.java

&#x20;   ├── BookingDAO.java

&#x20;   ├── QuotationDAO.java

&#x20;   ├── AvailabilityDAO.java

&#x20;   └── ReviewDAO.java



database/

├── schema.sql

└── seed.sql

```



\---



\## Member 4 – AI \& Admin Developer



\*\*Primary Role:\*\* Intelligent Features and Administrative Logic



\*\*Responsibilities:\*\*



\* Develop AI-powered service recommendations.

\* Implement smart search functionality.

\* Develop price guidance functionality.

\* Implement administrative business logic.

\* Develop service-provider verification functionality.

\* Implement complaint management functionality.

\* Coordinate AI and admin integration with the backend.



\*\*Assigned Files:\*\*



```text

backend/src/main/java/com/onestop/

├── ai/

│   ├── RecommendationService.java

│   ├── SmartSearchService.java

│   └── PriceGuidanceService.java

└── admin/

&#x20;   ├── AdminService.java

&#x20;   ├── VerificationService.java

&#x20;   └── ComplaintService.java

```



\---



\## Shared Responsibilities



All team members must:



1\. Follow the agreed project architecture.

2\. Communicate before modifying another member's assigned files.

3\. Follow common naming conventions.

4\. Test individual modules before integration.

5\. Maintain updated documentation.

6\. Use GitHub for version control and collaboration.

7\. Coordinate changes to shared interfaces and database contracts.



\## Important Development Guidelines



\* The folder structure represents the proposed initial allocation of responsibilities.

\* API contracts, database relationships and method signatures must be discussed before implementation.

\* Member 2 coordinates overall backend integration.

\* Changes to shared files require team discussion.

\* No member should overwrite another member's implementation without coordination.



\## Current Project Status



\*\*Stage:\*\* Initial repository structure established.



\*\*Next Stage:\*\* Team review, architecture finalisation and module-wise implementation.



