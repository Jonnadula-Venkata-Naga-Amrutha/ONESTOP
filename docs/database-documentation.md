\# ONESTOP – Database Documentation



\## 1. Overview



This document defines the proposed database design and development responsibilities for the ONESTOP Smart Local Services Platform.



The application uses MySQL for persistent data storage and JDBC for communication between the Java backend and the database.



\*\*Database design owner:\*\* Member 3 – Database \& Booking Developer.



\## 2. Database Technology



| Component                  | Technology        |

| -------------------------- | ----------------- |

| Database Management System | MySQL             |

| Database Connectivity      | JDBC              |

| Java Database Driver       | MySQL Connector/J |

| Database Development Tool  | MySQL Workbench   |



\## 3. Proposed Database Modules



The database is expected to support the following modules:



1\. User Management

2\. Service Management

3\. Booking Management

4\. Availability Management

5\. Quotation Management

6\. Review and Rating Management

7\. Service Provider Verification

8\. Complaint Management



The final tables, attributes and relationships must be approved by the team before implementation.



\## 4. Proposed Data Entities



| Entity       | Purpose                                                                  |

| ------------ | ------------------------------------------------------------------------ |

| User         | Stores customer, service-provider and administrative account information |

| Service      | Stores service details and categories                                    |

| Booking      | Stores customer booking information                                      |

| Quotation    | Stores service quotations and pricing information                        |

| Review       | Stores customer feedback and ratings                                     |

| Availability | Stores service-provider availability information                         |

| Verification | Stores service-provider verification information                         |

| Complaint    | Stores complaints and their resolution status                            |



These are proposed entities, not a final database schema. Member 3 should identify the required attributes and relationships in consultation with the team.



\## 5. Java Model Classes



\*\*Owner:\*\* Member 3



Proposed location:



`backend/src/main/java/com/onestop/model/`



| File           | Responsibility                   |

| -------------- | -------------------------------- |

| User.java      | Represents user information      |

| Service.java   | Represents service information   |

| Booking.java   | Represents booking information   |

| Quotation.java | Represents quotation information |

| Review.java    | Represents review information    |



Additional models may be introduced if required by the final database design.



\## 6. Data Access Objects (DAOs)



\*\*Owner:\*\* Member 3



Proposed location:



`backend/src/main/java/com/onestop/dao/`



| File                 | Responsibility                           |

| -------------------- | ---------------------------------------- |

| UserDAO.java         | User-related database operations         |

| ServiceDAO.java      | Service-related database operations      |

| BookingDAO.java      | Booking-related database operations      |

| QuotationDAO.java    | Quotation-related database operations    |

| AvailabilityDAO.java | Availability-related database operations |

| ReviewDAO.java       | Review-related database operations       |



DAOs should encapsulate SQL operations and provide reusable methods for the application.



\## 7. Database Scripts



Location:



`database/`



\### schema.sql



Responsible for:



\* Creating the database tables.

\* Defining primary keys.

\* Defining foreign-key relationships.

\* Defining constraints.

\* Establishing required indexes.



\### seed.sql



Responsible for:



\* Inserting initial development data.

\* Providing sample users and service records.

\* Providing sample booking and quotation records.

\* Supporting application testing.



Seed data must contain fictional development records, not real user information.



\## 8. JDBC Integration



\*\*Backend Integration:\*\* Member 2

\*\*Database Implementation:\*\* Member 3



The Java backend will use JDBC to communicate with MySQL.



Proposed connection class:



`backend/src/main/java/com/onestop/config/DBConnection.java`



The connection configuration must:



\* Use the appropriate JDBC driver.

\* Connect to the agreed database.

\* Handle connection errors safely.

\* Avoid exposing database credentials.

\* Use environment variables or another agreed secure configuration method.



\## 9. Database Integration Workflow



The proposed workflow is:



1\. Member 3 finalises the database schema.

2\. Member 3 implements the model classes.

3\. Member 3 implements the DAO classes.

4\. Member 2 configures and tests JDBC connectivity.

5\. Member 2 integrates DAO operations with Java Servlets and services.

6\. The team performs end-to-end testing.



\## 10. Database Design Guidelines



1\. Use meaningful table and column names.

2\. Define appropriate primary and foreign keys.

3\. Maintain referential integrity.

4\. Avoid unnecessary data duplication.

5\. Use appropriate data types.

6\. Use prepared statements for SQL operations.

7\. Handle database exceptions appropriately.

8\. Avoid hardcoding sensitive credentials.

9\. Document schema changes.

10\. Coordinate changes with the backend developer.



\## 11. Team Coordination Requirements



Before implementation, Member 2 and Member 3 must agree on:



\* User roles and account structure.

\* Model attributes and Java data types.

\* DAO method names and parameters.

\* Return types and exception-handling conventions.

\* Database naming conventions.

\* Transaction-handling requirements.

\* Authentication and session-related database operations.



Member 4 must coordinate any additional database requirements for AI and administrative features.



\## 12. Existing Development Environment



The backend developer has already tested a local MySQL connection using JDBC.



The existing local test database is named `skillbridge`.



\*\*Important:\*\* The final ONESTOP database name has not yet been agreed upon. The existing database must not be renamed or modified without team coordination.



\## 13. Current Status



\*\*Database Status:\*\* Initial database responsibilities and proposed entities documented.



\*\*Pending:\*\*



\* Final database name.

\* ER diagram.

\* Table definitions.

\* Column attributes and data types.

\* Primary and foreign-key relationships.

\* DAO method contracts.

\* Schema and seed script implementation.



