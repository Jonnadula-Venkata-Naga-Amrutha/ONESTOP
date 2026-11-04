-- ONESTOP DATABASE

CREATE DATABASE IF NOT EXISTS onestop_db;

USE onestop_db;


-- 1. USERS TABLE

CREATE TABLE IF NOT EXISTS users (
    user_id INT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('CUSTOMER', 'PROVIDER', 'ADMIN') NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id),
    UNIQUE KEY email (email),
    UNIQUE KEY phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 2. PROVIDER PROFILES TABLE

CREATE TABLE IF NOT EXISTS provider_profiles (
    provider_id INT NOT NULL,
    business_name VARCHAR(150) DEFAULT NULL,
    experience_years INT DEFAULT 0,
    description TEXT,
    verification_status ENUM('PENDING', 'VERIFIED', 'REJECTED')
        DEFAULT 'PENDING',
    average_rating DECIMAL(2,1) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (provider_id),

    FOREIGN KEY (provider_id)
        REFERENCES users(user_id)
);


-- 3. SERVICE CATEGORIES TABLE

CREATE TABLE IF NOT EXISTS service_categories (
    category_id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL,
    description TEXT,

    PRIMARY KEY (category_id),
    UNIQUE KEY category_name (category_name)
);


-- 4. PROVIDER SERVICES TABLE

CREATE TABLE IF NOT EXISTS provider_services (
    service_id INT NOT NULL AUTO_INCREMENT,
    provider_id INT NOT NULL,
    category_id INT NOT NULL,
    service_name VARCHAR(150) NOT NULL,
    description TEXT,
    base_price DECIMAL(10,2) NOT NULL,
    service_status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (service_id),
    KEY provider_id (provider_id),
    KEY category_id (category_id),

    FOREIGN KEY (provider_id)
        REFERENCES provider_profiles(provider_id),

    FOREIGN KEY (category_id)
        REFERENCES service_categories(category_id)
);


-- 5. PROVIDER AVAILABILITY TABLE

CREATE TABLE IF NOT EXISTS provider_availability (
    availability_id INT NOT NULL AUTO_INCREMENT,
    provider_id INT NOT NULL,
    available_day ENUM(
        'MONDAY', 'TUESDAY', 'WEDNESDAY',
        'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'
    ) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_available TINYINT(1) DEFAULT 1,

    PRIMARY KEY (availability_id),
    KEY provider_id (provider_id),

    FOREIGN KEY (provider_id)
        REFERENCES provider_profiles(provider_id)
);

-- 6. SERVICE AREAS TABLE

CREATE TABLE IF NOT EXISTS service_areas (
    area_id INT NOT NULL AUTO_INCREMENT,
    provider_id INT NOT NULL,
    city VARCHAR(100) NOT NULL,
    locality VARCHAR(150) DEFAULT NULL,
    pincode VARCHAR(10) DEFAULT NULL,

    PRIMARY KEY (area_id),
    KEY provider_id (provider_id),

    FOREIGN KEY (provider_id)
        REFERENCES provider_profiles(provider_id)
);

-- 7. BOOKINGS TABLE

CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    provider_id INT NOT NULL,
    service_id INT NOT NULL,
    service_address VARCHAR(255) NOT NULL,
    service_date DATE NOT NULL,
    preferred_time TIME NOT NULL,
    problem_description TEXT,
    booking_status ENUM(
        'PENDING', 'ACCEPTED', 'REJECTED',
        'COMPLETED', 'CANCELLED'
    ) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (booking_id),
    KEY customer_id (customer_id),
    KEY provider_id (provider_id),
    KEY service_id (service_id),

    FOREIGN KEY (customer_id) REFERENCES users(user_id),
    FOREIGN KEY (provider_id) REFERENCES provider_profiles(provider_id),
    FOREIGN KEY (service_id) REFERENCES provider_services(service_id)
);

-- 8. QUOTATIONS TABLE

CREATE TABLE IF NOT EXISTS quotations (
    quotation_id INT NOT NULL AUTO_INCREMENT,
    booking_id INT NOT NULL,
    provider_id INT NOT NULL,
    quoted_price DECIMAL(10,2) NOT NULL,
    message TEXT,
    quotation_status ENUM('PENDING', 'ACCEPTED', 'REJECTED')
        DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (quotation_id),
    KEY booking_id (booking_id),
    KEY provider_id (provider_id),

    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
    FOREIGN KEY (provider_id) REFERENCES provider_profiles(provider_id)
);

-- 9. REVIEWS TABLE

CREATE TABLE IF NOT EXISTS reviews (
    review_id INT NOT NULL AUTO_INCREMENT,
    booking_id INT NOT NULL,
    customer_id INT NOT NULL,
    provider_id INT NOT NULL,
    rating INT NOT NULL,
    review_comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (review_id),
    UNIQUE KEY booking_id (booking_id),
    KEY customer_id (customer_id),
    KEY provider_id (provider_id),

    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
    FOREIGN KEY (customer_id) REFERENCES users(user_id),
    FOREIGN KEY (provider_id) REFERENCES provider_profiles(provider_id),

    CHECK (rating BETWEEN 1 AND 5)
);

-- 10. VERIFICATION REQUESTS TABLE

CREATE TABLE IF NOT EXISTS verification_requests (
    verification_id INT NOT NULL AUTO_INCREMENT,
    provider_id INT NOT NULL,
    document_type VARCHAR(100) NOT NULL,
    document_path VARCHAR(255) NOT NULL,
    verification_status ENUM('PENDING', 'APPROVED', 'REJECTED')
        DEFAULT 'PENDING',
    admin_remarks TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reviewed_at TIMESTAMP NULL,

    PRIMARY KEY (verification_id),
    KEY provider_id (provider_id),

    FOREIGN KEY (provider_id)
        REFERENCES provider_profiles(provider_id)
);

-- 11. COMPLAINTS TABLE

CREATE TABLE IF NOT EXISTS complaints (
    complaint_id INT NOT NULL AUTO_INCREMENT,
    booking_id INT NOT NULL,
    customer_id INT NOT NULL,
    provider_id INT NOT NULL,
    complaint_description TEXT NOT NULL,
    complaint_status ENUM(
        'OPEN', 'IN_PROGRESS', 'RESOLVED', 'REJECTED'
    ) DEFAULT 'OPEN',
    admin_remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (complaint_id),
    KEY booking_id (booking_id),
    KEY customer_id (customer_id),
    KEY provider_id (provider_id),

    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
    FOREIGN KEY (customer_id) REFERENCES users(user_id),
    FOREIGN KEY (provider_id) REFERENCES provider_profiles(provider_id)
);

-- 12. BOOKING STATUS HISTORY TABLE

CREATE TABLE IF NOT EXISTS booking_status_history (
    history_id INT NOT NULL AUTO_INCREMENT,
    booking_id INT NOT NULL,
    old_status VARCHAR(20),
    new_status VARCHAR(20) NOT NULL,
    changed_by INT NOT NULL,
    remarks TEXT,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (history_id),

    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
    FOREIGN KEY (changed_by) REFERENCES users(user_id)
);