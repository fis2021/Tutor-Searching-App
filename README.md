# Tutor-Searching-App


# General description
This application aims to help both students and tutors by offering the possibility to increase the number of students that a tutor can have while also making it easier for students to find a tutor.


# Technologies used
Java 15


JavaFX 15 - UI;


Maven - dependencies and build tool;


Nitrite - database operations;

# Signing up for an account and logging in
Before using the platform, every user must sign up for an account. On registration, there are two types of user accounts, based on the aforementioned roles:

Student


Tutor

Based on the role of choice, specific functions will be granted to the user after logging in.


# The Tutor Account:
After the tutor logs in, he can accept / reject or remove students from his classes, specifying a rejecting / removing reason. The removing reason will automatically be added to the specific student’s personal information.


Also, after logging in, he will see a list of the students from all his classes.


A logged in tutor can also manage his calendar and personal information. The students will see the time when they have classes, exams and assignment deadlines based on the tutor’s calendar. The reviews will not be editable.


# The Student Account:
A student needs to login into the application to be able to see the list of all the tutors. The list should be searchable by the tutor’s name.


From the list of tutors, the student can also see the personal information of a specific tutor and his calendar, by selecting the tutor from the list.


A logged in student can also manage his personal information. The removing reasons will not be editable.


Also, logged in students can send reviews to the tutors. Students will only be able to review a tutor if they participated to their classes. The reviews will be available for both students and tutors to see and will automatically be added to the tutor’s personal information.


A logged in student can see a list of past requests, with their status (accepted, rejected, or pending). If the request is rejected, the rejection reason will also be displayed.

