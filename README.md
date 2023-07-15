# Anippe CRM

Purpose of this project is to create Perfex CRM clone with Java, powered by Eclipse Scout framework and PostgreSql database.

# How to run
1. Clone repository
2. Create database
3. Rename src/main/resources/config.properties.example to config.properties in anippe.server.app.dev and anippe.ui.html.app.dev projects
4. Open renamed config.properties file in anippe.server.app.dev project and write your database connection parameters.
5. Install Node.js and PNPM using this tutorial.
6. Import project into IntelliJ
7. Run webapp[all] to build CSS & JS and run server and client.
8. Start project at http://localhost:8082

This will run migrations on database and create tables.
