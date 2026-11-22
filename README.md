# KNITTY

KNITTY is my interdisciplinary project. It's an e-commerce website focused on crochet patterns, where users can find, buy and sell their own designs.

The idea is to create a platform for people who enjoy crochet, where they can discover new patterns and share their creations.

## Technologies used

**Frontend**
- React
- TypeScript
- HTML and CSS

**Backend**
- Java
- Spring Boot
- Hibernate / JPA

**Database**
- MariaDB
- XAMPP

## What I've done so far

- Designed the website and its main pages.
- Created the database and its relationships.
- Connected the backend to MariaDB.
- Implemented user registration and login.
- Added form validation and session management.
- Created a user profile.
- Connected the crochet pattern catalog to the database.
- Added the main categories: Home, Plushies, Complements and Clothes.
- Started working on the wishlist, purchases and sales information.

## Running the project

First, start MySQL/MariaDB from XAMPP and make sure the `db_knitty` database exists.

**Backend**

Open a terminal in the `knitty-backend` folder:

    bash mvnw spring-boot:run

**Frontend**

Open another terminal in the `knitty_frontend` folder:

    npm install
    npm run dev

Then open http://localhost:5173 in your browser.

## What's next?

The project is still a work in progress. I still need to finish the shopping system, payments, pattern downloads and some user features.

I also want to develop an Android version of KNITTY that uses the same backend and database as the website.

## About

This project is being developed for my studies, and I'll keep updating it as I complete the different parts.
