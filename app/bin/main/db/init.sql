-- Drop tables if they exist to start fresh
DROP TABLE IF EXISTS sensor_data;
DROP TABLE IF EXISTS financial_records;
DROP TABLE IF EXISTS schedule_tasks;
DROP TABLE IF EXISTS pre_orders;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS crops;
DROP TABLE IF EXISTS users;

-- users table
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    full_name TEXT NOT NULL,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT DEFAULT 'farmer',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- crops table
CREATE TABLE crops (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    planting_date DATE,
    estimated_harvest_date DATE,
    estimated_quantity REAL,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

-- inventory table
CREATE TABLE inventory (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    crop_id INTEGER NOT NULL,
    quantity REAL NOT NULL,
    unit TEXT DEFAULT 'kg',
    status TEXT DEFAULT 'available',
    FOREIGN KEY(crop_id) REFERENCES crops(id)
);

-- pre_orders table
CREATE TABLE pre_orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    crop_id INTEGER NOT NULL,
    buyer_name TEXT NOT NULL,
    quantity REAL NOT NULL,
    price REAL NOT NULL,
    order_date DATE DEFAULT CURRENT_DATE,
    status TEXT DEFAULT 'pending',
    FOREIGN KEY(crop_id) REFERENCES crops(id)
);

-- schedule_tasks table
CREATE TABLE schedule_tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    crop_id INTEGER NOT NULL,
    activity_type TEXT NOT NULL,
    scheduled_date DATE NOT NULL,
    status TEXT DEFAULT 'pending',
    notes TEXT,
    FOREIGN KEY(crop_id) REFERENCES crops(id)
);

-- financial_records table
CREATE TABLE financial_records (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    type TEXT NOT NULL,
    description TEXT,
    amount REAL NOT NULL,
    record_date DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

-- sensor_data table
CREATE TABLE sensor_data (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    crop_id INTEGER NOT NULL,
    soil_moisture REAL,
    temperature REAL,
    humidity REAL,
    ph REAL,
    recorded_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(crop_id) REFERENCES crops(id)
);
