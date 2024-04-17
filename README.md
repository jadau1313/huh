Shady Scheduling Solutions
C195 Objective Assessment project
Author: Jacob Sutherland
Create date: 9/27/2023
Finalized: 11/16/2023

This program allows the user to add, update, and delete appointments and customers to the connected
database and serves as a scheduling application. The application takes into account the user's timezone
and the business's timezone
as well as whether the default language is English or French. 

To use this application, a user will first need to log in. They will be alerted of whether or not
an appointment exists within the next 15 minutes of their log in. The user is then brought to the main menu,
where they can navigate to either the customer screen, the appointment screen, the reports menu,
or they can return to the log in screen. The appointment and customer screens both present 
table views of the appointments and customers, respectively. From there, users can press buttons to
add, edit, or delete items. To delete or edit an item, a user must first select the item to edit
or delete. Appointments can be filtered by all, week, and month. The user can use the reports menu to navigate
to the various reports. They can choose to view contact schedule reports, which displays 
a schedule for a selected contact, or customer reports, where the user can view appointments
filtered by either type or month and a count is displayed to let the user know how many appointments
exist by type and by month.

For A3f, the additional report, I chose to build upon the contact schedule report. The base requirement
was to provide a schedule for each contact. I wanted to also filter this further by showing
a schedule for each contact for the upcoming 7 days. This is useful so that a contact has an idea
of what their upcoming week looks like, rather than just seeing all of their work layed out in
one table. Additionally, for my Customer reports, in addition to getting the count for appointments by
type and by month(displayed on the bottom left), I also created a table view to display the appointments that meet the filter
criteria.

Version information for personal machine
Developed with IntelliJ IDEA 2022.3.2(Community Edition)
JDK: 17.0.6.0 x64 
JavaFX SDK: 17.0.6
MySQL Connector version: 8.1.0 

Version information for VM:
