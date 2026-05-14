# 📞 Telephone Directory GUI System

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-green)
![JDBC](https://img.shields.io/badge/JDBC-Connected-yellow)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen)

---

## 📋 Project Description

The **Telephone Directory GUI System** is a fully functional Java
desktop application that allows users to manage contacts through
a modern graphical user interface. Users can add, update, delete,
and search for contacts while ensuring that each contact name is
unique across the directory.

The system uses a **MySQL database** to permanently save all contact
data, so information is automatically loaded every time the
application starts. It is built using **Java Swing** and follows
**Object-Oriented Programming** principles, combining a clean
backend logic layer with an interactive and user-friendly interface.

---
 🏗️ System Architecture

The application follows a 3-layer architecture:

- Presentation Layer → Java Swing (UI)
- Business Logic Layer → ContactManager
- Data Layer → MySQL Database

This separation improves maintainability and scalability.

## ✨ Features

| Feature | Description |
|---|---|
| ➕ Add Contact | Add a new contact with a unique name and phone number |
| 🔍 Search Contact | Search for any contact by name instantly |
| ✏️ Update Contact | Update the phone number of an existing contact |
| 🗑️ Delete Contact | Remove a contact with a confirmation dialog |
| 📋 Display All | View all contacts in a sortable styled table |
| 💾 Data Persistence | All data saved to MySQL — survives app restart |
| ✅ Validation | Empty fields and duplicate names are handled |
| ⚠️ Error Handling | All errors shown via user-friendly popup dialogs |
| 🎨 Professional UI | Colored buttons, styled table, live status bar |
| 🖱️ Smart UX | Click any row to auto-fill input fields |
| 📤 Export to CSV | Export all contacts to a CSV file with one click |

---

## 👥 Team Members

| # | Name | Student ID | GitHub |  |
|---|---|---|---|---|
| 1 | Nuredin Seid | 1578/16 | [@Nurexi](https://github.com/Nurexi)
| 2 | Eman Ahmed | 0663/16 | [@Eman-Ahm](https://github.com/Eman-Ahm) 
| 3 | Siham Desta | 1774/16 | [@sihutina](https://github.com/sihutina) 
| 4 | Hayat Shekur | 4025/16 | [@hayat121dev](https://github.com/hayat121dev) 
| 5 | Ammar Abdurahman | 0244/16 | [@Ammudy](https://github.com/Ammudy) 
| 6 | Salsawit Ristu | 1655/16 | [@salsi-hub](https://github.com/salsi-hub) 

---

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|---|---|---|
| Java | 17+ | Core programming language |
| Java Swing | Built-in | GUI framework |
| JDBC | Built-in | Java Database Connectivity |
| MySQL | 8.0+ | Relational database storage |
| MySQL Connector | 9.7.0 | JDBC driver for MySQL |
| IntelliJ IDEA | Latest | Development environment |
| Git + GitHub | Latest | Version control + collaboration |

---
 🔐 Security Considerations

- Uses PreparedStatement to prevent SQL Injection
- Input validation ensures correct and safe user data
- Database credentials stored in config.properties file
- Error handling prevents system crashes and data corruption

## 📁 Project Structure

```
📦 TelephoneDirectoryGUI
┃
┣ 📂 src
┃ ┣ 📂 model
┃ ┃ ┗ 📄 Contact.java
┃ ┃     ├── name (String) — unique identifier
┃ ┃     ├── phoneNumber (String)
┃ ┃     ├── getName()
┃ ┃     ├── getPhoneNumber()
┃ ┃     └── setPhoneNumber()
┃ ┃
┃ ┣ 📂 service
┃ ┃ ┗ 📄 ContactManager.java
┃ ┃     ├── addContact()     — INSERT to MySQL
┃ ┃     ├── searchContact()  — SELECT from MySQL
┃ ┃     ├── deleteContact()  — DELETE from MySQL
┃ ┃     ├── updateContact()  — UPDATE in MySQL
┃ ┃     └── getAllContacts() — SELECT ALL from MySQL
┃ ┃
┃ ┣ 📂 ui
┃ ┃ ┗ 📄 MainGUI.java
┃ ┃     ├── buildUI()         — builds all components
┃ ┃     ├── attachListeners() — connects buttons to logic
┃ ┃     ├── handleAdd()       — add contact handler
┃ ┃     ├── handleDelete()    — delete contact handler
┃ ┃     ├── handleUpdate()    — update contact handler
┃ ┃     ├── handleSearch()    — search contact handler
┃ ┃     └── refreshTable()    — reloads table from DB
┃ ┃
┃ ┣ 📂 util
┃ ┃ ┗ 📄 ExportUtil.java
┃ ┃     └── exportToCSV() — saves contacts to .csv file
┃ ┗ 📄 Main.java
┃       └── main() — launches GUI on EDT
┃
┣ 📄 .gitignore
┗ 📄 README.md
```

---



## ⚙️ Requirements

Before running this project make sure you have:

| Requirement | Download |
|---|---|
| Java JDK 17+ | [Download](https://www.oracle.com/java/technologies/downloads/) |
| IntelliJ IDEA | [Download](https://www.jetbrains.com/idea/download/) |
| MySQL Server | [Download](https://dev.mysql.com/downloads/mysql/) |
| MySQL Connector JAR | [Download](https://dev.mysql.com/downloads/connector/j/) |

---

## 🗄️ Database Setup

Open **MySQL Workbench** and run these commands:

```sql
-- Step 1: Create the database
CREATE DATABASE telephone_directory;

-- Step 2: Select the database
USE telephone_directory;

-- Step 3: Create the contacts table
CREATE TABLE contacts (
    name  VARCHAR(100) PRIMARY KEY,
    phone VARCHAR(50)  NOT NULL
);
```

---

## 🚀 How to Run

### Step 1 — Clone the Repository
```bash
git clone https://github.com/Nurexi/Telephone-directory-gui-java.git
cd Telephone-directory-gui-java
```

### Step 2 — Add MySQL Connector JAR
```
1. Open project in IntelliJ IDEA
2. Click File → Project Structure → Libraries
3. Click + → Java
4. Select mysql-connector-j-9.7.0.jar
5. Click OK → Apply → OK
```

### Step 3 — Create `config.properties`
Create a new file inside the `src` folder called `config.properties`:
```properties
db.url=jdbc:mysql://localhost/telephone_directory
db.user=root
db.password=YOUR_MYSQL_PASSWORD
```
> ⚠️ Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL password

### Step 4 — Run the Application
```
Right click Main.java → Run 'Main'
```
> ✅ The Telephone Directory window will open!

---

## 🔒 Error Handling

| Scenario | Message Displayed |
|---|---|
| Empty name or phone field | ⚠️ "Please fill in all required fields." |
| Duplicate contact name | ⚠️ "This name is already used. Please enter a unique name." |
| Contact not found | ⚠️ "Contact not found." |
| Before deleting | ⚠️ "Are you sure you want to delete this contact?" |
| Successful add | ✅ "Contact added successfully." |
| Successful update | ✅ "Contact updated successfully." |
| Successful delete | ✅ "Contact deleted successfully." |

---

## 🔗 JDBC Connection 

```java
// Step 1 — Register Driver
Class.forName("com.mysql.cj.jdbc.Driver");

// Step 2 — Create Connection
Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

// Step 3 — Create Statement
PreparedStatement ps = conn.prepareStatement(sql);

// Step 4 — Execute Query
ps.executeUpdate(); // for INSERT, UPDATE, DELETE
ps.executeQuery();  // for SELECT

// Step 5 — Close Connection (auto with try-with-resources)
```

---

## 📚 Concepts Demonstrated

| Concept | Implementation |
|---|---|
| Object-Oriented Programming | Contact class, ContactManager class |
| Data Structures | ArrayList + MySQL table |
| GUI Design | Java Swing components |
| Event-Driven Programming | ActionListeners, SelectionListeners |
| Error Handling | JOptionPane dialogs |
| JDBC | Java Database Programming — Chapter 5 |
| SQL Queries | INSERT, SELECT, UPDATE, DELETE |
| Team Collaboration | Git branching + GitHub |

---

## 🌿 Branch Structure

| Branch | Member | Responsibility |
|---|---|---|
| `main` | Nuredin | Full project + MySQL integration |
| `eman-model` | Eman | Contact.java data model |
| `siham-main-window` | Siham | Main window + input fields |
| `hayat-add-delete` | Hayat | Add + Delete functionality |
| `ammar-update-search` | Ammar | Update + Search functionality |
| `salsawit-ui-styling` | Salsawit | UI styling + UX polish |

---
🚀 Future Improvements

- Add user authentication (login system)
- Export contacts to CSV file
- Add profile pictures for contacts
- Improve UI with modern design frameworks
- Convert application to web or mobile version

## 📄 License

This project was developed for educational purposes as part of a
Java Programming course.

---

*Built with ❤️ by Group 5 — Java Programming Course 2025*
