
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;
CREATE DATABASE IF NOT EXISTS timetable_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE timetable_db;
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  display_name VARCHAR(100) NOT NULL,
  role ENUM('ADMIN','LECTURER','STUDENT') NOT NULL,
  email VARCHAR(120) UNIQUE,
  active TINYINT(1) DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS semesters (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  academic_year VARCHAR(20),
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  is_published TINYINT(1) DEFAULT 0
);
CREATE TABLE IF NOT EXISTS courses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(20) UNIQUE NOT NULL,
  name VARCHAR(200) NOT NULL,
  credits INT NOT NULL DEFAULT 3,
  dept VARCHAR(100)
);
CREATE TABLE IF NOT EXISTS rooms (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(20) UNIQUE NOT NULL,
  name VARCHAR(120),
  capacity INT NOT NULL DEFAULT 60,
  type VARCHAR(50) DEFAULT 'LT'
);
CREATE TABLE IF NOT EXISTS timeslots (
  id INT AUTO_INCREMENT PRIMARY KEY,
  day_of_week TINYINT NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  week_pattern VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS class_sections (
  id INT AUTO_INCREMENT PRIMARY KEY,
  section_code VARCHAR(30) UNIQUE NOT NULL,
  course_id INT NOT NULL,
  lecturer_id INT NOT NULL,
  semester_id INT NOT NULL,
  expected_students INT DEFAULT 50,
  capacity INT DEFAULT 60,
  status ENUM('OPEN','CLOSED') DEFAULT 'OPEN',
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
  FOREIGN KEY (lecturer_id) REFERENCES users(id) ON DELETE RESTRICT,
  FOREIGN KEY (semester_id) REFERENCES semesters(id) ON DELETE RESTRICT
);
CREATE TABLE IF NOT EXISTS schedule_entries (
  id INT AUTO_INCREMENT PRIMARY KEY,
  class_section_id INT NOT NULL,
  room_id INT NOT NULL,
  timeslot_id INT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  note VARCHAR(255),
  FOREIGN KEY (class_section_id) REFERENCES class_sections(id) ON DELETE CASCADE,
  FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE RESTRICT,
  FOREIGN KEY (timeslot_id) REFERENCES timeslots(id) ON DELETE RESTRICT,
  UNIQUE KEY uq_sched_room (room_id, timeslot_id, start_date, end_date),
  UNIQUE KEY uq_sched_section (class_section_id, timeslot_id, start_date, end_date)
);
CREATE TABLE IF NOT EXISTS enrollments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_user_id INT NOT NULL,
  section_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (student_user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (section_id) REFERENCES class_sections(id) ON DELETE CASCADE,
  UNIQUE KEY uq_enr (student_user_id, section_id)
);
INSERT INTO users (username,password_hash,display_name,role,email,active)
VALUES ('admin','admin123','Administrator','ADMIN','admin@example.com',1)
ON DUPLICATE KEY UPDATE password_hash=VALUES(password_hash);
INSERT INTO semesters (name,academic_year,start_date,end_date,is_published)
VALUES ('HK1','2025-2026','2025-09-01','2025-12-31',1)
ON DUPLICATE KEY UPDATE start_date=VALUES(start_date),end_date=VALUES(end_date),is_published=VALUES(is_published);
