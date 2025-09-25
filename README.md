# Campus Course Records Manager (CCRM)

A console-based Java SE application for managing campus courses, students, grades, and records.

## Overview

CCRM helps educational institutions with:

- **Student Management:** Add, list, update, deactivate, enroll in classes, print profiles and transcripts.
- **Course Management:** Search/filter by instructor, department, or semester; add, list, update, deactivate, assign instructors.
- **Grades & Enrollment:** Compute GPA, track grades, enroll/disenroll students (with credit limits).
- **File Operations:** Import/export CSV data, backup to timestamped folders, recursively calculate backup sizes.
- **Reports:** Generate GPA distributions and top student lists using Streams API.

This project demonstrates Java SE skills: OOP, exception handling, modern I/O, Streams & Lambdas, recursion, and design patterns (Singleton, Builder).

---

## Setup & Running

### Requirements

- Java JDK 17+
- Eclipse IDE (recommended)

### Steps

1. **Clone the repository**
   ```sh
   git clone <repo-link>
   cd CCRM
   ```
2. **Compile**
   ```sh
   javac -d bin src/edu/ccrm/cli/Main.java
   ```
3. **Run**
   ```sh
   java -cp bin edu.ccrm.cli.Main
   ```

### Eclipse Setup

- Import as an existing project.
- Select the `src` folder.
- Right-click `Main.java` → Run As → Java Application.

---

## Features

### Student Management

- Add, list, update, deactivate students.
- Fields: `id`, `regNo`, `fullName`, `email`, `status`, `enrolledCourses`, `dateOfRegistration`.
- Print student profile & transcript.

### Course Management

- Add, list, update, deactivate courses.
- Fields: `code`, `title`, `credits`, `instructor`, `semester`, `department`.
- Search/filter by instructor, department, or semester (Streams API).

### Enrollment & Grading

- Enroll/unenroll students.
- Validate max credit limit per semester.
- Record marks, compute GPA & Grade (enums).
- Transcript generation (polymorphism, `toString()`).

### File Operations

- Import students/courses from CSV.
- Export data (students, courses, enrollments).
- Backup: copy exported files to timestamped folder.
- Recursive utilities: calculate backup size, list files by depth.

### CLI Workflow

Menu-driven interface:

1. Manage Students
2. Manage Courses
3. Enrollment & Grading
4. Import/Export Data
5. Backup & Reports
6. Exit

---

## Java Concepts Demonstrated

- **Core Language:** Primitives, operators, decision structures, loops, arrays, strings.
- **OOP:** Encapsulation, inheritance, abstraction, polymorphism, access levels, immutability, nested classes, interfaces, enums.
- **Advanced:** Lambdas, anonymous classes, Streams, custom exceptions, assertions, design patterns (Singleton, Builder), recursion.

---

## Package Structure

```
edu.ccrm
 ├── cli/         # Menu & input loop
 ├── domain/      # Person, Student, Instructor, Course, Enrollment, Grade, Semester
 ├── service/     # StudentService, CourseService, EnrollmentService, TranscriptService
 ├── io/          # ImportExportService, BackupService (NIO.2)
 ├── util/        # Validators, Comparators, recursion utilities
 └── config/      # AppConfig (Singleton), Builders
```

---

## Mapping Table (Syllabus → Code)

| Topic              | Example                          |
|--------------------|----------------------------------|
| Encapsulation      | Student with getters/setters     |
| Inheritance        | Person → Student, Instructor     |
| Abstraction        | Abstract Person                  |
| Polymorphism       | TranscriptService interface      |
| Enum               | Semester, Grade                  |
| Singleton          | AppConfig                        |
| Builder            | Course.Builder                   |
| Exception Handling | MaxCreditLimitExceededException  |
| Streams API        | Filtering/searching courses, GPA |
| Recursion          | Backup directory size calculation|

---

## Java Evolution Highlights

- **1995:** Java 1.0 released
- **2004:** Java 5 (generics, enums, annotations)
- **2014:** Java 8 (lambdas, Streams, Date/Time API)
- **2017:** Java 9 (modules)
- **2021:** Java 17 (sealed classes, records)

### Java Editions

| Edition   | Use Case                |
|-----------|------------------------|
| Java ME   | Mobile & embedded      |
| Java SE   | Desktop/local apps     |
| Java EE   | Web/enterprise apps    |

### JDK vs JRE vs JVM

- **JDK:** Tools + Compiler + JRE
- **JRE:** JVM + libraries
- **JVM:** Executes bytecode

---

## Notes

- Run with `java -ea` to enable assertions.
- Sample CSV files in `/test-data/`.

---

## Acknowledgements

- Official Java Documentation
- Eclipse IDE
- Tutorials on Streams, NIO.2, Design Patterns
